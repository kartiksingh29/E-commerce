package com.personal.productservice.services;

import com.personal.productservice.dto.RequestDTO;
import com.personal.productservice.exceptions.ProductNotFoundException;
import com.personal.productservice.models.Category;
import com.personal.productservice.models.Product;
import com.personal.productservice.repositories.CategoryRepository;
import com.personal.productservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier("realProductService") // identifier for Dependency Injection in Controller
public class RealProductService implements IProductService {

    public ProductRepository productRepository;

    public CategoryRepository categoryRepository;

    @Autowired
    public RealProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
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
    public Product addProduct(Product product) {
        // you either get a new product with existing category
        // or, you get a new product with a new category
        Category savedCategory = getExistingOrNewCategory(product);
        product.setCategory(savedCategory);
        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }

    @Override
    public Product replaceProduct(Long id, RequestDTO requestDTO) {

        return null;
    }

    @Override
    public Product updateProduct(Long id, Product product) throws ProductNotFoundException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()){
            throw new ProductNotFoundException("Product with id =" + id +" does not exist " +
                    "and hence cannot be updated!");
        }
        Product existingProduct = optionalProduct.get();

        Category categoryToBeSaved ;

        if( product.getCategory().getName() != null ) {
            categoryToBeSaved = getExistingOrNewCategory(product);
        }
        else {
            categoryToBeSaved = existingProduct.getCategory();
        }
        // now we build a productToBeSaved Object
        Product productToBeSaved = new Product();
        productToBeSaved.setId(id); //setting the id

        productToBeSaved.setName(product.getName()!=null
                ? product.getName() : existingProduct.getName());

        productToBeSaved.setDescription(product.getDescription() != null
                ? product.getDescription() : existingProduct.getDescription());

        productToBeSaved.setPrice(product.getPrice() > 0
                ? product.getPrice(): existingProduct.getPrice());

        productToBeSaved.setImageURL(product.getName()!= null
                ? product.getImageURL() : existingProduct.getImageURL());

        productToBeSaved.setCategory(categoryToBeSaved);
        return productRepository.save(productToBeSaved);
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

    Category getExistingOrNewCategory(Product product) {
        Optional<Category> optionalCategory = categoryRepository.findByName(product.getCategory().getName());
        Category savedCategory ;
        if(optionalCategory.isEmpty()){
            // this means that the category does not exist so we will first create it.
            savedCategory = categoryRepository.save(product.getCategory());
        }
        else {
            savedCategory = optionalCategory.get();
        }

        return savedCategory;
    }
}
