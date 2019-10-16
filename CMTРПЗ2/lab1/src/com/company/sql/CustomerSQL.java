package com.company.sql;

import com.company.dao.CustomerDAO;
import com.company.entities.Customer;

import java.sql.*;

public class CustomerSQL implements CustomerDAO {

	private Connection connection;

	public CustomerSQL() {
		String DB_DRIVER = "com.mysql.jdbc.Driver";
		String DB_URL = "jdbc:mysql://localhost:3306/lab1";
		String DB_USERNAME = "root";
		String DB_PASSWORD = "12345";

		try {
			Class.forName(DB_DRIVER);
			this.connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			System.out.println("Connected!");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Connection ERROR");
		}
	}

	@Override
	public void createTable() {
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(
					"create table customers(" +
					"id int PRIMARY KEY AUTO_INCREMENT," +
					"name text NOT NULL," +
					"surname text NOT NULL," +
					"passport text NOT NULL)");
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addRow(Customer customer) {
		PreparedStatement preparedStatement;
		String sql = "insert into customers (name, surname, passport) values(?,?,?)";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, customer.getName());
			preparedStatement.setString(2, customer.getSurname());
			preparedStatement.setString(3, customer.getPassport());
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Customer readRow(String id) {
		PreparedStatement preparedStatement;
		Customer customer = new Customer();
		String sql = "select * from customers where id=?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, Integer.valueOf(id));
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				customer.setId(Integer.toString(resultSet.getInt("id")));
				customer.setName(resultSet.getString("name"));
				customer.setSurname(resultSet.getString("surname"));
				customer.setPassport(resultSet.getString("passport"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}

	@Override
	public void updateRow(Customer customer) {
		PreparedStatement preparedStatement;
		String sql = "update customers set name=?, surname=?, passport=? where id=?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, customer.getName());
			preparedStatement.setString(2, customer.getSurname());
			preparedStatement.setString(3, customer.getPassport());
			preparedStatement.setInt(4, Integer.valueOf(customer.getId()));
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteRow(String id) {
		PreparedStatement preparedStatement;
		String sql = "delete from customers where id=?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, Integer.valueOf(id));
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void dropTable() {
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement("drop table customers");
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
