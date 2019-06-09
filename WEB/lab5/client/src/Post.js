import React from "react";
import './Post.css';
import { exists } from "fs";

const	imgTypes	=	["png", "jpg", "jpeg"];
const	videoTypes	=	["mp4", "wemb"];
const	musicTypes	=	["wav", "mp3"];

function getType(extension) {
	console.log(`extension = ${extension}`);
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
			deleted: false, 
			edited: false
		}
		this.handleClick = this.handleClick.bind(this);
		this.handleSubmit = this.handleSubmit.bind(this);
		this.handleChange = this.handleChange.bind(this);
		this.handleDelete = this.handleDelete.bind(this);
	}
	handleSubmit(event) {
		if (this.state.file != "" || this.state.text != "") { 
			this.setState(prev => prev.edited=false);
		}
		else {
			event.preventDefault(); 
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
						{
							(extension == 'img') ? <img src = {require(`./files/${this.state.file}`)} alt="post" width='200px'/> : <div/>
						}
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