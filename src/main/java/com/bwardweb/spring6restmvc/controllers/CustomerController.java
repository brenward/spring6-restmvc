package com.bwardweb.spring6restmvc.controllers;

import com.bwardweb.spring6restmvc.model.Customer;
import com.bwardweb.spring6restmvc.services.CustomerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Customer> handlePost(@RequestBody Customer customer){
        Customer savedCustomer = customerService.createCustomer(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer/" + savedCustomer.getId().toString());
        return new ResponseEntity<Customer>(savedCustomer,headers, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> getCustomers(){
        return customerService.listCustomers();
    }

    @RequestMapping(value = "{customerId}", method = RequestMethod.GET)
    public Customer getCustomeryId(@PathVariable("customerId") UUID customerId){
        return customerService.getById(customerId);
    }

    @PutMapping("{customerId}")
    public ResponseEntity updateById(@PathVariable UUID customerId, @RequestBody Customer customer){
        customerService.updateById(customerId, customer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{customerId}")
    public ResponseEntity deleteById(@PathVariable UUID customerId){
        customerService.deleteById(customerId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("{customerId}")
    public ResponseEntity patchById(@PathVariable UUID customerId, @RequestBody Customer customer){
        customerService.patchById(customerId, customer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
