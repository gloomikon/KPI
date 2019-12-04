package com.company.controller;
import com.company.entity.TicketAdmin;
import com.company.entity.TicketUser;
import com.company.repository.TicketRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/tickets")
public class TicketController {
    @Autowired
    TicketRepository ticketRepository;

    @GetMapping("user")
    public String getUserTickets(@RequestParam(value = "user_id", required = false) Long user_id) {

        List<TicketUser> resultList = ticketRepository.getUserTickets(user_id);
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = prettyGson.toJson(resultList);
        return prettyJson;
    }

    @GetMapping("admin")
    public String getAdminTickets() {

        List<TicketAdmin> resultList = ticketRepository.getAdminTickets();
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = prettyGson.toJson(resultList);
        return prettyJson;
    }
}
