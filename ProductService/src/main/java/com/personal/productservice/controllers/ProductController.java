package com.personal.productservice.controllers;

import com.personal.productservice.models.RequestDTO;
import com.personal.productservice.models.Category;
import com.personal.productservice.models.Product;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @GetMapping("/")
    public List<Product> getAllProducts() {
        return new ArrayList<>();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable("id") Long id){
        return new Product() ;
    }

    @GetMapping("/categories")
    public List<Category> getAllCategories(){
        return new ArrayList<>() ;
    }

    @GetMapping("/categories/{id}")
    public List<Product> getAllProductsByCategory(@PathVariable("id") String categoryName) {
        return new ArrayList<>();
    }

    @PostMapping("/")
    public Product addProduct(@RequestBody RequestDTO addProductDTO) {
        return new Product() ;
    }

    @PatchMapping("/{id}")
    public Product updateProduct(@PathVariable("id") Long productId, @RequestBody RequestDTO requestDTO){
        return new Product();
    }

    @PutMapping("/{id}")
    public Product replaceProduct(@PathVariable("id") Long productId, @RequestBody RequestDTO requestDTO) {
        return new Product();
    }



}
