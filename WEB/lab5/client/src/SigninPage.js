import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import sha256 from 'crypto-js/sha256';
import { Header, LeftSideMenu, Footer } from './Statics'; 

class SignInPage extends Component {
	constructor(props) {
		super(props);
		this.state = {
			login: '',
			password: '',
			success: null,
		}
		this.handleChange = this.handleChange.bind(this);
		this.handleSubmit = this.handleSubmit.bind(this);
	}

	handleChange(event) {
		const { name, value } = event.target;
		this.setState({ [name] : value });
	}

	handleSubmit(event) {
		console.log(this.state.login, this.state.password);
		event.preventDefault();
		let data = JSON.stringify({
			login: this.state.login,
			password: sha256(this.state.password).toString()});
		fetch('/login', {
			method: 'POST',
			body: data,
			}
		)
		.then((res) => {
			this.setState({ success: res.ok ? true : false });
			return res.text();
		})
		.then((token) => {
			if (this.state.success) {
				localStorage.setItem('token', token);
				this.props.history.push('/profile');
			}
			else {
				alert('Invalid password or login');	
			}
		})
		.catch((err) => console.log(err));
	}

	render() {
		return (
			<ul className='flex-container'>
				<li className='flex-item header'>
					<Header isLoggedIn={this.state.isLoggedIn} />
				</li>
				<LeftSideMenu />
				<li className='flex-item main'>
					<h1>It is an Sign In page</h1><br/> <hr/> <br/>
					<h3>Please, enter a login and password to sign in!</h3>
					<br/>
					<form onSubmit={this.handleSubmit} className="Form" id="signUp" style={{margin: 'auto', width: '300px', height:'310px'}}> 
						<input
							type="text"
							placeholder=" Login"
							name="login"
							value={this.state.login}
							onChange={this.handleChange} required/><br/>
						<input
							type="password" 
							placeholder=' Password'
							name="password" 
							value={this.state.password} 
							onChange={this.handleChange}/><br/>
						<button onClick={this.handleSubmit}>Submit</button> 
					</form>
					Don't have an accout? Then create it! <br/>
					<Link to='signup'> Press here to create a new accout =)</Link> <br/><br/><br/>
				</li>
				<Footer />
			</ul>
		)
	}
}

export default SignInPage;