import React from "react";

const	imgTypes	=	["png", "jpg", "jpeg"];
const	videoTypes	=	["mp4", "wemb"];
const	musicTypes	=	["wav", "mp3"];

function getType(extension) {
	return (imgTypes.includes(extension)) ? "img" :
		(videoTypes.includes(extension)) ? "video" :
		(musicTypes.includes(extension)) ? "music" : "file";
}

class Post extends React.Component {
	constructor(props){
		super(props);
		this.state = {
			text: props.text, 
			file: props.file,
			time: props.time,
			id:	props.id,
			deleted: false, 
			edited: false
		}
		this.handleClick = this.handleClick.bind(this);
		this.handleSubmit = this.handleSubmit.bind(this);
		this.handleChange = this.handleChange.bind(this);
		this.handleDelete = this.handleDelete.bind(this);
	}
	handleSubmit(event) {
		event.preventDefault();
		if (this.state.file !== "" || this.state.text !== "") { 
			this.setState(prev => prev.edited=false);
			fetch('/updatePost', {
				method: 'POST',
				body: JSON.stringify({ text: this.state.text, id: this.state.id })
			})
			.then((res) => res.ok? alert('Post updated'): alert('Error'));
		}
		else {
			alert('Not all fields are filled!');
		}
	}
	handleClick() { 
		this.setState(prev => prev.edited=true);
	}
	handleChange(event) {
		const {name, value} = event.target;
		this.setState({[name]: value})
	}
	handleDelete() {
		this.setState({ deleted: true });
		fetch('/deletePost', {
			method: 'POST',
			body: JSON.stringify({ id: this.state.id })
		})
		.then((res) => res.ok? alert('Post deleted'): alert('Error'));
	}
	renderFile(extension) {
		if (extension === 'img')
			return (
				<img src = {require(`./files/${this.state.file}`)} alt="post" width='200px'/>
			)
		else if (extension === 'video')
				return (
					<video width="320" height="240" controls preload="auto" >
						<source src={require(`./files/${this.state.file}`)} type="video/webm" />
						<p>Your browser does not support the video tag.</p>
					</video>
				)
		else if (extension === 'music')
				return (
					<audio controls>
						<source src = {require(`./files/${this.state.file}`)} type = "audio/mp3" />
						<p>Your browser doesn't support the audio tag.</p>
					</audio>
				)
		else if (extension === 'file')
				return (
					<p>Attachment: {this.state.file}</p>
				)
		else return (
			<div/>
		)
	}
	render() {
		let extension = (this.state.file) ? getType(this.state.file.split('.').pop()) : null;
		return (
			this.state.deleted ? (<div></div>) : (
				this.state.edited ? (
					<form onSubmit={this.handleSubmit} className="userPost">
						<textarea
							value={this.state.text} 
							name="text" 
							onChange={this.handleChange}/>
						<div><button>Save</button></div>
					</form>
				):(
					<div className="userPost">
						<br/>
						<p>{this.state.time}</p>
						<p>{this.state.text}</p>
						{ this.renderFile(extension) }
						<div>
							<button onClick={this.handleClick}>Change</button> 
							<button onClick={this.handleDelete}>Delete</button>
						</div>
						<hr/>
					</div>
				)
			)
		)
	}

}
export default Post;