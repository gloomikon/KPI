const sqlite3 = require('sqlite3');

const db = new sqlite3.Database(`${__dirname}/../saves/db.db`, 
			sqlite3.OPEN_CREATE | sqlite3.OPEN_READWRITE, 
			(err) => {(err) ? console.log(err) : console.log("Connected to database");
		});

let dbInterface = {
	contacts : {
		init : function(callback) {
			db.run(`CREATE TABLE IF NOT EXISTS contacts (
				id INTEGER PRIMARY KEY,
				name TEXT,
				org TEXT,
				type TEXT,
				body TEXT,
				filePath TEXT);` , callback);
		},
		getRecords : function(callback) {
			db.all(`SELECT * FROM contacts;`, [], (err, rows) => {
				callback(err, rows);
			});
		},
		insertRecord : function(record, callback) {
			console.log(`Inserting ${record}`);
			decodeURI.run(`INSERT INTO contacts
			(name, orgm type, body, filePath) VALUES
			${record['name']}, ${record['org']}, ${record['type']}, ${record['body']}, ${record['filePath']};`, [], callback);
		},
	},
}

module.exports = dbInterface;