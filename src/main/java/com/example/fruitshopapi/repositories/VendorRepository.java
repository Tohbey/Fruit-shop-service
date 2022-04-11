package com.example.fruitshopapi.repositories;

import com.example.fruitshopapi.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
}
