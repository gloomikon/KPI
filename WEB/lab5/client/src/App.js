import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import { BrowserRouter as Router, Route, Switch, Redirect } from 'react-router-dom';
import { Header , LeftSideMenu, Footer } from './Statics';
import ProfilePage from './ProfilePage';
import './css/style.css';

class App extends Component {
	render() {
		return (
			<Router>
				<ul className='flex-container'>
					<li className='flex-item header'>
						<Header />
					</li>
					<LeftSideMenu />
					<li className='flex-item main'>
						<Switch>
							<Redirect exact from='/' to='/index' />
							{/* <Route exact path='/' component={IndexPage} /> */}
							<Route path='/profile' component={ProfilePage} />
						</Switch>
					</li>
					<Footer />
				</ul>
			</Router>
		)
	}
}

export default App;
