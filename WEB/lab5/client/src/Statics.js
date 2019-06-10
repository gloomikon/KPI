import React, { Component } from 'react';
import logo from './media/logo.png';
import { Link } from 'react-router-dom';

class Header extends Component {
	constructor() {
		super();
		this.state = {
			token: null,
		}
	}
	componentDidMount() {
		const token = localStorage.getItem('token');
		this.setState({ token : token });
	}
	handleClick() {
		localStorage.removeItem('token');
	}
	render() {
		return (
			<header>
				<ul>
					<li>
						<img src={logo} alt='logo' className='logo'/>
					</li>
					<li>
						BT
					</li>
					<li className='headerSearch'>
						<input placeholder='Search...' />
					</li>
					<li>
						{
							this.state.token ? <button><Link to='signin' onClick={this.handleClick}> Log Out </Link></button> :
							<button><Link to='/signin'> Log In </Link></button>
						}
					</li> 
				</ul>
			</header>
		)
	}
}

const LeftSideMenu = () => (
	<aside className='flex-item sidebar'>
		<ul className='nav'>
			<li><Link to='/profile'>Profile</Link></li>
			<li><Link to='/index'>Menu</Link></li>
			<li><Link to='/groups'>Groups</Link></li>
			<li><Link to='/admin'>Admin</Link></li>
			<li><Link to='/contacts'>Contacts</Link></li>
		</ul>
	</aside>
)

const Footer = () => (
	<li className='flex-item footer'>(C) Designed by Kolumbia 2019</li>
)
export { Header, LeftSideMenu, Footer };