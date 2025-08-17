package com.personal.productservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@Entity
public class Category extends BaseModel implements Serializable {
    private String name ;
}


// Update : removed List<Product> field because it needs careful handling
//          and also because it was just for demo purpose/ understanding purpose.


// Here, we have List<Product> productList field in Category Class.
// This bi-direction association for Product-Category is legal in Java.
// But it needs to be handled carefully so that it doesn't go in infinite loop ( circular-dependency ).

// Example if we define custom constructor in both models, then Product object creation
// would require a Category object and similarly Category object creation would require
// a Product Object. Cyclic dependency.
