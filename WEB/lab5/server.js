const MongoClient = require('mongodb').MongoClient;
const ObjectID = require('mongodb').ObjectID;
const mongodbUrl = 'mongodb://localhost:27017/database';
const http = require('http');
const Busboy = require('busboy');
const path = require('path');
const url = require('url');
const fs = require('fs');
const cors = require('cors');
const jwt = require('jsonwebtoken');
const querystring = require('querystring');
const secret = 'first thought — best thought';
const hostname = '0.0.0.0';
const port = 8080;

function date() {
	const currentDate = new Date();
	const date = currentDate.getDate();
	const month = currentDate.getMonth();
	const year = currentDate.getFullYear();
	const hours = currentDate.getHours();
	const mins = currentDate.getMinutes();
	const dateString = date + '.' + (month + 1) + '.' + year + '  ' + hours + ':' + mins;
	return dateString;
}

const funcList = {
	'/contacts'			:	contacts,
	'/admin'			:	admin,
	'/reg'				:	signup,
	'/login'			:	signin,
	'/profilename'		:	profilename,
	'/profileposts'		:	profileposts,
	'/addpost'			:	addpost,
	'/deletePost'		:	deletePost,
	'/updatePost'		:	updatePost,
	'/getgroups'		:	getgroups,
	'/creategroup'		:	creategroup,
	'/getgroupinfo'		:	getgroupinfo,
	'/addusertogroup'	:	addusertogroup,
	'/deluserfromgroup'	:	deluserfromgroup,
	'/addgrpost'		:	addgrpost,
	'/getgrposts'		:	getgrposts,
	'/deletegrPost'		:	deletegrPost,
	'/updategrPost'		:	updategrPost,
	'default'			:	defaultf,
};

const types = {
	"js"	:	"text/javascript",
	"css"	:	"text/css",
	"pug"	:	"text/html",
	"html"	:	"text/html",
	"json"	:	"application/json",
	"png"	:	"image/png",
	"jpg"	:	"image/jpg",
	"jpeg"	:	"image/jpeg",
	"wav"	:	"audio/wav",
	"mp3"	:	"audio/mp3",
	"mp4"	:	"video/mp4",
	"webm"	:	"video/wemb",	
};

function getgrposts(req, res) {
	let group = querystring.parse(url.parse(req.url).query).group;
	MongoClient.connect(mongodbUrl, function(err, db) {
		if (err) throw err;
		const dbo = db.db('database');
		dbo.collection('groupsposts').find({'groupid': group}).toArray((err, result) => {
			if (err) throw err;
			res.setHeader('Content-Type', 'application/json');
			res.end(JSON.stringify(result));
			db.close();
		});
	});
}

function addgrpost(req, res) {
	let textFlag = false, fileFlag = false, record = {}, busboy = new Busboy({headers : req.headers});
	busboy.on('file', function(fieldname, file, filename, encoding, mimetype) {
		if (filename) {
			let filePath = path.join('./client/src/files/', path.basename(filename));
			record['filePath'] = path.basename(filename);
			file.pipe(fs.createWriteStream(filePath));
			fileFlag = true;
		}
		else {
			file.resume();
		}
	});
	busboy.on('field', function(fieldname, val, fieldnameTruncated, encoding, mimetype) {
		record[fieldname] = val;
		textFlag = (val != "" && fieldname != 'login' && fieldname != 'groupid') ? true : textFlag;
	});
	busboy.on('finish', function() {
		record['time'] = date();
		if (textFlag || fileFlag) {
			MongoClient.connect(mongodbUrl, function(err, db) {
				if (err) throw err;
				const dbo = db.db('database');
				dbo.collection('groupsposts').insertOne(record, (err, result) => {
					if (err) throw err;
					db.close();
				});
			});
			res.writeHead(200, {'Content-Type': 'text/plain'});
			res.write('Nice');
			res.end();
		} else {
			res.writeHead(400, {'Content-Type': 'text/plain'});
			res.write('BAD');
			res.end();
		}
	});
	req.pipe(busboy);
}

