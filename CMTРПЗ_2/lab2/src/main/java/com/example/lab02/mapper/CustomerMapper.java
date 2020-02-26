package com.example.lab02.mapper;

import com.example.lab02.entity.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerMapper implements RowMapper<Customer> {
	public Customer mapRow(ResultSet resultSet, int i) throws SQLException {
		Customer customer = new Customer();
		customer.setId(Integer.toString(resultSet.getInt("id")));
		customer.setName(resultSet.getString("name"));
		customer.setSurname(resultSet.getString("surname"));
		customer.setPassport(resultSet.getString("passport"));
		return customer;
	}
}