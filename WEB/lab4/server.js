/* IMPORTS */
const http = require('http');
const fs = require('fs');
const path = require('path');
const database = require(`${__dirname}/src/scripts/db.js`);
const Busboy = require('busboy');
const url = require('url');
const pug = require('pug');
const querystring  = require('querystring');

const hostname = '0.0.0.0';
const port = 8080;

let throwError = function(err) {
	err ? throwError(err) : 0;
}

/* WORK WITH DATABASE */
const contactsDB = database.contacts;
contactsDB.init((err) => {throwError(err)});
const postsDB = database.posts;
postsDB.init((err) => {throwError(err)});

function handleContactsForm(req, res) {
  console.log(req.headers);
	let success = true, record = {}, busboy = new Busboy({headers : req.headers});
	busboy.on('file', function(fieldname, file, filename, encoding, mimetype) {
		if (filename) {
			let filePath = path.join('./src/saves/files/', path.basename(filename));
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
		success ? contactsDB.insertRecord(record, throwError) : console.log("INVALID FORM");
		success ? 
			res.writeHead(303, {'Location' : `${req.url}?submited=true`}) :
			res.writeHead(302, {'Location' : `${req.url}?submited=false`});
		res.end();
	});
	req.pipe(busboy);
}

function handleProfileForm(req, res) {
	let textFlag = false, fileFlag = false, record = {}, busboy = new Busboy({headers : req.headers});
	busboy.on('file', function(fieldname, file, filename, encoding, mimetype) {
		if (filename) {
			let filePath = path.join('./src/saves/files/', path.basename(filename));
			let extension = getType(path.basename(filename).substr(path.basename(filename).lastIndexOf('.') + 1).toLowerCase());
			record['filePath'] = path.basename(filename);
			record['mediaType'] = extension;
			file.pipe(fs.createWriteStream(filePath));
			fileFlag = true;
		}
		else {
			file.resume();
		}
	});
	busboy.on('field', function(fieldname, val, fieldnameTruncated, encoding, mimetype) {
		record[fieldname] = val;
		textFlag = (val != "") ? true : textFlag;
	});
	busboy.on('finish', function() {
		record['time'] = (new Date()).toString();
		(textFlag || fileFlag) ? postsDB.insertRecord(record, throwError) : console.log("INVALID FORM");
		(textFlag || fileFlag) ? 
			res.writeHead(303, {'Location' : `${req.url}?submited=true`}) :
			res.writeHead(302, {'Location' : `${req.url}?submited=false`});
		res.end();
	});
	req.pipe(busboy);
}

/* DIFFERENT TYPEDEFS */
const types = {
	".js"	:	"text/javascript",
	".css"	:	"text/css",
	".pug"	:	"text/html",
	".html"	:	"text/html",
	".json"	:	"application/json",
	".png"	:	"image/png",
	".jpg"	:	"image/jpg",
	".jpeg"	:	"image/jpeg",
	".wav"	:	"audio/wav",
	".mp3"	:	"audio/mp3",
	".mp4"	:	"video/mp4",
	".webm"	:	"video/wemb",	
};

const	imgTypes	=	["png", "jpg", "jpeg"];
const	videoTypes	=	["mp4", "wemb"];
const	musicTypes	=	["wav", "mp3"];

function getType(extension) {
	console.log(`extension = ${extension}`);
	return (imgTypes.includes(extension)) ? "img" :
		(videoTypes.includes(extension)) ? "video" :
		(musicTypes.includes(extension)) ? "music" : "file";
}

/* FUNCTIONS FOR PAGES HANDLING */
const funcs = {
	"src/profile.html"	:	profile,
	"src/contacts.html"	:	contacts,
	"src/admin.html"	:	admin,
	"error"				:	error,
	"default" 			:	defaultGet,
};

function defaultGet(req, res) {
	let filePath = 'src' + url.parse(req.url).pathname;
	(filePath == 'src/') ? filePath = 'src/index.html' : filePath = filePath;
	let extname = path.extname(filePath);
	let contentType = types[extname] || "text/html";
	fs.readFile(filePath, function(error, content) {
		res.writeHead(200, { 'Content-Type': contentType });
		res.end(content, 'utf-8');
	});
}

function error(req, res) {
	let filePath = 'src/404.html';
	fs.readFile(filePath, function(error, content) {
		res.writeHead(200, { 'Content-Type' : 'text/html'});
		res.end(content, 'utf-8');
	});
}

function admin(req, res) {
	contactsDB.getRecords((list) => {
		console.log(list);
		let filePath = 'src/admin.pug';
		const compiledFunction = pug.compileFile(filePath);
		resp = compiledFunction({list: list});
		res.writeHead(200, { 'Content-Type': "text/html" });
		res.end(resp);
	});
}

function profile(req, res) {
	if (req.method == "GET") {
		postsDB.getRecords((list) => {
			console.log(list);
			list.sort(function(a, b) {
				let dateA = new Date(a['time']);
				let dateB = new Date(b['time']);
				return dateA = dateB; 
			})
			let filePath = 'src/profile.pug';
			const compiledFunction = pug.compileFile(filePath);
			let params = querystring.parse(url.parse(req.url).query);
			console.log(params);
			(params == null) ? 
				resp = compiledFunction({list: list}) :
				resp = compiledFunction({list: list,
										success : params['submited']});
			res.writeHead(200, { 'Content-Type': "text/html" });
			res.end(resp);
		});
	}
	else if (req.method == "POST") {
		handleProfileForm(req, res);
	}
}

function contacts(req, res) {
	if (req.method == "GET") {
		let filePath = ('src/contacts.pug');
		const compiledFunction = pug.compileFile(filePath);
		let params = querystring.parse(url.parse(req.url).query);
		(params == null) ? 
			resp = compiledFunction() :
			resp = compiledFunction({success : params['submited']});
		res.writeHead(200, { 'Content-Type': "text/html" });
		res.end(resp);
	}
	else if (req.method == "POST") {
		handleContactsForm(req, res);
	}
}

/* START */
http.createServer(function (req, res) {
	try {
		let filePath = 'src' + url.parse(req.url).pathname.replace('pug', 'html');
	(filePath == 'src/') ? filePath = 'src/index.html' : filePath = filePath;
	(filePath in funcs) ? funcs[filePath](req, res) : funcs['default'](req, res);
	}
	catch (error) {
		funcs['error'](req, res);
	}
}).listen(port, hostname, () => console.log(`Server works.`));