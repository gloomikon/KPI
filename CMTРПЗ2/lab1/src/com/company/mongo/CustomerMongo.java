package com.company.mongo;

import com.company.dao.CustomerDAO;
import com.company.entities.Customer;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.List;

public class CustomerMongo implements CustomerDAO {
	private MongoClient mongoClient;
	private MongoDatabase database;
	private MongoCollection<Document> collection;

	public CustomerMongo() {
		String MongoDB_URL = "mongodb://localhost:27017";
		mongoClient = MongoClients.create(MongoDB_URL);
		database = mongoClient.getDatabase("lab1");
		collection = database.getCollection("users");
		System.out.println("Connected");
	}

	@Override
	public void createTable() { }

	@Override
	public void addRow(Customer customer) {
		Document document = new Document();
		document.append("name", customer.getName());
		document.append("surname", customer.getSurname());
		document.append("passport", customer.getPassport());
		collection.insertOne(document);
	}

	@Override
	public Customer readRow(String id) {
		Customer customer = new Customer();

		Document searchResult = collection.find(new Document("_id", new ObjectId(id))).first();
		if (searchResult != null) {
			customer.setId(id);
			customer.setName(searchResult.get("name").toString());
			customer.setSurname(searchResult.get("surname").toString());
			customer.setPassport(searchResult.getString("passport"));
			return customer;
		}
		return null;
	}

	public List<Customer> readRows() {
		List<Customer> list = new ArrayList<>();
		for (Document document : collection.find()) {
			Customer customer = new Customer();
			customer.setId(document.get("_id").toString());
			customer.setName(document.get("name").toString());
			customer.setSurname(document.get("name").toString());
			customer.setPassport(document.get("passport").toString());
			list.add(customer);
		}
		return list;
	}

	@Override
	public void updateRow(Customer customer) {
		Document oldUser = collection.find(new Document("_id", new ObjectId(customer.getId()))).first();

		Bson updatedValue = new Document("name", customer.getName());
		((Document) updatedValue).append("surname", customer.getSurname());
		((Document) updatedValue).append("passport", customer.getPassport());
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
