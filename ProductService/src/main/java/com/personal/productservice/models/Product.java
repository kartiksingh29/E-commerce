package com.personal.productservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product extends BaseModel {

    private String name ;
    private String description ;
    private double price ;
    private String imageURL ;

    @ManyToOne(cascade ={CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REMOVE})
    private Category category ;
    // cascade persist means if product is added, check for category as well
    // cascade merge means if product is updated, check for category as well
    // cascade remove means if product is removed, check for category as well
    // check for category means if category doesn't already exist then add it
}
