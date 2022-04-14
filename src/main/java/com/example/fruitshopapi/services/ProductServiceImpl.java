package com.example.fruitshopapi.services;


import com.example.fruitshopapi.api.v1.mapper.ProductMapper;
import com.example.fruitshopapi.api.v1.model.ProductDTO;
import com.example.fruitshopapi.controllers.v1.CategoryController;
import com.example.fruitshopapi.controllers.v1.ProductController;
import com.example.fruitshopapi.controllers.v1.VendorController;
import com.example.fruitshopapi.domain.Product;
import com.example.fruitshopapi.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductMapper productMapper;
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository
                .findAll()
                .stream()
                .map(product -> {

                    ProductDTO productDTO = productMapper.productToProductDTO(product);
                    productDTO.setProductURL(getProductUrl(product.getId()));
                    productDTO.setCategoryURL(getCategoryUrl(product.getCategory()));
                    productDTO.setVendorURL(getVendorUrl(product.getVendor()));

                    return productDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::productToProductDTO)
                .map(productDTO -> {
                    productDTO.setProductURL(getProductUrl(id));

                    return productDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public ProductDTO createNewProduct(Product product) {
        return saveAndReturnDTO(product);
    }

    public ProductDTO saveAndReturnDTO(Product product){
        Product savedProduct = productRepository.save(product);

        ProductDTO returnDTO = productMapper.productToProductDTO(savedProduct);

        returnDTO.setProductURL(getProductUrl(savedProduct.getId()));

        return returnDTO;
    }

    @Override
    public ProductDTO saveProductByDTO(Long id, ProductDTO productDTO) {
        Product product = productMapper.productDTOToProduct(productDTO);
        product.setId(id);

        return saveAndReturnDTO(product);
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    private String getProductUrl(Long id) {
        return ProductController.BASE_URL + "/" + id;
    }

    private String getCategoryUrl(String category){
        return CategoryController.BASE_URL +"/"+category;
    }

    private String getVendorUrl(String vendor){
        return VendorController.BASE_URL +"/"+vendor;
    }

    @Override
    public Product patchProduct(Long id, Product product) {
        return productRepository.findById(id).map(product1 -> {

            if(product.getName() != null) {
                product1.setName(product.getName());
            }
            if(product.getPrice() != null) {
                product1.setPrice(product.getPrice());
            }
            if(product.getCategory() != null) {
                product1.setCategory(product.getCategory());
            }
            if(product.getVendor() != null) {
                product1.setVendor(product.getVendor());
            }

            return product;
        }).orElseThrow(ResourceNotFoundException::new);
    }
}
