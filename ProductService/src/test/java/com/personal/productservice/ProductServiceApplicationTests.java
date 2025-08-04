package com.personal.productservice;

import com.personal.productservice.models.Product;
import com.personal.productservice.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class ProductServiceApplicationTests {

	ProductRepository productRepository;

	@Autowired
	public ProductServiceApplicationTests(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Test
	void contextLoads() {
	}

	@Test
	void getAllProducts() {
		List<Product> productList = productRepository.findAll();
		for (Product product : productList) {
			System.out.println(product);
		}
	}

	@Test
	void getProductById() {
		Product product = productRepository.getProductByIdIs(100L);
		System.out.println(product.getName());
	}

	@Test
	@Transactional // rolls back the transaction on completion
	void deleteProductById() {
		productRepository.deleteById(100L);
	}

}
