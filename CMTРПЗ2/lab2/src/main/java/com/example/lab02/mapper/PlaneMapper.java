package com.example.lab02.mapper;

import com.example.lab02.entity.Plane;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaneMapper implements RowMapper<Plane> {
	public Plane mapRow(ResultSet resultSet, int i) throws SQLException {
		Plane plane = new Plane();
		plane.setId(Integer.toString(resultSet.getInt("id")));
		plane.setName(resultSet.getString("name"));
		plane.setCapacity(resultSet.getInt("capacity"));
		return plane;
	}
}