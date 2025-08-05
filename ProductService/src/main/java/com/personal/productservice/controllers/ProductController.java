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
    public ProductController(@Qualifier("realProductService") IProductService productService) {
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

    @PostMapping("/")
    public ResponseEntity<Product> addProduct(@RequestBody RequestDTO addProductDTO) {
        Product product = getProductFromRequestDTO(addProductDTO);
        Product addedProduct =productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedProduct);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody RequestDTO requestDTO) throws ProductNotFoundException {
        Product product = getProductFromRequestDTO(requestDTO);
        Product updatedProduct = productService.updateProduct(id,product);

        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> replaceProduct(@PathVariable("id") Long id, @RequestBody RequestDTO requestDTO){
        Product product = productService.replaceProduct(id,requestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable("id") Long id) throws ProductNotFoundException {
        Boolean deleted = productService.deleteProductById(id);
        return ResponseEntity.ok().body(deleted);
    }

    @GetMapping("/categories/{id}")
    public List<Product> getAllProductsByCategory(@PathVariable("id") String categoryName){
        return new ArrayList<>();
    }

    //controller level Exception Handler
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleProductNotFoundException(ProductNotFoundException exception) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(exception.getMessage()+ " at the controller") ;
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDTO) ;
    }

    Product getProductFromRequestDTO(RequestDTO requestDTO){
        Product product = new Product();
        product.setName(requestDTO.getTitle());
        product.setPrice(requestDTO.getPrice());
        product.setImageURL(requestDTO.getImage());
        product.setDescription(requestDTO.getDescription());
        product.setCategory(new Category());
        product.getCategory().setName(requestDTO.getCategory());
        return product;
    }
}
