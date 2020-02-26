package com.company.mongo;

import com.company.dao.PlaneDAO;
import com.company.entities.Plane;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class PlaneMongo implements PlaneDAO {
	private MongoClient mongoClient;
	private MongoDatabase database;
	private MongoCollection<Document> collection;

	public PlaneMongo() {
		String MongoDB_URL = "mongodb://localhost:27017";
		mongoClient = MongoClients.create(MongoDB_URL);
		database = mongoClient.getDatabase("lab1");
		collection = database.getCollection("planes");
		System.out.println("Connected");
	}

	@Override
	public void createTable() { }

	@Override
	public void addRow(Plane plane) {
		Document document = new Document();
		document.append("name", plane.getName());
		document.append("capacity", plane.getCapacity());
		collection.insertOne(document);
	}

	@Override
	public Plane readRow(String id) {
		Plane plane = new Plane();

		Document searchResult = collection.find(new Document("_id", new ObjectId(id))).first();
		if (searchResult != null) {
			plane.setId(id);
			plane.setName(searchResult.get("name").toString());
			plane.setCapacity((int)searchResult.get("capacity"));
			return plane;
		}
		return null;
	}

	public List<Plane> readRows() {
		List<Plane> list = new ArrayList<>();
		for (Document document : collection.find()) {
			Plane plane = new Plane();
			plane.setId(document.get("_id").toString());
			plane.setName(document.get("name").toString());
			plane.setCapacity((int)document.get("capacity"));
			list.add(plane);
		}
		return list;
	}

	@Override
	public void updateRow(Plane plane) {
		Document oldUser = collection.find(new Document("_id", new ObjectId(plane.getId()))).first();

		Bson updatedValue = new Document("name", plane.getName());
		((Document) updatedValue).append("capacity", plane.getCapacity());
		Bson updateOperation = new Document("$set", updatedValue);
		collection.updateOne(oldUser, updateOperation);
	}

	@Override
	public void deleteRow(String id) {
		Document delUser = collection.find(new Document("_id", new ObjectId(id))).first();
		collection.deleteOne(delUser);
	}

	@Override
	public void dropTable() {
		collection.deleteMany(new Document());
	}
}
