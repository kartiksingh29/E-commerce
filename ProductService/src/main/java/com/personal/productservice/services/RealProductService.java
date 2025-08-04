package com.personal.productservice.services;

import com.personal.productservice.dto.RequestDTO;
import com.personal.productservice.exceptions.ProductNotFoundException;
import com.personal.productservice.models.Product;
import com.personal.productservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier("realProductService") // identifier for Dependency Injection
public class RealProductService implements IProductService {

    public ProductRepository productRepository;

    @Autowired
    public RealProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getProductById(Long id) throws ProductNotFoundException {
        // Optional<Product> signifies that you may or may not find a product
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            throw new ProductNotFoundException("Product with id =" + id +" does not exist!");
        }
        return optionalProduct.get();
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products;
    }

    @Override
    public Product replaceProduct(Long id, RequestDTO requestDTO) {
        return null;
    }

    @Override
    public Boolean deleteProductById(Long id) throws ProductNotFoundException {
        try{
            productRepository.deleteById(id);  // this method never throws exception by design
        }
        catch(Exception e){
            throw new ProductNotFoundException("Product with id =" + id +" does not exist!");
        }
        return Boolean.TRUE ;
    }
}
