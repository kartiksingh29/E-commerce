package com.personal.productservice;

import com.personal.productservice.models.Product;
import com.personal.productservice.projections.ProductWithIdNamePrice;
import com.personal.productservice.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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
	void testGetAllProducts() {
		List<Product> productList = productRepository.findAll();
		for (Product product : productList) {
			System.out.println(product.getName());
		}
	}

	@Test
	void testGetProductById() {
		Product product = productRepository.getProductByIdIs(152L);
		System.out.println(product.getName());
	}

	@Test
	void testGetProductByName() {
		Product product = productRepository.getProductByName("Dell laptop 15");
		System.out.println(product.getName());
	}

	@Test
	@Transactional // rolls back the transaction on completion
	void testDeleteProductById() {
		productRepository.deleteById(100L);
	}

	@Test
	void testHQLQuery1() {
		Product product = productRepository.hqlGetProductById(152L);
		System.out.println(product.getId());
		System.out.println(product.getName());
	}
	@Test
	void testHQLQuery2() {
		List<ProductWithIdNamePrice> productWithIdNamePrices =
				productRepository.hqlGetProductProjectionsWithId(List.of(152L,153L,154L));
		for(ProductWithIdNamePrice productWithIdNamePrice : productWithIdNamePrices) {
			System.out.println(productWithIdNamePrice.getId());
			System.out.println(productWithIdNamePrice.getName());
		}
	}

}
