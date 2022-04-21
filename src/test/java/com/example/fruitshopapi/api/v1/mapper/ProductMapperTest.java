package com.example.fruitshopapi.api.v1.mapper;

import com.example.fruitshopapi.api.v1.model.ProductDTO;
import com.example.fruitshopapi.domain.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    public static final String NAME = "product 1";
    public static final String CATEGORY = "fruit";
    public static final String VENDOR = "vendor 1";
    public static final Double PRICE = 4.56;
    public static final long ID = 1L;

    ProductMapper productMapper = ProductMapper.INSTANCE;

    @Test
    void productToProductDTO() {
        Product product = new Product();
        product.setId(ID);
        product.setVendor(VENDOR);
        product.setPrice(PRICE);
        product.setCategory(CATEGORY);
        product.setName(NAME);

        ProductDTO productDTO = productMapper.productToProductDTO(product);

        assertEquals(NAME, productDTO.getName());
        assertEquals(PRICE, productDTO.getPrice());
    }

    @Test
    void productDTOToProduct() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setPrice(PRICE);
        productDTO.setName(NAME);

        Product product = productMapper.productDTOToProduct(productDTO);

        assertEquals(NAME, product.getName());
        assertEquals(PRICE, product.getPrice());
    }
}