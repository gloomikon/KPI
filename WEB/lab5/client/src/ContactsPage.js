import React, { Component } from 'react';
import success from './media/success.png';
import failure from './media/failure.png';
import { Header, LeftSideMenu, Footer } from './Statics'; 

class ContactsPage extends Component {
	constructor(props) {
		super(props);
		this.state = {
			valid: null,
		}
		this.handleSubmit = this.handleSubmit.bind(this);
	}

	handleSubmit(event) {
		event.preventDefault();
		const data = new FormData(document.getElementById('contacts'));
		document.getElementById('contacts').reset();
		fetch('/contacts', {
			method: 'POST',
			body: data,
		}).then((res) => {
			(res.ok) ? this.setState({ valid : true }) : this.setState({ valid: false });
		}).catch((err) => {
			this.setState({ valid: false });
		});
	}

	render() {
		let validation = null;
		if (this.state.valid != null) {
			validation = this.state.valid ? <img src={success} alt='success' width='100px' />:
											<img src={failure} alt='failure' width='100px' />;
		}
		return (
			<ul className='flex-container'>
				<li className='flex-item header'>
					<Header isLoggedIn={this.state.isLoggedIn} />
				</li>
				<LeftSideMenu />
				<li className='flex-item main'>
					<section className="flex-item main_header">
						Contacts
					</section>
					<article className="flex-item content">
						<h1>Fill the form for feedback!</h1>
						<form id='contacts' onSubmit={this.handleSubmit} encType="multipart/form-data" >
							<p>Full name:</p><br/>
							<input type="text" name = "fullname" /> <br/>
							<p>Organisation:</p><br/>
							<input type="text" name = "organization" /> <br/>
							<p>Type of apllication:</p><br/>
							<select name="app_type">
								<option>Press</option>
								<option>Partership</option>
								<option>Another</option>
							</select> <br/>
							<p>Your message:</p><br/>
							<textarea name="message"></textarea><br/>
							<p>Attach file:</p><br/>
							<input type="file" name="file" /><br/>
							<input type = "submit" value="SEND" /><br/>
						</form>
						{validation}
						<br/>
						<br/>
						<br/>
						<pre>e-mail:		gloomikon@gmail.com <br/></pre>
						<pre>github:		<a href="https://github.com/gloomikon">gloomikon</a><br/></pre>
						<pre>telegram:	<a href="http://t.me/gloomikon">@gloomikon</a><br/></pre>
						<pre>instagram:	<a href="https://www.instagram.com/nikolay.zhurba/">nikolay.zhurba</a><br/></pre>
						<br/>
						<br/>
						<br/>
						<br/>
						<br/><br/>
					</article>
				</li>
				<Footer />
			</ul>
		)
	}
}

export default ContactsPage;