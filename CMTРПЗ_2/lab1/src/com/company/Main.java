package com.company;

public class Main {
	public static void main(String[] args) {
		FacadeSQL.dropAllTables();
		FacadeSQL.createCustomers();
		FacadeSQL.createPlanes();
		FacadeSQL.createTickets();

		FacadeMongo.dropAllTables();
		FacadeMongo.createCustomers();
		FacadeMongo.createPlanes();
		FacadeMongo.createTickets();
	}
}
