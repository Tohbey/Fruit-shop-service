package com.example.fruitshopapi.bootstrap;

import com.example.fruitshopapi.domain.Category;
import com.example.fruitshopapi.domain.Customer;
import com.example.fruitshopapi.domain.Product;
import com.example.fruitshopapi.domain.Vendor;
import com.example.fruitshopapi.repositories.CategoryRepository;
import com.example.fruitshopapi.repositories.CustomerRepository;
import com.example.fruitshopapi.repositories.ProductRepository;
import com.example.fruitshopapi.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;
    private final ProductRepository productRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        loadCategories();
        loadCustomers();
        loadVendors();
        loadProduct();
    }

    private void loadProduct(){
        Product product1 = new Product();
        product1.setName("Product 1");
        product1.setCategory("Fruits");
        product1.setPrice(4.56);
        product1.setVendor("Vendor 1");

        Product product2 = new Product();
        product2.setName("Product 2");
        product2.setCategory("Dried");
        product2.setPrice(4.00);
        product2.setVendor("Vendor 2");

        Product product3 = new Product();
        product3.setName("Product 3");
        product3.setCategory("Fresh");
        product3.setPrice(5.00);
        product3.setVendor("Vendor 1");

        Product product4 = new Product();
        product4.setName("Product 4");
        product4.setCategory("Fresh");
        product4.setPrice(5.60);
        product4.setVendor("Vendor 2");

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        productRepository.save(product4);

        System.out.println("Products Loaded: " + productRepository.count());
    }

    private void loadVendors() {
        Vendor vendor1 = new Vendor();
        vendor1.setName("Vendor 1");
        vendorRepository.save(vendor1);

        Vendor vendor2 = new Vendor();
        vendor2.setName("Vendor 2");
        vendorRepository.save(vendor2);

        System.out.println("Vendors Loaded: " + vendorRepository.count());
    }

    private void loadCategories() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        System.out.println("Categories Loaded: " + categoryRepository.count());
    }

    private void loadCustomers() {
        //given
        Customer customer1 = new Customer();
        customer1.setId(1l);
        customer1.setFirstname("Michale");
        customer1.setLastname("Weston");
        customerRepository.save(customer1);

        Customer customer2 = new Customer();
        customer2.setId(2l);
        customer2.setFirstname("Sam");
        customer2.setLastname("Axe");

        customerRepository.save(customer2);

        System.out.println("Customers Loaded: " + customerRepository.count());
    }
}