function deluserfromgroup(req, res) {
	let { group, user } = querystring.parse(url.parse(req.url).query);
	MongoClient.connect(mongodbUrl, function(err, db) {
		if (err) throw err;
		const dbo = db.db('database');
		dbo.collection('users').findOne({ 'login': user }, (err, result) => {
			if (err) throw err;
			if (result) {
				console.log(2);
				dbo.collection('groups').findOne({'_id' : ObjectID(group), 'users' : user }, (err, result) => {
					if (err) throw err;
					if (result) {
						console.log(3);
						dbo.collection('groups').updateOne({ '_id': ObjectID(group) } ,
						{ $pull: {'users' : user }}, function(err, result) {
							if (err) throw err;
							else {
								res.writeHead(200, {'Content-Type': 'text/plain'});
								res.end('User added');
								db.close();
							}
						});
					}
					else {
						res.writeHead(400, {'Content-Type': 'text/plain'});
						res.end('No such user');
						db.close();
					}
				})
			}
			else {
				res.writeHead(400, {'Content-Type': 'text/plain'});
				res.end('No such user');
				db.close();
			};
		});
	});
}

function addusertogroup(req, res) {
	let { group, user } = querystring.parse(url.parse(req.url).query);
	MongoClient.connect(mongodbUrl, function(err, db) {
		if (err) throw err;
		const dbo = db.db('database');
		dbo.collection('groups').findOne({'_id' : ObjectID(group), 'users' : user }, (err, result) => {
			if (err) throw err;
			if (result) {
				res.writeHead(400, {'Content-Type': 'text/plain'});
				res.end('User already in group');
				db.close();
			}
			else {
				dbo.collection('users').findOne({ 'login': user }, (err, result) => {
					if (err) throw err;
					if (result) {
						dbo.collection('groups').updateOne({ '_id': ObjectID(group) } ,
						{ $push: {'users' : user }}, function(err, result) {
							if (err) throw err;
							else {
								res.writeHead(200, {'Content-Type': 'text/plain'});
								res.end('User added');
								db.close();
							}
						});
					}
					else {
						res.writeHead(400, {'Content-Type': 'text/plain'});
						res.end('No such user');
						db.close();
					}
				})
			};
		});
	});
}

function getgroupinfo(req, res) {
	let group = querystring.parse(url.parse(req.url).query).group;
	MongoClient.connect(mongodbUrl, function(err, db) {
		if (err) throw err;
		const dbo = db.db('database');
		dbo.collection('groups').findOne({'_id': ObjectID(group)}, (err, result) => {
			if (err) throw err;
			res.setHeader('Content-Type', 'application/json');
			res.end(JSON.stringify(result));
			db.close();
		});
	});
}

function creategroup(req, res) {
	let body = '';
	req.on('data', function(data) {
		body += data;
		if (body.length > 1000000)
			req.connection.destroy();
	});
	req.on('end', function() {
		const {admin, group}  = JSON.parse(body);
		MongoClient.connect(mongodbUrl, function(err, db) {
			if (err) throw err;
			const dbo = db.db('database');
			dbo.collection('groups').findOne({ 'name' : group }, function(err, result) {
				if (err) throw err;
				if (result) {
					res.writeHead(400, {'Content-Type': 'text/plain'});
					res.end('Group alredy exists!');
					db.close();
				}
				else {
					dbo.collection('groups').insertOne({ 'admin' : admin, 'name' : group, 'users':[admin]},
					function(err, result) {
						if (err) throw err;
						res.writeHead(200, {'Content-Type': 'text/plain'});
						res.end('User alredy exists!');
						db.close();
					});
				}
			})
		});
	});
}

function getgroups(req, res) {
	let login = querystring.parse(url.parse(req.url).query).login;
	MongoClient.connect(mongodbUrl, function(err, db) {
		if (err) throw err;
		const dbo = db.db('database');
		dbo.collection('groups').find({'users': login}).toArray((err, result) => {
			if (err) throw err;
			res.setHeader('Content-Type', 'application/json');
			res.end(JSON.stringify(result));
			db.close();
		});
	});
}

