import React from 'react';
import logo from './media/logo.png';
import { Link } from 'react-router-dom';

const Header = () => (
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
				<button>
					Log Out
				</button>
			</li> 
		</ul>
	</header>
);

const LeftSideMenu = () => (
	<aside className='flex-item sidebar'>
		<ul class='nav'>
			<li><Link to='/profile'>Profile</Link></li>
			<li><Link to='/menu'>Menu</Link></li>
			<li><Link to='/music'>Music</Link></li>
			<li><Link to='/video'>Video</Link></li>
			<li><Link to='/contacts'>Contacts</Link></li>
		</ul>
	</aside>
)

const Footer = () => (
	<li className='flex-item footer'>(C) Designed by Kolumbia 2019</li>
)
export { Header, LeftSideMenu, Footer };