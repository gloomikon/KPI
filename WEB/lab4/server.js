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

function handlePostMethod(req, res, database) {
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
		success ? database.insertRecord(record, throwError) : console.log("INVALID FORM");
		success ? 
			res.writeHead(303, {'Location' : `${req.url}?submited=true`}) :
			res.writeHead(302, {'Location' : `${req.url}?submited=false`});
		res.end();
	});
	req.pipe(busboy);
}

/* WRITEHEAD TYPES */
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

/* FUNCTIONS FOR PAGES HANDLING */
const funcs = {
	"src/contacts.html"	:	contacts,
	"src/admin.html"	:	admin,
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

function contacts(req, res) {
	if (req.method == "GET") {
		let filePath = ('src/contacts.pug');
		const compiledFunction = pug.compileFile(filePath);
		let params = querystring.parse(url.parse(req.url).query);
		console.log(params);
		(params == null) ? 
			resp = compiledFunction() :
			resp = compiledFunction({success : params['submited']});
		res.writeHead(200, { 'Content-Type': "text/html" });
		res.end(resp);
	}
	else if (req.method == "POST") {
		handlePostMethod(req, res, contactsDB);
	}
}

/* START */
http.createServer(function (req, res) {
	let filePath = 'src' + url.parse(req.url).pathname.replace('pug', 'html');
	(filePath == 'src/') ? filePath = 'src/index.html' : filePath = filePath;
	(filePath in funcs) ? funcs[filePath](req, res) : funcs['default'](req, res);
}).listen(port, hostname, () => console.log(`Server works.`));