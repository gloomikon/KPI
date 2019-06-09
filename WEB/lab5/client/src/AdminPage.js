import React, { Component } from 'react';
import { Header, LeftSideMenu, Footer } from './Statics'; 

class ContactsPage extends Component {
	constructor(props) {
		super(props);
		const token = localStorage.getItem('token');
		this.state = {
			isLoggedIn: token ? true: false,
			data: null,
		}
	}

	componentDidMount() {
		fetch('/admin')
			.then(res => res.json())
			.then((data) => this.setState({ data: data }))
			.catch((err) => console.log(err));
	}

	renderOneRecord(record) {
		fetch(`/${record.filePath}`)
			.then((res) => res.blob())
			.then((img) => {
				const objUrl = URL.createObjectURL(img);
				const imgTag = document.getElementById(record.filePath);
				imgTag.src = objUrl;
			});
		return (
			<div>
				<p>Name: {record.fullname}</p>
				<p>Organization: {record.organization}</p>
				<p>Type: {record.type}</p>
				<p>Text: {record.message}</p>
				<br/>
				<img src={null} alt={record.filePath} id={record.filePath} width='300px' />
				<hr/><br/>
			</div>
		);
	}

	render() {
		let records = [];
		if (this.state.data != null) {
			for (let record of this.state.data) {
				records.push(this.renderOneRecord(record));
			}
		}
		return (
			<ul className='flex-container'>
				<li className='flex-item header'>
					<Header isLoggedIn={this.state.isLoggedIn} />
				</li>
				<LeftSideMenu />
				<li className='flex-item main'>
				<section className="flex-item main_header">
					Admin Page
				</section>
				<article className="flex-item content">
					<h1>All records</h1>
					{records}
				</article>
				</li>
				<Footer />
			</ul>
		)
	}
}

export default ContactsPage;