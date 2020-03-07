package com.gateway.apigateway.Customer;

import com.gateway.apigateway.GetResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Optional;

@FeignClient(value = "client")
@RequestMapping(path="/customers")
public interface CustomerClient {

    @RequestMapping(path = "", method = RequestMethod.GET)
    public @ResponseBody
    GetResponse<Iterable<Customer>> getAll();

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public @ResponseBody
    GetResponse<Optional<Customer>> getById(@PathVariable int id);

    @RequestMapping(path = "", method = RequestMethod.POST)
    public @ResponseBody
    GetResponse<Boolean> addCustomer(@RequestParam(value = "id", required = true) Integer id,
                                     @RequestParam(value = "name", required = true) String name,
                                     @RequestParam(value = "url", required = true) String url);

    @RequestMapping(path = "", method = RequestMethod.PUT)
    public @ResponseBody
    GetResponse<Boolean> updateCustomer(@RequestParam(value = "id", required = true) Integer id,
                                        @RequestParam(value = "name", required = true) String name,
                                        @RequestParam(value = "url", required = true) String url);

    @RequestMapping(path = "", method = RequestMethod.DELETE)
    public @ResponseBody
    GetResponse<Boolean> deleteCustomer(@RequestParam(value = "id", required = true) Integer id);
}