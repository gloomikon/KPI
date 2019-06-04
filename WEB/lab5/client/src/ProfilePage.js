import React, { Component } from 'react';
import avatar from './media/avatar.jpg'

class ProfilePage extends Component {
	constructor(props) {
		super(props);
		this.state = {
			user: null,
		}
	}

	render() {
		return (
			<div>
				<section class='flex-item main_header'>
					<ul>
						<li><img src={avatar} alt='avatar' className='avatar' /></li>
						<li>
							<p id='profile'><b>Nikolay Zhurba<br/><br/></b><i>Kiev, Ukraine<br/><br/></i>if you don't stand for something, you fall for everything</p>
						</li>
					</ul>
				</section>
				<article className='flext-item content'>
					<h1>Create new post</h1>
					<form id='feedback'>
						<p>Your message:</p><br/>
						<textarea name='message' /><br/>
						<p>Attach file:</p>
						<input type='file' name='file' /><br/>
						<input type='submit' value='SEND' />
					</form>
				</article>
			</div>
		)
	}
}

export default ProfilePage;