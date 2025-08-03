package com.personal.productservice.orminheritancedemo.mappedsuperclass;

import jakarta.persistence.Entity;

@Entity(name = "ms_mentor")
public class Mentor extends User {
    private double averageRating;
}
