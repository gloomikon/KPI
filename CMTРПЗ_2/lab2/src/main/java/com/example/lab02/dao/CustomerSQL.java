package com.example.lab02.dao;
import com.example.lab02.entity.Customer;
import com.example.lab02.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerSQL implements CustomerDAO {
	@Autowired
	public JdbcTemplate jdbcTemplate;

	@Override
	public void createTable() {
		String sql = "create table customers(" +
				"id int PRIMARY KEY AUTO_INCREMENT," +
				"name text NOT NULL," +
				"surname text NOT NULL," +
				"passport text NOT NULL)";
		jdbcTemplate.execute(sql);
	}

	@Override
	public void addRow(Customer customer) {
		String sql = "insert into customers (name, surname, passport) values(?,?,?)";
		jdbcTemplate.update(sql, customer.getName(), customer.getSurname(), customer.getPassport());
	}

	@Override
	public Customer readRow(String id) {
		String sql = "select * from customers where id=?";
		return jdbcTemplate.queryForObject(sql, new CustomerMapper(), id);
	}

	@Override
	public void updateRow(Customer customer) {
		String sql = "update customers set name=?, surname=?, passport=? where id=?";
		jdbcTemplate.update(sql, customer.getName(), customer.getSurname(), customer.getPassport(),
				Integer.valueOf(customer.getId()));
	}

	@Override
	public void deleteRow(String id) {
		String sql = "delete from customers where id=?";
		jdbcTemplate.update(sql, Integer.valueOf(id));
	}

	@Override
	public void dropTable() {
		String sql = "drop table customers";
		jdbcTemplate.execute(sql);
	}
}
