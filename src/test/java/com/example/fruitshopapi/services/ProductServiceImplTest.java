package com.example.fruitshopapi.services;

import com.example.fruitshopapi.api.v1.mapper.ProductMapper;
import com.example.fruitshopapi.api.v1.model.ProductDTO;
import com.example.fruitshopapi.api.v1.model.ProductListDTO;
import com.example.fruitshopapi.domain.Product;
import com.example.fruitshopapi.repositories.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceImplTest {

    public static final long ID_1 = 1L;
    public static final String NAME_1 = "Product 1";
    public static final String VENDOR_1 = "vendor 1";
    public static final String CATEGORY_1 = "category 1";
    public static final Double PRICE_1 = 5.67;

    public static final long ID_2 = 2L;
    public static final String NAME_2 = "Product 2";
    public static final String VENDOR_2 = "vendor 2";
    public static final String CATEGORY_2 = "category 2";
    public static final Double PRICE_2 = 7.67;

    @Mock
    ProductRepository productRepository;

    ProductService productService;


    @Before
    public void setUp() throws  Exception{
        MockitoAnnotations.initMocks(this);

        productService = new ProductServiceImpl(ProductMapper.INSTANCE, productRepository);
    }

    private Product getProduct1(){
        Product product = new Product();
        product.setName(NAME_1);
        product.setCategory(CATEGORY_1);
        product.setId(ID_1);
        product.setVendor(VENDOR_1);
        product.setPrice(PRICE_1);
        return product;
    }

    private Product getProduct2(){
        Product product = new Product();
        product.setName(NAME_2);
        product.setCategory(CATEGORY_2);
        product.setId(ID_2);
        product.setVendor(VENDOR_2);
        product.setPrice(PRICE_2);
        return product;
    }

    @Test
    public void getAllProducts() {
        List<Product> productList = Arrays.asList(getProduct1(), getProduct2());
        given(productRepository.findAll()).willReturn(productList);

        List<ProductDTO> productListDTO = productService.getAllProducts();

        then(productRepository).should(times(1)).findAll();
        assertThat(productListDTO.size(), is(equalTo(2)));
    }

    @Test
    public void getProductById() {
        Product product = getProduct1();

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        ProductDTO productDTO = productService.getProductById(1L);
        assertEquals(NAME_1, productDTO.getName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getProductByIdNotFound() {
        given(productRepository.findById(anyLong())).willReturn(Optional.empty());

        ProductDTO productDTO = productService.getProductById(1L);

        then(productRepository).should(times(1)).findById(anyLong());
    }

    @Test
    public void createNewProduct() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(NAME_1);

        Product product = getProduct1();

        given(productRepository.save(any(Product.class))).willReturn(product);

        ProductDTO savedProductDTO = productService.createNewProduct(product);

        then(productRepository).should().save(any(Product.class));
        assertThat(savedProductDTO.getProductURL(), containsString("1"));
    }

    @Test
    public void saveProductByDTO() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(NAME_1);

        Product product = getProduct1();

        given(productRepository.save(any(Product.class))).willReturn(product);

        ProductDTO savedProductDTO = productService.saveProductByDTO(ID_1, productDTO);

        then(productRepository).should().save(any(Product.class));
        assertThat(savedProductDTO.getProductURL(), containsString("1"));
    }

    @Test
    public void deleteProductById() {
        productService.deleteProductById(1L);

        then(productRepository).should().deleteById(anyLong());
    }

    @Test
    public void patchProduct() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(NAME_1);

        Product product = getProduct1();

        given(productRepository.findById(anyLong())).willReturn(Optional.of(product));
        given(productRepository.save(any(Product.class))).willReturn(product);

        ProductDTO savedProductDTO = productService.patchProduct(ID_1, productDTO);

        then(productRepository).should().save(any(Product.class));
        then(productRepository).should(times(1)).findById(anyLong());
        assertThat(savedProductDTO.getProductURL(), containsString("1"));
    }
}