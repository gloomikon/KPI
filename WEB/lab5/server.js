const MongoClient = require('mongodb').MongoClient;
const mongodbUrl = 'mongodb://localhost:27017/database';
const http = require('http');
const Busboy = require('busboy');
const path = require('path');
const fs = require('fs');

const hostname = '0.0.0.0';
const port = 8080;

const funcList = {
	'/contacts'	:	contacts,
}

function contacts(req, res) {
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
		if (success) {
			console.log('NICE');
			res.writeHead(200, {'Content-Type': 'text/plain'});
			res.write('Nice');
			res.end();
		} else {
			console.log('BAD');
			res.writeHead(400, {'Content-Type': 'text/plain'});
			res.write('BAD');
			res.end();
		}
		res.end();
	});
	req.pipe(busboy);
}

http.createServer(function(req, res) {
	const url = req.url;
	funcList[url](req, res);
}).listen(port, hostname, () => console.log('Сервер поднят'));