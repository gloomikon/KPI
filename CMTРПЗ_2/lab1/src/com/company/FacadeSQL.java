package com.company;

import com.company.entities.Customer;
import com.company.entities.Plane;
import com.company.entities.Ticket;
import com.company.sql.CustomerSQL;
import com.company.sql.PlaneSQL;
import com.company.sql.TicketSQL;

public class FacadeSQL {
	public static void createCustomers() {
		CustomerSQL customerSQL = new CustomerSQL();
		customerSQL.createTable();
		for (int i = 0; i < 10; i++) {
			Customer c = (Customer) Factory.createEntity(Entity.Customer);
			System.out.println(c);
			customerSQL.addRow(c);
		}
	}
	public static void createPlanes() {
		PlaneSQL planeSQL = new PlaneSQL();
		planeSQL.createTable();
		for (int i = 0; i < 5; i++) {
			Plane p = (Plane) Factory.createEntity(Entity.Plane);
			System.out.println(p);
			planeSQL.addRow(p);
		}
	}
	public static void createTickets() {
		TicketSQL ticketSQL = new TicketSQL();
		ticketSQL.createTable();
		for (int i = 0; i < 5; i++) {
			Ticket t = (Ticket) Factory.createEntity(Entity.Ticket);
			System.out.println(t);
			ticketSQL.addRow(t);
		}
	}
	public static void dropAllTables() {
		TicketSQL ticketSQL = new TicketSQL();
		CustomerSQL customerSQL = new CustomerSQL();
		PlaneSQL planeSQL = new PlaneSQL();
		ticketSQL.dropTable();
		customerSQL.dropTable();
		planeSQL.dropTable();
	}
}
