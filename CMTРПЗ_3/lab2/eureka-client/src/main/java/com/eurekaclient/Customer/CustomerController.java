package com.eurekaclient.Customer;

import com.eurekaclient.GetResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class CustomerController {
    @Autowired
    CustomerRepository customerRepository;

    @Value("${eureka.instance.instance-id}")
    private String instanceId;

    @RequestMapping(path="/customers", method = RequestMethod.GET)
    public @ResponseBody
    GetResponse<Iterable<Customer>> getAll() {
        return new GetResponse<Iterable<Customer>>(customerRepository.findAll(), instanceId);
    }

    @RequestMapping(path="customers/{id}", method = RequestMethod.GET)
    public @ResponseBody GetResponse<Optional<Customer>> getById(@PathVariable int id) {
        return new GetResponse<Optional<Customer>>(customerRepository.findById(id), instanceId);
    }

    @RequestMapping(path="/customers", method = RequestMethod.POST)
    public GetResponse<Boolean> addCustomer(@RequestParam(value = "id", required = true) Integer id,
                                            @RequestParam(value = "name", required = true) String name,
                                            @RequestParam(value = "url", required = true) String url) {
        Optional<Customer> test = customerRepository.findById(id);
        if (test.isPresent()) {
            return new GetResponse<Boolean>(false, instanceId);
        }
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName(name);
        customer.setUrl(url);
        customerRepository.save(customer);

        return new GetResponse<Boolean>(true, instanceId);
    }

    @RequestMapping(path="/customers", method = RequestMethod.PUT)
    public GetResponse<Boolean>  updateCustomer(@RequestParam(value = "id", required = true) Integer id,
                                                @RequestParam(value = "name", required = true) String name,
                                                @RequestParam(value = "url", required = true) String url) {
        Optional<Customer> test = customerRepository.findById(id);
        if (!test.isPresent()) {
            return new GetResponse<Boolean>(false, instanceId);
        }
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName(name);
        customer.setUrl(url);
        customerRepository.save(customer);

        return new GetResponse<Boolean>(true, instanceId);
    }

    @RequestMapping(path="/customers", method = RequestMethod.DELETE)
    public GetResponse<Boolean> deleteCustomer(@RequestParam(value = "id", required = true) Integer id) {
        Optional<Customer> test = customerRepository.findById(id);
        if (!test.isPresent()) {
            return new GetResponse<Boolean>(false, instanceId);
        }
        customerRepository.deleteById(id);
        return new GetResponse<Boolean>(true, instanceId);
    }
}