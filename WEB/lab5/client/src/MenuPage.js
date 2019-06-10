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