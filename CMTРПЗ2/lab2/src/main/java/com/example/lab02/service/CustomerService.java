package com.example.lab02.service;

import com.example.lab02.entity.Customer;

public interface CustomerService {

	// Create empty table
	void createTable();

	// Add row to existing table
	void addRow(Customer customer);

	// Get info about Customer by ID
	Customer readRow(String id);

	// Update info about Customer
	void updateRow(Customer customer);

	// Delete Customer by ID
	void deleteRow(String id);

	// Delete table
	void dropTable();
}