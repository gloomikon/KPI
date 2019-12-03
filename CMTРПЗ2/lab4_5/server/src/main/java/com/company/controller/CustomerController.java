package com.company.controller;
import com.company.entity.Customer;
import com.company.repository.CustomerRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
	CustomerRepository customerRepository;

    @GetMapping("")
    public String getUserById(@RequestParam(value = "id", required = false) Long id) {

        List<Customer> resultList = new ArrayList<>();
        if (id == null) {
            Iterable<Customer> result = customerRepository.findAll();
            result.forEach(resultList::add);
        }
        else {
            Optional<Customer> customer = customerRepository.findById(id);
            if (customer.isPresent()) {
                resultList.add(customer.get());
            }
        }
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = prettyGson.toJson(resultList);
        return prettyJson;
    }

    @PostMapping("")
    public Boolean addCustomer(@RequestBody
                                @RequestParam(value = "id", required = true) Long id,
                                @RequestParam(value = "name", required = true) String name,
                                @RequestParam(value = "url", required = true) String url) {
        Optional<Customer> test = customerRepository.findById(id);
        if (test.isPresent()) {
            return false;
        }
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName(name);
        customer.setUrl(url);
        customerRepository.save(customer);

        return true;
    }
}
