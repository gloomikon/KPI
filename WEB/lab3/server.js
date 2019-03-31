var http = require('http');
var fs = require('fs');
var path = require('path');
const hostname = '0.0.0.0';
const port = 8080;

http.createServer(function (request, response) {
    
    var filePath = 'src' + request.url;
    if (filePath == 'src/')
        filePath = 'src/index.html';
    var extname = path.extname(filePath);    //returns the extension of the path, from the last occurrence of the .
    var contentType = 'text/html';
    switch (extname) {
        case '.js':
            file
            contentType = 'text/javascript';
            break;
        case '.css':
            contentType = 'text/css';
            break;
        case '.json':
            contentType = 'application/json';
            break;
        case '.png':
            contentType = 'image/png';
            break;      
        case '.jpg':
            contentType = 'image/jpg';
            break;
        case '.wav':
            contentType = 'audio/wav';
            break;
        case '.mp4':
            contentType = 'video/mp4';
            break;
        case '.mp3':
            contentType = 'audio/mp3';
            break;
    }
    console.log("FILEPATH = " + filePath);
    console.log("URL = " + request.url);

    fs.readFile(filePath, function(error, content) {

        response.writeHead(200, { 'Content-Type': contentType });
        response.end(content, 'utf-8');
    });

}).listen(port, hostname);
console.log('Server running at http://127.0.0.1:8080/');