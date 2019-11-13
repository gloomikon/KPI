package com.company.controller;
import com.company.entity.Customer;
import com.company.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
	CustomerRepository customerRepository;

    @GetMapping("")
    public List<Customer> getUserById(@RequestParam(value = "id", required = false) Integer id) {

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

        return resultList;
    }
}
