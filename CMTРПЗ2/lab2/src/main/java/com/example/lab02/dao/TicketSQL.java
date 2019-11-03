package com.example.lab02.dao;
import com.example.lab02.entity.Ticket;
import com.example.lab02.mapper.TicketMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class TicketSQL implements TicketDAO {
	@Autowired
	public JdbcTemplate jdbcTemplate;

	@Override
	public void createTable() {
		String sql = "create table tickets(" +
				"id int PRIMARY KEY AUTO_INCREMENT," +
				"customer_id int NOT NULL," +
				"plane_id int NOT NULL," +
				"place text NOT NULL," +
				"constraint foreign key (customer_id)" +
				"references customers(id)" +
				"on update cascade on delete cascade," +
				"constraint foreign key (plane_id)" +
				"references planes(id)" +
				"on update cascade on delete cascade )";
		jdbcTemplate.execute(sql);
	}

	@Override
	public void addRow(Ticket ticket) {
		String sql = "insert into tickets (customer_id, plane_id, place) values(?,?,?)";
		jdbcTemplate.update(sql, Integer.valueOf(ticket.getCustomer_id()),
				Integer.valueOf(ticket.getPlane_id()), ticket.getPlace());
	}

	@Override
	public Ticket readRow(String id) {
		String sql = "select * from tickets where id=?";
		return jdbcTemplate.queryForObject(sql, new TicketMapper(), id);
	}

	@Override
	public void updateRow(Ticket ticket) {
		String sql = "update tickets set customer_id=?, plane_id=?, place=? where id=?";
		jdbcTemplate.update(sql, Integer.valueOf(ticket.getCustomer_id()),
				Integer.valueOf(ticket.getPlane_id()), ticket.getPlace(),
				Integer.valueOf(ticket.getId()));
	}

	@Override
	public void deleteRow(String id) {
		String sql = "delete from tickets where id=?";
		jdbcTemplate.update(sql, Integer.valueOf(id));
	}

	@Override
	public void dropTable() {
		String sql = "drop table tickets";
		jdbcTemplate.execute(sql);
	}
}
