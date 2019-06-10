import React, { Component } from 'react';
import decode from 'jwt-decode';
import { Header, LeftSideMenu, Footer } from './Statics'; 
import { Link } from 'react-router-dom';

class GroupsPage extends Component {
	constructor(props) {
		super(props);
		const token = localStorage.getItem('token');
		this.state = {
			isLoggedIn: token ? true: false,
			currentUser: token ? decode(token).login : '',
			data: null,
			group: '',
		}
		this.handleSubmit = this.handleSubmit.bind(this);
		this.handleChange = this.handleChange.bind(this);
	}

	handleSubmit(event) {
		event.preventDefault();
		document.getElementById('group').reset();
		fetch('/creategroup', {
			method: 'POST',
			body: JSON.stringify({'admin': this.state.currentUser, 'group': this.state.group }),
		}).then((res) => {
			if (res.ok) {
				alert('Group created');
				fetch(`/getgroups?login=${this.state.currentUser}`)
				.then(res => res.json())
				.then((data) => this.setState({ data: data }))
				.catch((err) => console.log(err));
			}
			else {
				alert('Group alredy exists');
			}
		}).catch((err) => console.log(err));
	}

	handleChange(event) {
		const {name, value} = event.target;
		this.setState({[name]: value})
	}

	componentDidMount() {
		fetch(`/getgroups?login=${this.state.currentUser}`)
		.then(res => res.json())
		.then((data) => this.setState({ data: data }))
		.catch((err) => console.log(err));
	}

	renderOneGroup(group) {
		return (
			<Link to={`/group/${group._id}`}>{group.name}<br/></Link>
		)
	}
	render() {
		let groups = [];
		if (this.state.data != null && this.state.data.length > 0)
			for (let group of this.state.data)
				groups.push(this.renderOneGroup(group));
		return (
			<ul className='flex-container'>
				<li className='flex-item header'>
					<Header isLoggedIn={this.state.isLoggedIn} />
				</li>
				<LeftSideMenu />
				{
					this.state.currentUser ? (
						<li className='flex-item main' style={{height:'650px'}}>
							<h1>Add new group: </h1><hr/>
							<form id='group' onSubmit={this.handleSubmit}>
								Name:
								<textarea
									name='group'
									value={this.state.group}
									onChange={this.handleChange}
								/><br/>
								<input type='submit' value='SEND' />
							</form><hr/><br/>
							{groups}
						</li>
					) : (
						<li className='flex-item main'>
						<section className='flex-item main_header'>
							<h1>You are not logged in!</h1>
						</section>
						<article className='flext-item content'>
							<h1><br/><br/><br/><br/>Please, log in to see your groups!!!!<br/><br/><br/><br/><br/><br/><br/><br/></h1>
						</article>
						</li>
					)
				}
				<Footer />
			</ul>
		)
	}
}

export default GroupsPage;