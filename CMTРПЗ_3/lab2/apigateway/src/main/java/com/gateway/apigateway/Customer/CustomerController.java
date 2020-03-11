package com.gateway.apigateway.Customer;

import com.gateway.apigateway.GetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Optional;

@Controller
public class CustomerController {

    @Autowired
    CustomerClient customerClient;

    @RequestMapping(path = "" , method = RequestMethod.GET)
    public @ResponseBody
    GetResponse<Iterable<Customer>> getAll() {
        return customerClient.getAll();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public @ResponseBody GetResponse<Optional<Customer>> getById(@PathVariable int id) {
        return customerClient.getById(id);
    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    public @ResponseBody
    GetResponse<Boolean> addCustomer(@RequestParam(value = "id", required = true) Integer id,
                                     @RequestParam(value = "age", required = true) Integer age,
                                     @RequestParam(value = "name", required = true) String name,
                                     @RequestParam(value = "lastname", required = true) String lastname,
                                     @RequestParam(value = "address", required = true) String address,
                                     @RequestParam(value = "phonenumber", required = true) String phonenumber,
                                     @RequestParam(value = "email", required = true) String email,
                                     @RequestParam(value = "url", required = true) String url) {
        return customerClient.addCustomer(id, age, name, lastname, address, phonenumber, email, url);
    }

    @RequestMapping(path = "", method = RequestMethod.PUT)
    public @ResponseBody
    GetResponse<Boolean> updateCustomer(@RequestParam(value = "id", required = true) Integer id,
                                        @RequestParam(value = "age", required = true) Integer age,
                                        @RequestParam(value = "name", required = true) String name,
                                        @RequestParam(value = "lastname", required = true) String lastname,
                                        @RequestParam(value = "address", required = true) String address,
                                        @RequestParam(value = "phonenumber", required = true) String phonenumber,
                                        @RequestParam(value = "email", required = true) String email,
                                        @RequestParam(value = "url", required = true) String url) {
        return customerClient.updateCustomer(id, age, name, lastname, address, phonenumber, email, url);
    }

    @RequestMapping(path = "", method = RequestMethod.DELETE)
    public @ResponseBody
    GetResponse<Boolean> deleteCustomer(@RequestParam(value = "id", required = true) Integer id) {
        return customerClient.deleteCustomer(id);
    }
}
