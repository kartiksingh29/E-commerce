package com.personal.productservice.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private Long id ;
    private String name ;
    private String description ;
    private double price ;
    private String imageURL ;
    private Category category ;
}
