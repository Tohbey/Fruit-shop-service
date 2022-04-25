package com.example.fruitshopapi.controllers.v1;

import com.example.fruitshopapi.api.v1.model.ProductDTO;
import com.example.fruitshopapi.controllers.RestResponseEntityExceptionHandler;
import com.example.fruitshopapi.domain.Product;
import com.example.fruitshopapi.services.ProductService;
import com.example.fruitshopapi.services.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

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

public class ProductControllerTest extends AbstractRestControllerTest{

    @Mock
    ProductService productService;

    @InjectMocks
    ProductController productController;

    MockMvc mockMvc;

    public static final String NAME_1 = "Product 1";
    public static final Double PRICE_1 = 5.67;

    public static final String NAME_2 = "Product 2";
    public static final Double PRICE_2 = 7.67;

    private ProductDTO getProduct1(){
        ProductDTO product = new ProductDTO();
        product.setName(NAME_1);
        product.setCategoryURL(CategoryController.BASE_URL +"/1");
        product.setVendorURL(VendorController.BASE_URL +"/1");
        product.setProductURL(ProductController.BASE_URL +"/1");
        product.setPrice(PRICE_1);

        return product;
    }

    private ProductDTO getProduct2(){
        ProductDTO product = new ProductDTO();
        product.setName(NAME_2);
        product.setCategoryURL(CategoryController.BASE_URL +"/1");
        product.setVendorURL(VendorController.BASE_URL +"/1");
        product.setProductURL(ProductController.BASE_URL +"/1");
        product.setPrice(PRICE_2);

        return product;
    }

    @Before
    public void setup() throws Exception{
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void getListOfProduct() throws Exception {
        ProductDTO productDTO1 = getProduct1();
        ProductDTO productDTO2 = getProduct2();

        when(productService.getAllProducts()).thenReturn(Arrays.asList(productDTO2, productDTO1));

        mockMvc.perform(get(ProductController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products", hasSize(2)));
    }

    @Test
    public void getProductById() throws Exception {
        ProductDTO productDTO1 = getProduct1();

        when(productService.getProductById(anyLong())).thenReturn(productDTO1);

        mockMvc.perform(get(ProductController.BASE_URL +"/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME_1)));
    }

    @Test
    public void createNewProduct() throws Exception {
        ProductDTO product = new ProductDTO();
        product.setName("product 1");
        product.setPrice(1.23);

        ProductDTO returnDto = new ProductDTO();
        returnDto.setName(product.getName());
        returnDto.setPrice(product.getPrice());
        returnDto.setProductURL(ProductController.BASE_URL +"/1");

        when(productService.saveProductByDTO(anyLong(), any(ProductDTO.class))).thenReturn(returnDto);

        mockMvc.perform(post(ProductController.BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo("product 1")))
                .andExpect(jsonPath("$.price", equalTo(1.23)))
                .andExpect(jsonPath("$.product_url", equalTo(ProductController.BASE_URL + "/1")));
    }

    @Test
    public void updateProduct() throws Exception {
        ProductDTO product = new ProductDTO();
        product.setName("product 1");
        product.setPrice(1.23);

        ProductDTO returnDto = new ProductDTO();
        returnDto.setName(product.getName());
        returnDto.setPrice(product.getPrice());
        returnDto.setProductURL(ProductController.BASE_URL +"/1");

        when(productService.saveProductByDTO(anyLong(), any(ProductDTO.class))).thenReturn(returnDto);

        mockMvc.perform(put(ProductController.BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("product 1")))
                .andExpect(jsonPath("$.price", equalTo(1.23)))
                .andExpect(jsonPath("$.product_url", equalTo(ProductController.BASE_URL + "/1")));
    }

    @Test
    public void patchProduct() throws Exception {
        ProductDTO product = new ProductDTO();
        product.setName("product 1");

        ProductDTO returnDto = new ProductDTO();
        returnDto.setName(product.getName());
        returnDto.setPrice(1.23);
        returnDto.setProductURL(ProductController.BASE_URL +"/1");

        when(productService.patchProduct(anyLong(), any(ProductDTO.class))).thenReturn(returnDto);

        mockMvc.perform(patch(ProductController.BASE_URL+"/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("product 1")))
                .andExpect(jsonPath("$.price", equalTo(1.23)))
                .andExpect(jsonPath("$.product_url", equalTo(ProductController.BASE_URL+"/1")));
    }

    @Test
    public void deleteProduct() throws Exception {
        mockMvc.perform(delete(ProductController.BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(productService).deleteProductById(anyLong());
    }

    @Test
    public void testNotFoundException() throws Exception {

        when(productService.getProductById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(ProductController.BASE_URL + "/222")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}