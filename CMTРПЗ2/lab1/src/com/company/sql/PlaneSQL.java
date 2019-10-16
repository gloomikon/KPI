package com.company.sql;

import com.company.dao.PlaneDAO;
import com.company.entities.Plane;

import java.sql.*;

public class PlaneSQL implements PlaneDAO {
	private Connection connection;

	public PlaneSQL() {
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
					"create table planes(" +
							"id int PRIMARY KEY AUTO_INCREMENT," +
							"name text NOT NULL," +
							"capacity int NOT NULL)");
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addRow(Plane plane) {
		PreparedStatement preparedStatement;
		String sql = "insert into planes (name, capacity) values(?,?)";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, plane.getName());
			preparedStatement.setInt(2, plane.getCapacity());
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Plane readRow(String id) {
		PreparedStatement preparedStatement;
		Plane plane = new Plane();
		String sql = "select * from planes where id=?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, Integer.valueOf(id));
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				plane.setId(Integer.toString(resultSet.getInt("id")));
				plane.setName(resultSet.getString("name"));
				plane.setCapacity(resultSet.getInt("capacity"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return plane;
	}

	@Override
	public void updateRow(Plane plane) {
		PreparedStatement preparedStatement;
		String sql = "update planes set name=?, capacity=? where id=?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, plane.getName());
			preparedStatement.setInt(2, plane.getCapacity());
			preparedStatement.setInt(3, Integer.valueOf(plane.getId()));
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteRow(String id) {
		PreparedStatement preparedStatement;
		String sql = "delete from planes where id=?";
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
			preparedStatement = connection.prepareStatement("drop table planes");
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
