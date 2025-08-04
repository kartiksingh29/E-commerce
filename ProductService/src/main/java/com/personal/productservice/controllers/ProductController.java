package com.personal.productservice.controllers;

import com.personal.productservice.dto.ErrorResponseDTO;
import com.personal.productservice.dto.RequestDTO;
import com.personal.productservice.exceptions.ProductNotFoundException;
import com.personal.productservice.models.Category;
import com.personal.productservice.models.Product;
import com.personal.productservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    IProductService productService ;

    @Autowired
    public ProductController(@Qualifier("realProductService") IProductService productService){
        this.productService = productService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(products) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) throws ProductNotFoundException {
            Product product = productService.getProductById(id);
            return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @GetMapping("/categories")
    public List<Category> getAllCategories(){
        return new ArrayList<>() ;
    }

    @GetMapping("/categories/{id}")
    public List<Product> getAllProductsByCategory(@PathVariable("id") String categoryName){
        return new ArrayList<>();
    }

    @PostMapping("/")
    public ResponseEntity<Product> addProduct(@RequestBody RequestDTO addProductDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new Product());
    }

    @PatchMapping("/{id}")
    public Product updateProduct(@PathVariable("id") Long productId, @RequestBody RequestDTO requestDTO){
        return new Product();
    }

    @PutMapping("/{id}")
    public Product replaceProduct(@PathVariable("id") Long id, @RequestBody RequestDTO requestDTO){
        return productService.replaceProduct(id,requestDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable("id") Long id) throws ProductNotFoundException {
        Boolean deleted = productService.deleteProductById(id);
        return ResponseEntity.ok().body(deleted);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleProductNotFoundException(ProductNotFoundException exception) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(exception.getMessage()+ " at the controller") ;
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDTO) ;
    }
}
