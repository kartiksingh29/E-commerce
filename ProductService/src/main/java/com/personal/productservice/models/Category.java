package com.personal.productservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Category extends BaseModel {
    // this mappedBy tells spring to not create any new mapping for category-product relation.

    // cascade-remove means if category removed, then remove all corresponding products also.

    // fetch type as Lazy ensures this field is not fetched right away when fetching Category
    // from database to improve query performance.
    @OneToMany(mappedBy="category", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    private List<Product> productList;

    private String name ;
}



// Here, we have List<Product> productList field in Category Class.
// This bi-direction association for Product-Category is legal in Java.
// But it needs to be handled carefully so that it doesn't go in infinite loop.

// Example if we define custom constructor in both models, then Product object creation
// would require a Category object and similarly Category object creation would require
// a Product Object. Cyclic dependency.
