package com.example.fruitshopapi.controllers.v1;

import com.example.fruitshopapi.api.v1.model.CustomerDTO;
import com.example.fruitshopapi.api.v1.model.VendorDTO;
import com.example.fruitshopapi.api.v1.model.VendorListDTO;
import com.example.fruitshopapi.controllers.RestResponseEntityExceptionHandler;
import com.example.fruitshopapi.services.ResourceNotFoundException;
import com.example.fruitshopapi.services.VendorService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VendorControllerTest extends AbstractRestControllerTest{

    public static final String NAME_1 = "My Vendor";
    public static final long ID_1 = 1L;
    public static final String NAME_2 = "My Vendor";
    public static final long ID_2 = 1L;

    @Mock
    VendorService vendorService;

    @InjectMocks
    VendorController vendorController;

    MockMvc mockMvc;

    private VendorDTO getVendor1(){
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME_1);
        vendorDTO.setVendorUrl(VendorController.BASE_URL +"/"+ID_1);

        return vendorDTO;
    }

    private VendorDTO getVendor2(){
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME_2);
        vendorDTO.setVendorUrl(VendorController.BASE_URL +"/"+ID_2);

        return vendorDTO;
    }

    @Before
    public void setup() throws Exception{
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void getVendorList() throws Exception {
        VendorDTO vendorDTO1 = getVendor1();
        VendorDTO vendorDTO2 = getVendor2();

        when(vendorService.getAllVendors()).thenReturn(Arrays.asList(vendorDTO1, vendorDTO2));

        mockMvc.perform(get(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors",hasSize(2)));
    }

    @Test
    public void getVendorById() throws Exception {
        VendorDTO vendorDTO1 = getVendor1();

        when(vendorService.getVendorById(anyLong())).thenReturn(vendorDTO1);

        mockMvc.perform(get(VendorController.BASE_URL +"/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME_1)));
    }

    @Test
    public void createNewVendor() throws Exception{
        VendorDTO vendor = new VendorDTO();
        vendor.setName("vendor 1");
        vendor.setVendorUrl(VendorController.BASE_URL +"/1");


        VendorDTO returnDTO = new VendorDTO();
        returnDTO.setName(vendor.getName());
        returnDTO.setVendorUrl(vendor.getVendorUrl());

        when(vendorService.createNewVendor(vendor)).thenReturn(returnDTO);

        mockMvc.perform(post(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .contentType(asJsonString(vendor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo("vendor 1")));
    }

    @Test
    public void updateVendor() throws Exception{
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("vendor 1");

        VendorDTO returnDTO = new VendorDTO();
        returnDTO.setName(vendorDTO.getName());
        returnDTO.setVendorUrl(VendorController.BASE_URL +"/1");

        when(vendorService.saveVendorByDTO(anyLong(), any(VendorDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(put(VendorController.BASE_URL +"/1")
                .contentType(MediaType.APPLICATION_JSON)
                .contentType(asJsonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("vendor 1")))
                .andExpect(jsonPath("$.vendor_url",equalTo(VendorController.BASE_URL+"/1")));
    }

    @Test
    public void patchVendor() throws Exception{
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("vendor 1");

        VendorDTO returnDTO = new VendorDTO();
        returnDTO.setName(vendorDTO.getName());
        returnDTO.setVendorUrl(VendorController.BASE_URL +"/2");

        when(vendorService.patchVendor(anyLong(), any(VendorDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(patch(VendorController.BASE_URL +"/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(asJsonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("vendor 1")))
                .andExpect(jsonPath("$.vendor_url",equalTo(VendorController.BASE_URL+"/2")));
    }

    @Test
    public void deleteVendor() throws Exception{
        mockMvc.perform(delete(VendorController.BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(vendorService).deleteVendorById(anyLong());
    }

    @Test
    public void testNotFoundException() throws Exception {

        when(vendorService.getVendorById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(CustomerController.BASE_URL + "/222")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}