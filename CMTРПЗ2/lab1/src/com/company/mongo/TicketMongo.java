package com.company.mongo;

import com.company.dao.TicketDAO;
import com.company.entities.Ticket;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class TicketMongo implements TicketDAO {
	private MongoClient mongoClient;
	private MongoDatabase database;
	private MongoCollection<Document> collection;

	public TicketMongo() {
		String MongoDB_URL = "mongodb://localhost:27017";
		mongoClient = MongoClients.create(MongoDB_URL);
		database = mongoClient.getDatabase("lab1");
		collection = database.getCollection("tickets");
		System.out.println("Connected");
	}

	@Override
	public void createTable() { }

	@Override
	public void addRow(Ticket ticket) {
		Document document = new Document();
		document.append("customer_id", ticket.getCustomer_id());
		document.append("plane_id", ticket.getPlane_id());
		document.append("place", ticket.getPlace());
		collection.insertOne(document);
	}

	@Override
	public Ticket readRow(String id) {
		Ticket ticket = new Ticket();

		Document searchResult = collection.find(new Document("_id", new ObjectId(id))).first();
		if (searchResult != null) {
			ticket.setId(id);
			ticket.setCustomer_id(searchResult.get("customer_id").toString());
			ticket.setPlane_id(searchResult.get("plane_id").toString());
			ticket.setPlace(searchResult.get("place").toString());
			return ticket;
		}
		return null;
	}

	public List<Ticket> readRows() {
		List<Ticket> list = new ArrayList<>();
		for (Document document : collection.find()) {
			Ticket ticket = new Ticket();
			ticket.setId(document.get("_id").toString());
			ticket.setCustomer_id(document.get("customer_id").toString());
			ticket.setPlane_id(document.get("plane_id").toString());
			ticket.setPlace(document.get("place").toString());
			list.add(ticket);
		}
		return list;
	}

	@Override
	public void updateRow(Ticket ticket) {
		Document oldUser = collection.find(new Document("_id", new ObjectId(ticket.getId()))).first();

		Bson updatedValue = new Document("customer_id", ticket.getCustomer_id());
		((Document) updatedValue).append("plane_id", ticket.getPlane_id());
		((Document) updatedValue).append("place", ticket.getPlace());
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
