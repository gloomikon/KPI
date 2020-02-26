package com.example.lab02.controller;

import com.example.lab02.entity.Ticket;
import com.example.lab02.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TicketController {
	@Autowired
	public TicketService ticketService;

	@PostMapping("/ticket")
	public Ticket addTicket(@RequestBody
							@RequestParam(value = "id", required = false) String id,
							@RequestParam(value = "customer_id", required = false) String customer_id,
							@RequestParam(value = "plane_id", required = false) String plane_id,
							@RequestParam(value = "place", required = false) String place) {
		Ticket ticket = new Ticket();
		ticket.setId(id);
		ticket.setCustomer_id(customer_id);
		ticket.setPlane_id(plane_id);
		ticket.setPlace(place);
		ticketService.addRow(ticket);
		return ticket;
	}

	@GetMapping("/ticket/{id}")
	public Ticket getTicket(@PathVariable String id) {
		return ticketService.readRow(id);
	}

	@PutMapping("/ticket/{id}")
	public Ticket updateTicket(@PathVariable String id,
							@RequestParam(value = "customer_id", required = false) String customer_id,
							@RequestParam(value = "plane_id", required = false) String plane_id,
							@RequestParam(value = "place", required = false) String place) {
		Ticket ticket = ticketService.readRow(id);
		ticket.setCustomer_id(customer_id);
		ticket.setPlane_id(plane_id);
		ticket.setPlace(place);
		ticketService.updateRow(ticket);
		return ticket;
	}

	@DeleteMapping("/ticket/{id}")
	public Ticket deleteTicket(@PathVariable String id) {
		Ticket ticket = ticketService.readRow(id);
		ticketService.deleteRow(id);
		return ticket;
	}
}
