import React, { Component } from 'react';
import './App.css';
import { BrowserRouter as Router, Route, Switch, Redirect } from 'react-router-dom';
import ProfilePage from './ProfilePage';
import ContactsPage from './ContactsPage';
import AdminPage from './AdminPage';
import SigninPage from './SigninPage';
import SignupPage from './SignupPage';
import GroupsPage from './GroupsPage';
import GroupPage from './GroupPage';
import MenuPage from './MenuPage';

import './css/style.css';

class App extends Component {
	render() {
		return (
			<Router>
					<Switch>
						<Redirect exact from='/' to='/index' />
						<Route path='/profile' component={ProfilePage} />
						<Route path='/group/signin' component={SigninPage} />
						<Route path='/contacts' component={ContactsPage} />
						<Route path='/admin' component={AdminPage} />
						<Route path='/signin' component={SigninPage} />
						<Route path='/signup' component={SignupPage} />
						<Route path='/groups' component={GroupsPage} />
						<Route path='/index' component={MenuPage} />
						<Route path='/group/:id' component={GroupPage} />
					</Switch>
			</Router>
		)
	}
}


export default App;
