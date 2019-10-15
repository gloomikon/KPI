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
		this.connection = null;

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
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(
					"create table customers(" +
					"id int PRIMARY KEY," +
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

		String sql = "INSERT INTO customers (id, name, surname, passport) VALUES(?,?,?,?)";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, customer.getId());
			preparedStatement.setString(2, customer.getName());
			preparedStatement.setString(3, customer.getSurname());
			preparedStatement.setString(4, customer.getPassport());
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Customer readRow(int id) {
		PreparedStatement preparedStatement;
		Customer customer = new Customer();
		String sql = "select * from customers where id=?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				customer.setId(resultSet.getInt("id"));
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
			preparedStatement.setInt(4, customer.getId());
			System.out.println(preparedStatement);
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteRow(int id) {
		PreparedStatement preparedStatement;

		String sql = "delete from customers where id=?";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
