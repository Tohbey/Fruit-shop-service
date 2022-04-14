package com.example.fruitshopapi.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private String name;
    private Double price;

    @JsonProperty("product_url")
    private String productURL;

    @JsonProperty("category_url")
    private String categoryURL;

    @JsonProperty("vendor_url")
    private String vendorURL;
}
