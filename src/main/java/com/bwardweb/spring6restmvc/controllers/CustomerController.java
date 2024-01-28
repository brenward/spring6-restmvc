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
@RequestMapping()
public class CustomerController {
    public static final String CUSTOMER_PATH = "/api/v1/customer";
    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{customerId}";

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(CUSTOMER_PATH)
    public ResponseEntity<Customer> handlePost(@RequestBody Customer customer){
        Customer savedCustomer = customerService.createCustomer(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", CUSTOMER_PATH + "/" + savedCustomer.getId().toString());
        return new ResponseEntity<Customer>(savedCustomer,headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = CUSTOMER_PATH, method = RequestMethod.GET)
    public List<Customer> getCustomers(){
        return customerService.listCustomers();
    }

    @RequestMapping(value = CUSTOMER_PATH_ID, method = RequestMethod.GET)
    public Customer getCustomeryId(@PathVariable("customerId") UUID customerId){
        return customerService.getById(customerId);
    }

    @PutMapping(CUSTOMER_PATH_ID)
    public ResponseEntity updateById(@PathVariable UUID customerId, @RequestBody Customer customer){
        customerService.updateById(customerId, customer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(CUSTOMER_PATH_ID)
    public ResponseEntity deleteById(@PathVariable UUID customerId){
        customerService.deleteById(customerId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(CUSTOMER_PATH_ID)
    public ResponseEntity patchById(@PathVariable UUID customerId, @RequestBody Customer customer){
        customerService.patchById(customerId, customer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
