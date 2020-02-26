package com.example.lab02.service;
import com.example.lab02.dao.PlaneDAO;
import com.example.lab02.entity.Plane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaneServiceImpl implements PlaneService {
	@Autowired
	PlaneDAO planeDAO;

	@Override
	public void createTable() {
		planeDAO.createTable();
	}

	@Override
	public void addRow(Plane plane) {
		planeDAO.addRow(plane);
	}

	@Override
	public Plane readRow(String id) {
		return planeDAO.readRow(id);
	}

	@Override
	public void updateRow(Plane plane) {
		planeDAO.updateRow(plane);
	}

	@Override
	public void deleteRow(String id) {
		planeDAO.deleteRow(id);
	}

	@Override
	public void dropTable() {
		planeDAO.dropTable();
	}
}
