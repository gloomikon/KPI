package com.example.lab02.dao;

import com.example.lab02.entity.Ticket;

public interface TicketDAO {

	// Create empty table
	void createTable();

	// Add row to existing table
	void addRow(Ticket ticket);

	// Get info about Ticket by ID
	Ticket readRow(String id);

	// Update info about Ticket
	void updateRow(Ticket ticket);

	// Delete Ticket by ID
	void deleteRow(String id);

	// Delete table
	void dropTable();
}