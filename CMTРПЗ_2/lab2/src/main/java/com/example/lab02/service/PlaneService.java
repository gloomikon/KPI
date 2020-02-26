package com.example.lab02.service;

import com.example.lab02.entity.Plane;

public interface PlaneService {

	// Create empty table
	void createTable();

	// Add row to existing table
	void addRow(Plane plane);

	// Get info about Plane by ID
	Plane readRow(String id);

	// Update info about Plane
	void updateRow(Plane plane);

	// Delete Plane by ID
	void deleteRow(String id);

	// Delete table
	void dropTable();
}