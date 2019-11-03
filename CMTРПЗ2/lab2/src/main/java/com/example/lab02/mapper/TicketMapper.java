package com.example.lab02.mapper;

import com.example.lab02.entity.Ticket;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketMapper implements RowMapper<Ticket> {
	public Ticket mapRow(ResultSet resultSet, int i) throws SQLException {
		Ticket ticket = new Ticket();
		ticket.setId(Integer.toString(resultSet.getInt("id")));
		ticket.setCustomer_id(Integer.toString(resultSet.getInt("customer_id")));
		ticket.setPlane_id(Integer.toString(resultSet.getInt("plane_id")));
		ticket.setPlace(resultSet.getString("place"));
		return ticket;
	}
}