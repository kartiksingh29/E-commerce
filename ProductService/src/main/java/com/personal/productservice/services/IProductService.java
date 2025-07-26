package com.personal.productservice.services;

import com.personal.productservice.dto.RequestDTO;
import com.personal.productservice.models.Product;

import java.util.List;

public interface IProductService {
    public Product getProductById(Long id);
    public List<Product> getAllProducts();
    public Product replaceProduct(Long id, RequestDTO requestDTO);
}
