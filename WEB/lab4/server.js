const http = require('http');
const fs = require('fs');
const path = require('path');
const database = require(`${__dirname}/src/scripts/db.js`);
const Busboy = require('busboy');
const url = require('url');

const hostname = '0.0.0.0';
const port = 8080;

const contactsDB = database.contacts;
contactsDB.init((err) => {throwError(err)});

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

const funcs = {
	"src/contacts.html"	:	contacts,
	"default" 			:	defaultGet,
};

function defaultGet(req, res) {
	let filePath = 'src' + url.parse(req.url).pathname;
	if (filePath == 'src/')
		filePath = 'src/index.html';
	let extname = path.extname(filePath);    //returns the extension of the path, from the last occurrence of the .
	let contentType = types[extname] || "text/html";

	//console.log("FILEPATH = " + filePath);
	//console.log("URL = " + req.url);

	fs.readFile(filePath, function(error, content) {
		res.writeHead(200, { 'Content-Type': contentType });
		res.end(content, 'utf-8');
	});
}

function contacts(req, res) {
	if (req.method == "GET") {
		console.log("get");
		defaultGet(req, res);
	}
	else if (req.method == "POST") {
		console.log("post");
		let busboy = new Busboy({headers : req.headers});
		let fields = [];
		busboy.on('file', function(fieldname, file, filename, encoding, mimetype) {
			if (filename) {
				let filePath = path.join('./saves/files/', path.basename(filename));
				console.log(`FILEPATH = ${filePath}`);
				fields.push(filePath);
				file.pipe(fs.createWriteStream(filePath));
			}
			else {
				console.log("NO FILE");
				file.resume();
			}
		});
		busboy.on('field', function(fieldname, val, fieldnameTruncated, encoding, mimetype) {
			console.log("Fiels parse");
			fields.push(val);
		});
		busboy.on('finish', function() {
			contactsDB.insertRecord(fields, throwError);
			console.log("Success");
			res.writeHead(303, { Connection: 'close', Location: '/contacts.html' });
			res.end();
		});
		req.pipe(busboy);
	}
}

let throwError = function(err) {
	if (err) {
		throw err;
	}
}


http.createServer(function (req, res) {
	console.log(req.method);
	let filePath = 'src' + url.parse(req.url).pathname;
	if (filePath == 'src/')
		filePath = 'src/index.html';
	(filePath in funcs) ?  funcs[filePath](req, res) : funcs['default'](req, res);
}).listen(port, hostname, () => console.log(`Server works.`));