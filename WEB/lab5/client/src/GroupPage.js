import React, { Component } from 'react';
import { Header, LeftSideMenu, Footer } from './Statics';
import decode from 'jwt-decode';
import Post from './Post';

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
	}

	handleAdd(event) {
		event.preventDefault();
		document.getElementById('user').reset();
		const id = this.props.match.params.id;
		fetch(`/addusertogroup?group=${id}&user=${this.state.user}`)
		.then((res) => {
			if (res.ok) {
				alert('User added');
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

		// fetch(`/getgroups?group=${id}`)
		// 	.then(res => res.json())
		// 	.then((data) => this.setState({ posts: data }))
		// 	.catch((err) => console.log(err));
	}

	renderOneUser(elem) {
		return (
			<p>{elem}&nbsp;;</p>
		)
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
		let name = '';
		let potsts = [];
		let users = [];
		if (this.state.data != null) {
			name = this.state.data.name;
			for (let user of this.state.data.users)
				users.push(this.renderOneUser(user))
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
									You are an admin. You can add or delete users
									<form id='user' >
										<textarea
											name="user"
											value={this.state.user}
											onChange={this.handleChange}
										/><br/>
										<button onClick={this.handleAdd}>Add</button>
										<button onClick={this.handleDelete}>Delete</button>
									</form>
								</div>
							) : (<div></div>)
						}
					</li>
				<Footer />
			</ul>
		)
	}
}

export default GroupPage;