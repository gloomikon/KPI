package com.company;

import com.company.entities.Customer;
import com.company.entities.Plane;
import com.company.entities.Ticket;
import com.company.mongo.CustomerMongo;
import com.company.mongo.PlaneMongo;
import com.company.mongo.TicketMongo;

public class FacadeMongo {
	public static void createCustomers() {
		CustomerMongo customerMongo = new CustomerMongo();
		customerMongo.createTable();
		for (int i = 0; i < 10; i++) {
			Customer c = (Customer) Factory.createEntity(Entity.Customer);
			System.out.println(c);
			customerMongo.addRow(c);
		}
	}
	public static void createPlanes() {
		PlaneMongo planeMongo = new PlaneMongo();
		planeMongo.createTable();
		for (int i = 0; i < 5; i++) {
			Plane p = (Plane) Factory.createEntity(Entity.Plane);
			System.out.println(p);
			planeMongo.addRow(p);
		}
	}
	public static void createTickets() {
		TicketMongo ticketMongo = new TicketMongo();
		ticketMongo.createTable();
		for (int i = 0; i < 5; i++) {
			Ticket t = (Ticket) Factory.createEntity(Entity.Ticket);
			System.out.println(t);
			ticketMongo.addRow(t);
		}
	}
	public static void dropAllTables() {
		TicketMongo ticketMongo = new TicketMongo();
		CustomerMongo customerMongo = new CustomerMongo();
		PlaneMongo planeMongo = new PlaneMongo();
		ticketMongo.dropTable();
		customerMongo.dropTable();
		planeMongo.dropTable();
	}
}