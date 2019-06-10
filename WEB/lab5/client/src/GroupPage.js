import React, { Component } from 'react';
import { Header, LeftSideMenu, Footer } from './Statics'; 

class MenuPage extends Component {
	constructor(props) {
		super(props);
		const token = localStorage.getItem('token');
		this.state = {
			isLoggedIn: token ? true: false
		}
	}

	componentDidMount() {
		let id = { id: this.props.match.params.id };
		fetch(`/getgroupinfo?group=${id}`)
		.then(res => res.json())
		.then((data) => this.setState({data: data}))
		.catch(err => console.log(err));
	}
	renderOneUser(elem) {
		return (
			<p>elem&nbsp;;</p>
		)
	}
	renderUsers() {
		let users = [];
		for (user in data.users)
			users.push(this.renderOneUser(user))
		return (
			<div>
				{users}<br/>
			</div>
		)
	}
	render() {
		return (
			<ul className='flex-container'>
				<li className='flex-item header'>
					<Header isLoggedIn={this.state.isLoggedIn} />
				</li>
				<LeftSideMenu />
					<li className='flex-item main'>
						Menu
					</li>
				<Footer />
			</ul>
		)
	}
}

export default MenuPage;