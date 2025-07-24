package com.personal.productservice.services;

import com.personal.productservice.dto.ResponseDTO;
import com.personal.productservice.models.Category;
import com.personal.productservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FakeStoreProductService implements IProductService {

    RestTemplate restTemplate ;

    @Autowired
    public FakeStoreProductService(RestTemplate restTemplate){
        this.restTemplate = restTemplate ;
    }

    public Product getProductFromResponseDTO(ResponseDTO responseDTO) {
        Product product = new Product();
        product.setId(responseDTO.getId());
        product.setName(responseDTO.getTitle());
        product.setPrice(responseDTO.getPrice());
        product.setCategory(new Category());
        product.getCategory().setName(responseDTO.getCategory());
        product.setDescription(responseDTO.getDescription());
        product.setImageURL(responseDTO.getImage());

        return product ;
    }

    @Override
    public Product getProductById(Long id) {
        ResponseDTO responseDTO = restTemplate.getForObject("https://fakestoreapi.com/products/"+id,
                                   ResponseDTO.class);

        Product product = getProductFromResponseDTO(responseDTO);
        return product ;
    }

}
