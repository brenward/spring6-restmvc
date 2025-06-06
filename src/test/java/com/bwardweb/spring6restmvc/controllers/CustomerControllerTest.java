package com.bwardweb.spring6restmvc.controllers;

import com.bwardweb.spring6restmvc.config.SpringSecurityConfig;
import com.bwardweb.spring6restmvc.model.CustomerDTO;
import com.bwardweb.spring6restmvc.services.CustomerService;
import com.bwardweb.spring6restmvc.services.CustomerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.core.Is.is;

@WebMvcTest(CustomerController.class)
@Import(SpringSecurityConfig.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CustomerService customerService;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<CustomerDTO> customerArgumentCaptor;

    CustomerServiceImpl customerServiceImpl;

    @BeforeEach
    void setUp(){
        customerServiceImpl = new CustomerServiceImpl();
    }

    @Test
    void testCreateCustomer() throws Exception {
        CustomerDTO customer = customerServiceImpl.listCustomers().get(0);
        customer.setId(null);
        customer.setVersion(null);

        given(customerService.createCustomer(any(CustomerDTO.class))).willReturn(customerServiceImpl.listCustomers().get(1));

        mockMvc.perform(post(CustomerController.CUSTOMER_PATH)
                        .with(BeerControllerTest.jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testUpdateCustomer() throws Exception {
        CustomerDTO customer = customerServiceImpl.listCustomers().get(0);

        given(customerService.updateById(any(),any())).willReturn(Optional.of(customer));

        mockMvc.perform(put(CustomerController.CUSTOMER_PATH_ID, customer.getId())
                        .with(BeerControllerTest.jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isNoContent());

        verify(customerService).updateById(any(UUID.class), any(CustomerDTO.class));

    }


    @Test
    void testGetCustomers() throws Exception {
        given(customerService.listCustomers()).willReturn(customerServiceImpl.listCustomers());

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH)
                        .with(BeerControllerTest.jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()",is(3)));
    }

    @Test
    void testCustomerNotFound() throws Exception {
        given(customerService.getById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID, UUID.randomUUID())
                        .with(BeerControllerTest.jwtRequestPostProcessor))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetCustomeryId() throws Exception {
        CustomerDTO cust = customerServiceImpl.listCustomers().get(0);

        given(customerService.getById(cust.getId())).willReturn(Optional.of(cust));

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID, cust.getId().toString())
                        .with(BeerControllerTest.jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.customerName", is(cust.getCustomerName())));
    }

    @Test
    void testDeleteCustomer() throws Exception {
        CustomerDTO cust = customerServiceImpl.listCustomers().get(0);

        given(customerService.deleteById(any())).willReturn(true);

        mockMvc.perform(delete(CustomerController.CUSTOMER_PATH_ID, cust.getId())
                        .with(BeerControllerTest.jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(customerService).deleteById(uuidArgumentCaptor.capture());

        assertEquals(cust.getId(), uuidArgumentCaptor.getValue());

    }

    @Test
    void testPatchCustomer() throws Exception {
        CustomerDTO cust = customerServiceImpl.listCustomers().get(0);

        Map<String,Object> customerMap = new HashMap<>();
        customerMap.put("customerName","New Name");

        given(customerService.patchById(any(),any())).willReturn(Optional.of(cust));

        mockMvc.perform(patch(CustomerController.CUSTOMER_PATH_ID, cust.getId())
                        .with(BeerControllerTest.jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerMap)))
                .andExpect(status().isNoContent());

        verify(customerService).patchById(uuidArgumentCaptor.capture(),customerArgumentCaptor.capture());

        assertEquals(cust.getId(),uuidArgumentCaptor.getValue());
        assertEquals(customerMap.get("customerName"), customerArgumentCaptor.getValue().getCustomerName());
    }
}