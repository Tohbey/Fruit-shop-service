package com.example.fruitshopapi.services;

import com.example.fruitshopapi.api.v1.model.ProductDTO;
import com.example.fruitshopapi.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductDTO> getAllProducts();

    ProductDTO getProductById(Long id);

    ProductDTO createNewProduct(Product product);

    ProductDTO saveProductByDTO(Long id, ProductDTO productDTO);

    void deleteProductById(Long id);

    Product patchProduct(Long id, Product product);
}
