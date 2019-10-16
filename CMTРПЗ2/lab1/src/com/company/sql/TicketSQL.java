package com.company.sql;

import com.company.dao.TicketDAO;
import com.company.entities.Ticket;

import java.sql.*;

public class TicketSQL implements TicketDAO {
	private Connection connection;

	public TicketSQL() {
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
					"create table tickets(" +
							"id int PRIMARY KEY AUTO_INCREMENT," +
							"customer_id int NOT NULL," +
							"plane_id int NOT NULL," +
							"place text NOT NULL," +
							"constraint foreign key (customer_id)" +
							"references customers(id)" +
							"on update cascade on delete cascade," +
							"constraint foreign key (plane_id)" +
							"references planes(id)" +
							"on update cascade on delete cascade )");
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addRow(Ticket ticket) {
		PreparedStatement preparedStatement;
		String sql = "insert into tickets (customer_id, plane_id, place) values(?,?,?)";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, Integer.valueOf(ticket.getCustomer_id()));
			preparedStatement.setInt(2, Integer.valueOf(ticket.getPlane_id()));
			preparedStatement.setString(3, ticket.getPlace());
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Ticket readRow(String id) {
		PreparedStatement preparedStatement;
		Ticket ticket = new Ticket();
		String sql = "select * from tickets where id=?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, Integer.valueOf(id));
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				ticket.setId(Integer.toString(resultSet.getInt("id")));
				ticket.setCustomer_id(Integer.toString(resultSet.getInt("customer_id")));
				ticket.setPlane_id(Integer.toString(resultSet.getInt("plane_id")));
				ticket.setPlace(resultSet.getString("place"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ticket;
	}

	@Override
	public void updateRow(Ticket ticket) {
		PreparedStatement preparedStatement;
		String sql = "update tickets set customer_id=?, plane_id=?, place=? where id=?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, Integer.valueOf(ticket.getCustomer_id()));
			preparedStatement.setInt(2, Integer.valueOf(ticket.getPlane_id()));
			preparedStatement.setString(3, ticket.getPlace());
			preparedStatement.setInt(4, Integer.valueOf(ticket.getId()));
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteRow(String id) {
		PreparedStatement preparedStatement;
		String sql = "delete from tickets where id=?";
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
			preparedStatement = connection.prepareStatement("drop table tickets");
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
