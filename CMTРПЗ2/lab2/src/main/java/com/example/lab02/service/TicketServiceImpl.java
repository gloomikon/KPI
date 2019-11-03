package com.example.lab02.service;

import com.example.lab02.dao.TicketDAO;
import com.example.lab02.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketService {
	@Autowired
	TicketDAO ticketDAO;

	@Override
	public void createTable() {
		ticketDAO.createTable();
	}

	@Override
	public void addRow(Ticket ticket) {
		ticketDAO.addRow(ticket);
	}

	@Override
	public Ticket readRow(String id) {
		return ticketDAO.readRow(id);
	}

	@Override
	public void updateRow(Ticket ticket) {
		ticketDAO.updateRow(ticket);
	}

	@Override
	public void deleteRow(String id) {
		ticketDAO.deleteRow(id);
	}

	@Override
	public void dropTable() {
		ticketDAO.dropTable();
	}
}
