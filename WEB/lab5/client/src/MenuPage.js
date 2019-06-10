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
					<li className='flex-item main' style={{height:'650px'}}>
						<p>Under construction</p><br/>
						<p>Nothing to do here</p>
					</li>
				<Footer />
			</ul>
		)
	}
}

export default MenuPage;