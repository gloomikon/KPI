import React, { Component } from 'react';
import sha256 from 'crypto-js/sha256';
import { Header, LeftSideMenu, Footer } from './Statics'; 

class SignUpPage extends Component {
	constructor(props) {
		super(props);
		this.state = {
			login: '',
			fullname: '',
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
		event.preventDefault();
		let data = JSON.stringify({
			login: this.state.login,
			fullname: this.state.fullname,
			password: sha256(this.state.password).toString()});
		fetch('/reg', {
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
					<h1>It is a registration page</h1><br/> <hr/> <br/>
					<h3>Please, enter a login and password and your full name to create an account!</h3>
					<br/>
					<form onSubmit={this.handleSubmit} className="Form" id="signUp" style={{margin: 'auto', width: '300px', height:'310px'}}> 
						<input type="text" 
							placeholder=" Login" 
							name="login"
							value={this.state.login}
							onChange={this.handleChange} required/><br/>
						<input type="text" 
							placeholder=" Fullname" 
							name="fullname"
							value={this.state.fullname}
							onChange={this.handleChange} required/><br/>
						<input type="password" 
							name="password" 
							placeholder=' Password'
							value={this.state.password} 
							onChange={this.handleChange}/><br/>
						<button onClick={this.handleSubmit}>Submit</button> 
					</form>
					We are happy to have you with us! <br/>
					Keep it cool!<br/><br/>
				</li>
				<Footer />
			</ul>
		)
	}
}

export default SignUpPage;