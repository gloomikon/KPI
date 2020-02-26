package com.company.controller;
import com.company.entity.Ticket;
import com.company.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/tickets")
public class TicketController {
    @Autowired
    TicketRepository ticketRepository;

    @GetMapping("")
    public List<Ticket> getUserById(@RequestParam(value = "id", required = false) Integer id) {

        List<Ticket> resultList = new ArrayList<>();
        if (id == null) {
            Iterable<Ticket> result = ticketRepository.findAll();
            result.forEach(resultList::add);
        }
        else {
            Optional<Ticket> customer = ticketRepository.findById(id);
            if (customer.isPresent()) {
                resultList.add(customer.get());
            }
        }
        return resultList;
    }
    @PostMapping("")
    public Ticket addTicket(@RequestBody
                            @RequestParam(value = "id", required = false) Integer id,
                            @RequestParam(value = "customer_id", required = false) String customer_id,
                            @RequestParam(value = "plane_id", required = false) String plane_id,
                            @RequestParam(value = "place", required = false) String place) {
        Ticket ticket = new Ticket();
        ticket.setId(id);
        ticket.setCustomer_id(customer_id);
        ticket.setPlane_id(plane_id);
        ticket.setPlace(place);
        ticketRepository.save(ticket);
        return ticket;
    }
}
