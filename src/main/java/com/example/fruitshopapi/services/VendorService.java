package com.example.fruitshopapi.services;

import com.example.fruitshopapi.api.v1.model.VendorDTO;
import com.example.fruitshopapi.api.v1.model.VendorListDTO;

import java.util.List;

public interface VendorService {
    VendorDTO getVendorById(Long id);

    List<VendorDTO> getAllVendors();

    VendorDTO createNewVendor(VendorDTO vendorDTO);

    VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO);

    VendorDTO patchVendor(Long id, VendorDTO vendorDTO);

    void deleteVendorById(Long id);
}
