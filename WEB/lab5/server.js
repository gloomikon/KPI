const MongoClient = require('mongodb').MongoClient;
const mongodbUrl = 'mongodb://localhost:27017/database';
const http = require('http');
const Busboy = require('busboy');

const funcList = {
	'/contacts'	:	constacts,
}

function contacts(req, res) {
	
}

const server = http.createServer(function(req, res) {
	const url = req.url;
	funcList[url](req, res);
})