function deletePost(req, res) {
	let body = '';
	req.on('data', function(data) {
		body += data;
		if (body.length > 1000000)
			req.connection.destroy();
	});
	req.on('end', function() {
		const rem = JSON.parse(body);
		MongoClient.connect(mongodbUrl, function(err, db) {
			if (err) throw err;
			const dbo = db.db('database');
			dbo.collection('posts').remove({ '_id': ObjectID(rem.id)})
		});
		res.writeHead(200, {'Content-Type': 'text/plain'});
		res.end('User alredy exists!');
	});	
}

function deletegrPost(req, res) {
	let body = '';
	req.on('data', function(data) {
		body += data;
		if (body.length > 1000000)
			req.connection.destroy();
	});
	req.on('end', function() {
		const rem = JSON.parse(body);
		MongoClient.connect(mongodbUrl, function(err, db) {
			if (err) throw err;
			const dbo = db.db('database');
			dbo.collection('groupsposts').remove({ '_id': ObjectID(rem.id)})
		});
		res.writeHead(200, {'Content-Type': 'text/plain'});
		res.end('User alredy exists!');
	});	
}

function updatePost(req, res) {
	let body = '';
	req.on('data', function(data) {
		body += data;
		if (body.length > 1000000)
			req.connection.destroy();
	});
	req.on('end', function() {
		const upd = JSON.parse(body);
		MongoClient.connect(mongodbUrl, function(err, db) {
			if (err) throw err;
			const dbo = db.db('database');
			dbo.collection('posts').updateOne({ '_id': ObjectID(upd.id)}, 
				{ $set: { 'message' : upd.text } }, function(err, result) {
					if (err) throw err
			})})
		res.writeHead(200, {'Content-Type': 'text/plain'});
		res.end('User alredy exists!');
	});
}

function updategrPost(req, res) {
	let body = '';
	req.on('data', function(data) {
		body += data;
		if (body.length > 1000000)
			req.connection.destroy();
	});
	req.on('end', function() {
		const upd = JSON.parse(body);
		MongoClient.connect(mongodbUrl, function(err, db) {
			if (err) throw err;
			const dbo = db.db('database');
			dbo.collection('groupsposts').updateOne({ '_id': ObjectID(upd.id)}, 
				{ $set: { 'message' : upd.text } }, function(err, result) {
					if (err) throw err
			})})
		res.writeHead(200, {'Content-Type': 'text/plain'});
		res.end('User alredy exists!');
	});
}

function addpost(req, res) {
	let textFlag = false, fileFlag = false, record = {}, busboy = new Busboy({headers : req.headers});
	busboy.on('file', function(fieldname, file, filename, encoding, mimetype) {
		if (filename) {
			let filePath = path.join('./client/src/files/', path.basename(filename));
			record['filePath'] = path.basename(filename);
			file.pipe(fs.createWriteStream(filePath));
			fileFlag = true;
		}
		else {
			file.resume();
		}
	});
	busboy.on('field', function(fieldname, val, fieldnameTruncated, encoding, mimetype) {
		record[fieldname] = val;
		textFlag = (val != "" && fieldname != 'login') ? true : textFlag;
	});
	busboy.on('finish', function() {
		record['time'] = date();
		if (textFlag || fileFlag) {
			MongoClient.connect(mongodbUrl, function(err, db) {
				if (err) throw err;
				const dbo = db.db('database');
				dbo.collection('posts').insertOne(record, (err, result) => {
					if (err) throw err;
					db.close();
				});
			});
			res.writeHead(200, {'Content-Type': 'text/plain'});
			res.write('Nice');
			res.end();
		} else {
			res.writeHead(400, {'Content-Type': 'text/plain'});
			res.write('BAD');
			res.end();
		}
	});
	req.pipe(busboy);
}

function profilename(req, res) {
	let body = '';
	let login = querystring.parse(url.parse(req.url).query).login;
	MongoClient.connect(mongodbUrl, function(err, db) {
		if (err) throw err;
		const dbo = db.db('database');
		dbo.collection('users').findOne({ login: login }, function(err, result) {
			if (err) throw err;
			res.setHeader('Content-Type', 'text/plain');
			res.end(result.fullname);
			db.close();
		});
	});
}

