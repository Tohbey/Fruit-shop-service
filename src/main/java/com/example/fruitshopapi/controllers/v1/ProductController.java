package com.example.fruitshopapi.controllers.v1;

import com.example.fruitshopapi.api.v1.model.ProductDTO;
import com.example.fruitshopapi.api.v1.model.ProductListDTO;
import com.example.fruitshopapi.domain.Product;
import com.example.fruitshopapi.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(ProductController.BASE_URL)
public class ProductController {
    public static final String BASE_URL = "/api/v1/products";

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ProductListDTO getListOfProduct(){
        return new ProductListDTO(productService.getAllProducts());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }


    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO createNewProduct(@RequestBody Product product){
        return productService.createNewProduct(product);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO){
        return productService.saveProductByDTO(id, productDTO);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product patchProduct(@PathVariable Long id, @RequestBody Product product){
        return productService.patchProduct(id, product);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable Long id){
        productService.deleteProductById(id);
    }
}
