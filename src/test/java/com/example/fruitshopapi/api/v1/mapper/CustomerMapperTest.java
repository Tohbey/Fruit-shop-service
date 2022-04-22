package com.example.fruitshopapi.api.v1.mapper;

import com.example.fruitshopapi.api.v1.model.CustomerDTO;
import com.example.fruitshopapi.controllers.v1.CustomerController;
import com.example.fruitshopapi.domain.Customer;
import com.example.fruitshopapi.services.CustomerService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerMapperTest {

    public static final String FIRSTNAME = "Fafo";
    public static final String LASTNAME = "tobi";
    public static final String BASE_URL = "/api/v1/customers";
    public static final long ID = 1L;
    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    public void customerToCustomerDTO() {
        Customer customer = new Customer();
        customer.setFirstname(FIRSTNAME);
        customer.setLastname(LASTNAME);

        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        assertEquals(FIRSTNAME, customerDTO.getFirstname());
        assertEquals(LASTNAME, customerDTO.getLastname());
    }

    @Test
    public void customerDtoToCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRSTNAME);
        customerDTO.setLastname(LASTNAME);
        customerDTO.setCustomerUrl(BASE_URL + ID);

        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
        assertEquals(customerDTO.getFirstname(), customer.getFirstname());
        assertEquals(customerDTO.getLastname(), customer.getLastname());
    }
}