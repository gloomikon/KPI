import React, { Component } from 'react';
import avatar from './media/default.png';
import { Header, LeftSideMenu, Footer } from './Statics'; 
import decode from 'jwt-decode';
import Post from './Post';

function date() {
	const currentDate = new Date();
	const date = currentDate.getDate();
	const month = currentDate.getMonth();
	const year = currentDate.getFullYear();
	const hours = currentDate.getHours();
	const mins = currentDate.getMinutes();
	const dateString = date + '.' + (month + 1) + '.' + year + '  ' + hours + ':' + mins;
	return dateString;
}

class ProfilePage extends Component {
	constructor(props) {
		super(props);
		const token = localStorage.getItem('token');
		this.state = {
			isLoggedIn: token ? true: false,
			currentUser: token ? decode(token).login : '',
			data: null,
			fullname: '',
		}
		this.handleSubmit = this.handleSubmit.bind(this);
	}
	componentDidMount() {
		if (this.state.currentUser)
		{
			fetch(`/profilename?login=${this.state.currentUser}`)
				.then(res => res.text())
				.then((fullname) =>  this.setState({ fullname: fullname }))
				.catch((err) => console.log(err));
			fetch(`/profileposts?login=${this.state.currentUser}`)
				.then(res => res.json())
				.then((data) => this.setState({ data: data }))
				.catch((err) => console.log(err));
		}
	}
	handleSubmit(event) {
		event.preventDefault();
		const data = new FormData(document.getElementById('post'));
		data.append('login', this.state.currentUser);
		document.getElementById('post').reset();
		fetch('/addpost', {
			method: 'POST',
			body: data,
		}).then((res) => {
			if (res.ok) {
				fetch(`/profileposts?login=${this.state.currentUser}`)
				.then(res => res.json())
				.then((data) => this.setState({ data: data }))
				.catch((err) => console.log(err));
			}
			else {
				alert('Post has not been added');
			}
		}).catch((err) => console.log(err));
	}

	renderOnePost(post) {
		return (
			<Post 
				text={post.message}
				file={post.filePath}
				time={post.time}
				id={post._id}
			/>
		)
	}
	render() {
		let posts = [];
		if (this.state.data != null && this.state.data.length > 0) {
			for (let post of this.state.data) {
				posts.push(this.renderOnePost(post));
			}
		}
		return (
			<ul className='flex-container'>
				<li className='flex-item header'>
					<Header isLoggedIn={this.state.isLoggedIn} />
				</li>
				<LeftSideMenu />
				{
					this.state.currentUser ? (
						<li className='flex-item main'>
					<section className='flex-item main_header'>
						<ul>
							<li><img src={avatar} alt='avatar' className='avatar' /></li>
							<li>
								<p id='profile'><b>{this.state.fullname}<br/><br/></b><i>Kiev, Ukraine<br/><br/></i>Keep it cool!</p>
							</li>
						</ul>
					</section>
					<article className='flext-item content'>
						<h1>Create new post</h1>
						<form id='post' onSubmit={this.handleSubmit} encType="multipart/form-data">
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
					)
					:
					(
						<li className='flex-item main'>
					<section className='flex-item main_header'>
						<h1>You are not logged in!</h1>
					</section>
					<article className='flext-item content'>
						<h1><br/><br/><br/><br/>Please, log in to create posts!!!!<br/><br/><br/><br/><br/><br/><br/><br/></h1>
					</article>
					</li>
					)
				}
				<Footer />
			</ul>
		)
	}
}

export default ProfilePage;