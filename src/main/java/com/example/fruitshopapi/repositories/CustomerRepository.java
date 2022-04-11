package com.example.fruitshopapi.repositories;

import com.example.fruitshopapi.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository  extends JpaRepository<Customer, Long> {
}

