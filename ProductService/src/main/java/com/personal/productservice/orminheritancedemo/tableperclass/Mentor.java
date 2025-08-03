package com.personal.productservice.orminheritancedemo.tableperclass;

import jakarta.persistence.Entity;

@Entity(name = "tpc_mentor")
public class Mentor extends User {
    private double averageRating;
}
