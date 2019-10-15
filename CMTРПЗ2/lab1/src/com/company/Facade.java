package com.company;

import com.company.entities.Customer;
import com.company.entities.Plane;
import com.company.entities.Ticket;
import com.company.sql.CustomerSQL;
import com.company.sql.PlaneSQL;
import com.company.sql.TicketSQL;

import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.Random;

public class Facade {
	private static String[] customerNames = {
			"Nikolay",
			"Alexandr",
			"Betty",
			"Maksym",
			"Andrey",
			"Artem",
			"Bogdan",
			"Nikita",
			"Vasya",
			"Ivan",
			"Zhenya",
			"Olesya",
			"Karina"
	};
	private static String[] customerSurnames = {
			"Zhurba",
			"Samoilenko",
			"Vik",
			"Smith",
			"Kolomijchenko",
			"Krivda",
			"Dudnik",
			"Neizhko",
			"Yashcyhenko"
	};
	private static String[] planeNames = {
			"Wright Flyer",
			"Supermarine Spitfire",
			"Boeing 787",
			"Lockheed SR-71 Blackbird",
			"Cirrus SR22",
			"Learjet 23",
			"Lockheed C-130",
			"Douglas DC-3",
			"Blériot XI",
			"Cessna 172",
			"Boeing B-29 Superfortress",
			"Gulfstream G500",
			"Boeing 747",
			"Bell X-1",
			"Airbus A320"
	};
	static private String generatePassport() {
		String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		SecureRandom rnd = new SecureRandom();
		StringBuilder sb = new StringBuilder(10);
		for(int i = 0; i < 10; i++)
			sb.append( AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}
	static private String generateName() {
		int rnd = new Random().nextInt(customerNames.length);
		return customerNames[rnd];
	}
	static private String generateSurname() {
		int rnd = new Random().nextInt(customerSurnames.length);
		return customerSurnames[rnd];
	}
	static private String generatePlaneName() {
		int rnd = new Random().nextInt(planeNames.length);
		return planeNames[rnd];
	}
	static private String generatePlace() {
		String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		SecureRandom rnd = new SecureRandom();
		StringBuilder sb = new StringBuilder(10);
		for(int i = 0; i < 2; i++)
			sb.append( AB.charAt(rnd.nextInt(26) + 10));
		for(int i = 0; i < 4; i++)
			sb.append( AB.charAt(rnd.nextInt(10)));
		return sb.toString();
	}

	public static Object createEntity(Entity entity) {
		if (entity == Entity.Customer) {
			Customer customer = new Customer();
			customer.setName(generateName());
			customer.setSurname(generateSurname());
			customer.setPassport(generatePassport());
			return customer;
		} else if (entity == Entity.Plane) {
			Plane plane = new Plane();
			plane.setName(generatePlaneName());
			plane.setCapacity((new Random().nextInt(100)) + 20);
			return plane;
		} else if (entity == Entity.Ticket) {
			Ticket ticket = new Ticket();
			ticket.setCustomer_id(new Random().nextInt(10) + 1);
			ticket.setPlane_id(new Random().nextInt(5) + 1);
			ticket.setPlace(generatePlace());
			return ticket;
		}
		return null;
	}

	public static void createCustomers() {
		CustomerSQL customerSQL = new CustomerSQL();
		customerSQL.createTable();
		for (int i = 0; i < 10; i++) {
			Customer c = (Customer) createEntity(Entity.Customer);
			System.out.println(c);
			customerSQL.addRow(c);
		}
	}
	public static void createPlanes() {
		PlaneSQL planeSQL = new PlaneSQL();
		planeSQL.createTable();
		for (int i = 0; i < 5; i++) {
			Plane p = (Plane) createEntity(Entity.Plane);
			System.out.println(p);
			planeSQL.addRow(p);
		}
	}
	public static void createTickets() {
		TicketSQL ticketSQL = new TicketSQL();
		ticketSQL.createTable();
		for (int i = 0; i < 5; i++) {
			Ticket t = (Ticket) createEntity(Entity.Ticket);
			System.out.println(t);
			ticketSQL.addRow(t);
		}
	}
}
