const sqlite3 = require('sqlite3');

const db = new sqlite3.Database(`${__dirname}/../saves/db.db`, 
			sqlite3.OPEN_CREATE | sqlite3.OPEN_READWRITE, 
			(err) => {(err) ? console.log(err) : console.log("Connected to database");
		});

let dbInterface = {
	contacts : {
		init : function() {
			db.exec(`CREATE TABLE IF NOT EXISTS contacts (
				fullname		TEXT,
				organization	TEXT,
				app_type		TEXT,
				message			TEXT,
				filePath		TEXT);`);
		},
		getRecords : function(callback) {
			let list = [];
			db.each(`SELECT * FROM contacts;`, [], (err, rows) => {
				list.push(rows);
			}, () => callback(list));
		},
		insertRecord : function(record) {
			console.log("INSERTING");
			db.exec(`INSERT INTO contacts VALUES
			("${record['fullname']}", "${record['organization']}", "${record['app_type']}", "${record['message']}", "${record['filePath']}");`);
		},
	},
	posts: {
		init : function() {
			db.exec(`CREATE TABLE IF NOT EXISTS posts (
				message		TEXT,
				time		TEXT,
				filePath	TEXT,
				mediaType	TEXT);`);
		},
		getRecords : function(callback) {
			let list = [];
			db.each(`SELECT * FROM posts;`, [], (err, rows) => {
				list.push(rows);
			}, () => callback(list));
		},
		insertRecord : function(record) {
			console.log("INSERTING into RECORDS");
			let query = `INSERT INTO posts VALUES
			("${record['message']}", "${record['time']}", "${record['filePath']}", "${record['mediaType']}");`
			db.exec(query);
		},
	}
}

module.exports = dbInterface;