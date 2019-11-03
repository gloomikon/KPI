package com.example.lab02.controller;

import com.example.lab02.entity.Customer;
import com.example.lab02.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {
	@Autowired
	public CustomerService customerService;

	@GetMapping("/customer/{id}")
	public Customer getCustomer(@PathVariable String id) {
		return customerService.readRow(id);
	}

	@PostMapping("/customer")
	public Customer addCustomer(@RequestBody
							@RequestParam(value = "id", required = false) String id,
							@RequestParam(value = "name", required = false) String name,
							@RequestParam(value = "surname", required = false) String surname,
							@RequestParam(value = "passport", required = false) String passport) {
		Customer customer = new Customer();
		customer.setId(id);
		customer.setName(name);
		customer.setSurname(surname);
		customer.setPassport(passport);
		customerService.addRow(customer);
		return customer;
	}

	@PutMapping("/customer/{id}")
	public Customer updateCustomer(@PathVariable String id,
							@RequestParam (value = "name", required = false) String name,
							@RequestParam (value = "surname", required = false) String surname,
							@RequestParam (value = "passport", required = false) String passport) {
		Customer customer = customerService.readRow(id);
		customer.setName(name);
		customer.setSurname(surname);
		customer.setPassport(passport);
		customerService.updateRow(customer);
		return customer;
	}

	@DeleteMapping("/customer/{id}")
	public Customer deleteCustomer(@PathVariable String id) {
		Customer customer = customerService.readRow(id);
		customerService.deleteRow(id);
		return customer;
	}
}
