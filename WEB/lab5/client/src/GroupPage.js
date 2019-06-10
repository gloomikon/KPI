import React, { Component } from 'react';
import { Header, LeftSideMenu, Footer } from './Statics';
import decode from 'jwt-decode';
import Post from './GrPost';

class GroupPage extends Component {
	constructor(props) {
		super(props);
		const token = localStorage.getItem('token');
		this.state = {
			isLoggedIn: token ? true: false,
			currentUser: token ? decode(token).login : '',
			data: null,
			posts: null,
			user: '',
			admin: '',
		}
		this.handleAdd = this.handleAdd.bind(this);
		this.handleDelete = this.handleDelete.bind(this);
		this.handleChange = this.handleChange.bind(this);
		this.handleSubmit = this.handleSubmit.bind(this);
	}

	handleSubmit(event) {
		event.preventDefault();
		const id = this.props.match.params.id;
		const data = new FormData(document.getElementById('grpost'));
		data.append('login', this.state.currentUser);
		data.append('groupid', this.props.match.params.id);
		document.getElementById('grpost').reset();
		fetch('/addgrpost', {
			method: 'POST',
			body: data,
		}).then((res) => {
			if (res.ok) {
				fetch(`/getgrposts?group=${id}`)
				.then(res => res.json())
				.then((data) => this.setState({ posts: data }))
				.catch((err) => console.log(err));
				alert('Post has been added');
			}
			else {
				alert('Post has not been added');
			}
		}).catch((err) => console.log(err));
	}

	handleAdd(event) {
		event.preventDefault();
		document.getElementById('user').reset();
		const id = this.props.match.params.id;
		fetch(`/addusertogroup?group=${id}&user=${this.state.user}`)
		.then((res) => {
			if (res.ok) {
				alert('User added');
				fetch(`/getgroupinfo?group=${id}`)
				.then(res => res.json())
				.then((data) => {this.setState({data: data});})
				.catch(err => console.log(err));
			}
			else {
				alert('Error occured');
			}
		})
		.catch(err => console.log(err));
	}

	handleDelete(event) {
		event.preventDefault();
		document.getElementById('user').reset();
		const id = this.props.match.params.id;
		fetch(`/deluserfromgroup?group=${id}&user=${this.state.user}`)
		.then((res) => {
			if (res.ok) {
				alert('User deleted');
			}
			else {
				alert('Error occured');
			}
		})
		.catch(err => console.log(err));
	}

	handleChange(event) {
		const {name, value} = event.target;
		this.setState({[name]: value})
	}
	componentDidMount() {
		const id = this.props.match.params.id;
		fetch(`/getgroupinfo?group=${id}`)
			.then(res => res.json())
			.then((data) => {this.setState({data: data, admin: data.users[0]});})
			.catch(err => console.log(err));

		fetch(`/getgrposts?group=${id}`)
			.then(res => res.json())
			.then((data) => this.setState({ posts: data }))
			.catch((err) => console.log(err));
	}

	renderOneUser(elem) {
		return (
			<p>{elem}&nbsp;</p>
		)
	}

	renderOnePost(post) {
		return (
			<Post 
				text={post.message}
				file={post.filePath}
				time={post.time}
				login={post.login}
				id={post._id}
			/>
		)
	}

	render() {
		let name = '';
		let posts = [];
		let users = [];
		if (this.state.data != null) {
			name = this.state.data.name;
			for (let user of this.state.data.users)
				users.push(this.renderOneUser(user))
		}
		if (this.state.posts != null && this.state.posts.length > 0) {
			for (let elem of this.state.posts) {
				posts.push(this.renderOnePost(elem));
			}
		}
		return (
			<ul className='flex-container'>
				<li className='flex-item header'>
					<Header isLoggedIn={this.state.isLoggedIn} />
				</li>
				<LeftSideMenu />
					<li className='flex-item main'>
						<h1>&nbsp;{name}</h1><hr/>
						Users:&nbsp;{users}
						<hr/>
						{
							(this.state.currentUser === this.state.admin) ? (
								<div>
									<section className='flex-item main_header'>
										<h6>You are an admin. You can add or delete users</h6>
										<form id='user' >
											<textarea
												name="user"
												value={this.state.user}
												onChange={this.handleChange}
											/><br/>
											<button onClick={this.handleAdd}>Add</button>
											<button onClick={this.handleDelete}>Delete</button>
										</form>
									</section>
								</div>
							) : (<div></div>)
						}
						<hr/>
						<article className='flext-item content'>
							<h5>Create new post</h5>
							<form id='grpost' onSubmit={this.handleSubmit} encType="multipart/form-data">
								<p>Your message:</p><br/>
								<textarea name='message' /><br/>
								<p>Attach file:</p>
								<input type='file' name='filePath' /><br/>
								<input type='submit' value='SEND' />
							</form>
							<hr/>
							{posts}
						</article>
					</li>
				<Footer />
			</ul>
		)
	}
}

export default GroupPage;