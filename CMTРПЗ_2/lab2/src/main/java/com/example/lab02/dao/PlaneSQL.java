package com.example.lab02.dao;
import com.example.lab02.entity.Plane;
import com.example.lab02.mapper.PlaneMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PlaneSQL implements PlaneDAO {
	@Autowired
	public JdbcTemplate jdbcTemplate;

	@Override
	public void createTable() {
		String sql = "create table planes(" +
				"id int PRIMARY KEY AUTO_INCREMENT," +
				"name text NOT NULL," +
				"capacity int NOT NULL)";
		jdbcTemplate.execute(sql);
	}

	@Override
	public void addRow(Plane plane) {
		String sql = "insert into planes (name, capacity) values(?,?)";
		jdbcTemplate.update(sql, plane.getName(), plane.getCapacity());
	}

	@Override
	public Plane readRow(String id) {
		String sql = "select * from planes where id=?";
		return jdbcTemplate.queryForObject(sql, new PlaneMapper(), id);
	}

	@Override
	public void updateRow(Plane plane) {
		String sql = "update planes set name=?, capacity=? where id=?";
		jdbcTemplate.update(sql, plane.getName(), plane.getCapacity(), Integer.valueOf(plane.getId()));
	}

	@Override
	public void deleteRow(String id) {
		String sql = "delete from planes where id=?";
		jdbcTemplate.update(sql, Integer.valueOf(id));
	}

	@Override
	public void dropTable() {
		String sql = "drop table planes";
		jdbcTemplate.execute(sql);
	}
}