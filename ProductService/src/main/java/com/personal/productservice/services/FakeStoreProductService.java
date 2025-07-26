package com.personal.productservice.services;

import com.personal.productservice.dto.RequestDTO;
import com.personal.productservice.dto.ResponseDTO;
import com.personal.productservice.models.Category;
import com.personal.productservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

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

        ResponseDTO responseDTO = restTemplate.getForObject(
                "https://fakestoreapi.com/products/"+id,
                                   ResponseDTO.class);

        Product product = getProductFromResponseDTO(responseDTO);
        return product ;
    }

    @Override
    public List<Product> getAllProducts() {
        ResponseDTO[] responseList = restTemplate.getForObject(
                "https://fakestoreapi.com/products",
                ResponseDTO[].class);

        List<Product> products = new ArrayList<>();
        for(ResponseDTO responseDTO: responseList) {
            Product product = getProductFromResponseDTO(responseDTO);
            products.add(product);
        }
        return products ;
    }

    @Override
    public Product replaceProduct(Long id, RequestDTO requestDTO){

        //restTemplate.put("https://fakestoreapi.com/products/"+id,requestDTO);

        //In one API we will PUT and also get back the response
        RequestCallback requestCallback = restTemplate.httpEntityCallback(
                requestDTO, ResponseDTO.class);
        HttpMessageConverterExtractor<ResponseDTO> responseExtractor =
                new HttpMessageConverterExtractor(ResponseDTO.class,
                        restTemplate.getMessageConverters());
        ResponseDTO responseDTO = restTemplate.execute(
                "https://fakestoreapi.com/products/"+id,
                HttpMethod.PUT, requestCallback, responseExtractor);

        return getProductFromResponseDTO(responseDTO);
    }

}
