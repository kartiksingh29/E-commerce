package com.personal.productservice.services;

import com.personal.productservice.models.Product;

public interface IProductService {
    public Product getProductById(Long id);
}
