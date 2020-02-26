package com.example.lab02.service;

import com.example.lab02.dao.CustomerDAO;
import com.example.lab02.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService{
	@Autowired
	CustomerDAO customerDAO;

	@Override
	public void createTable() {
		customerDAO.createTable();
	}

	@Override
	public void addRow(Customer customer) {
		customerDAO.addRow(customer);
	}

	@Override
	public Customer readRow(String id) {
		return customerDAO.readRow(id);
	}

	@Override
	public void updateRow(Customer customer) {
		customerDAO.updateRow(customer);
	}

	@Override
	public void deleteRow(String id) {
		customerDAO.deleteRow(id);
	}

	@Override
	public void dropTable() {
		customerDAO.dropTable();
	}
}