function profileposts(req, res) {
	let login = querystring.parse(url.parse(req.url).query).login;
	MongoClient.connect(mongodbUrl, function(err, db) {
		if (err) throw err;
		const dbo = db.db('database');
		dbo.collection('posts').find({'login': login}).toArray((err, result) => {
			if (err) throw err;
			res.setHeader('Content-Type', 'application/json');
			res.end(JSON.stringify(result));
			db.close();
		});
	});
}

function signup(req, res) {
	let body = '';
	req.on('data', function(data) {
		body += data;
		if (body.length > 1000000)
			req.connection.destroy();
	});
	req.on('end', function() {
		const registration = JSON.parse(body);
		MongoClient.connect(mongodbUrl, function(err, db) {
			if (err) throw err;
			const dbo = db.db('database');
			dbo.collection('users').findOne({ login: registration.login },
				function(err, result) {
					if (err) throw err;
					if (result) {
						res.writeHead(400, {'Content-Type': 'text/plain'});
						res.end('User alredy exists!');
						db.close();
					}
					else {
						dbo.collection('users').insertOne(registration, function(err, result) {
							if (err) throw err;
							let token = jwt.sign({login: registration.login}, secret);
							res.setHeader('Content-Type', 'application/json');
							res.end(token);
							db.close();
						});
					}
				});
		});
	});
}

function signin(req, res) {
	let body = '';
	req.on('data', function(data) {
		body += data;
		if (body.length > 1000000)
			req.connection.destroy();
	});
	req.on('end', function() {
		const registration = JSON.parse(body);
		MongoClient.connect(mongodbUrl, function(err, db) {
			if (err) throw err;
			const dbo = db.db('database');
			dbo.collection('users').findOne({ login: registration.login, 
				password: registration.password }, function(err, result) {
					if (err) throw err;
					if (result) {
						const token = jwt.sign({login: result.login}, secret);
						res.setHeader('Content-Type', 'application/json');
						res.end(token);
						db.close();
					}
					else {
						res.writeHead(400, {'Content-Type': 'text/plain'});
						res.write('Invalid login or password!\n');
						res.end();
						db.close();
					}
				})
		})
	})
}

function contacts(req, res) {
	let success = true, record = {}, busboy = new Busboy({headers : req.headers});
	busboy.on('file', function(fieldname, file, filename, encoding, mimetype) {
		if (filename) {
			let filePath = path.join('./client/src/files/', path.basename(filename));
			record['filePath'] = path.basename(filename);
			file.pipe(fs.createWriteStream(filePath));
		}
		else {
			success = false;
			file.resume();
		}
	});
	busboy.on('field', function(fieldname, val, fieldnameTruncated, encoding, mimetype) {
		record[fieldname] = val;
		success = (val == "") ? false : success;
	});
	busboy.on('finish', function() {
		if (success) {
			MongoClient.connect(mongodbUrl, (err, db) => {
				if (err) throw err;
				let dbo = db.db('database');
				dbo.collection('contacts').insertOne(record, (err, result) => {
					if (err) throw err;
					db.close();
				});
			});
			res.writeHead(200, {'Content-Type': 'text/plain'});
			res.write('Nice');
			res.end();
		} else {
			res.writeHead(400, {'Content-Type': 'text/plain'});
			res.write('BAD');
			res.end();
		}
	});
	req.pipe(busboy);
}

function admin(req, res) {
	MongoClient.connect(mongodbUrl, (err, db) => {
		if (err) throw err;
		let dbo = db.db('database');
		dbo.collection('contacts').find({}).toArray((err, result) => {
			if (err) throw err;
			res.setHeader('Content-Type', 'application/json');
			res.end(JSON.stringify(result));
			db.close();
		});
	});
}

function defaultf(req, res) {
	const type = types[req.url.split('.').pop()];
	fs.readFile('client/src/files' + req.url, (err, data) => {
		if (err) throw err;
		res.setHeader('Content-Type', type);
		res.end(data);
	});
}

http.createServer(function(req, res) {
	let urlpath = url.parse(req.url).pathname;
	(urlpath in funcList) ? funcList[urlpath](req, res) : funcList['default'](req, res);
}).listen(port, hostname, () => console.log('Сервер поднят'));