package com.personal.productservice.repositories;

import com.personal.productservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product getProductByIdIs(Long id);
    Product getProductByName(String name);
}
