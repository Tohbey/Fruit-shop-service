package com.example.fruitshopapi.services;

import com.example.fruitshopapi.api.v1.mapper.CustomerMapper;
import com.example.fruitshopapi.api.v1.model.CustomerDTO;
import com.example.fruitshopapi.api.v1.model.ProductDTO;
import com.example.fruitshopapi.domain.Customer;
import com.example.fruitshopapi.domain.Product;
import com.example.fruitshopapi.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class CustomerServiceImplTest {

    private static final long ID_1 = 1L;
    private static final String FIRSTNAME_1 = "first name 1";
    private static final String LASTNAME_1 = "last name 1";

    private static final long ID_2 = 1L;
    private static final String FIRSTNAME_2 = "first name 2";
    private static final String LASTNAME_2 = "last name 2";

    @Mock
    CustomerRepository customerRepository;

    CustomerService customerService;

    @Before
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);

        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
    }

    private Customer getCustomer1(){
        Customer customer = new Customer();
        customer.setId(ID_1);
        customer.setLastname(LASTNAME_1);
        customer.setFirstname(FIRSTNAME_1);

        return customer;
    }

    private Customer getCustomer2(){
        Customer customer = new Customer();
        customer.setId(ID_2);
        customer.setLastname(LASTNAME_2);
        customer.setFirstname(FIRSTNAME_2);

        return customer;
    }

    @Test
    public void getAllCustomers() {
        List<Customer> customers = Arrays.asList( getCustomer1(), getCustomer2());
        given(customerRepository.findAll()).willReturn(customers);

        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

        then(customerRepository).should(times(1)).findAll();
        assertThat(customerDTOS.size(), is(equalTo(2)));
    }

    @Test
    public void getCustomerById() {
        Customer customer = getCustomer1();

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        CustomerDTO customerDTO = customerService.getCustomerById(1L);

        assertEquals(LASTNAME_1, customerDTO.getLastname());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getProductByIdNotFound() {
        given(customerRepository.findById(anyLong())).willReturn(Optional.empty());

        CustomerDTO customerDTO = customerService.getCustomerById(1L);

        then(customerRepository).should(times(1)).findById(anyLong());
    }

    @Test
    public void createNewCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastname(LASTNAME_1);

        Customer customer = getCustomer1();

        given(customerRepository.save(any(Customer.class))).willReturn(customer);

        CustomerDTO savedCustomerDTO = customerService.createNewCustomer(customerDTO);

        then(customerRepository).should().save(any(Customer.class));
        assertThat(savedCustomerDTO.getCustomerUrl(), containsString("1"));
    }

    @Test
    public void saveCustomerByDTO() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastname(LASTNAME_1);

        Customer customer = getCustomer1();

        given(customerRepository.save(any(Customer.class))).willReturn(customer);

        CustomerDTO savedCustomerDTO = customerService.saveCustomerByDTO(ID_1, customerDTO);
        assertThat(savedCustomerDTO.getFirstname(), containsString("1"));
    }

    @Test
    public void patchCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastname(LASTNAME_1);

        Customer customer = getCustomer1();

        given(customerRepository.findById(anyLong())).willReturn(Optional.of(customer));
        given(customerRepository.save(any(Customer.class))).willReturn(customer);

        CustomerDTO savedCustomerDTO = customerService.patchCustomer(ID_1, customerDTO);

        then(customerRepository).should().save(any(Customer.class));
        then(customerRepository).should(times(1)).findById(anyLong());
        assertThat(savedCustomerDTO.getLastname(), containsString("1"));
    }

    @Test
    public void deleteCustomerById() {
        customerService.deleteCustomerById(1L);

        then(customerRepository).should().deleteById(anyLong());
    }
}