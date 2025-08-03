package com.personal.productservice.orminheritancedemo.singletable;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity // no table here for mentor class since single table
@DiscriminatorValue("1")
public class Mentor extends User {
    private double averageRating;
}
