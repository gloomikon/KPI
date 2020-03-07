package com.eurekaclient.Customer;

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

    @Value("${eureka.instance.instanceId}")
    private Integer instanceId;

    @GetMapping("/instance")
    public Integer getInstanceId() {
        return instanceId;
    }

    @GetMapping("/customers")
    public String getUserById(@RequestParam(value = "id", required = false) Integer id) {

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
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = prettyGson.toJson(resultList);
        return prettyJson;
    }

    @PostMapping("/customers")
    public Boolean addCustomer(@RequestBody
                               @RequestParam(value = "id", required = true) Integer id,
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

    @PutMapping("/customers")
    public Boolean updateCustomer(@RequestBody
                               @RequestParam(value = "id", required = true) Integer id,
                               @RequestParam(value = "name", required = true) String name,
                               @RequestParam(value = "url", required = true) String url) {
        Optional<Customer> test = customerRepository.findById(id);
        if (!test.isPresent()) {
            return false;
        }
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName(name);
        customer.setUrl(url);
        customerRepository.save(customer);

        return true;
    }

    @DeleteMapping("/customers")
    public Boolean deleteCustomer(@RequestBody
                              @RequestParam(value = "id", required = true) Integer id) {
        Optional<Customer> test = customerRepository.findById(id);
        if (!test.isPresent()) {
            return false;
        }
        customerRepository.deleteById(id);
        return true;
    }
}