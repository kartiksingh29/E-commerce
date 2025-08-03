package com.personal.productservice.orminheritancedemo.joinedtable;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity(name = "jt_mentor")
@PrimaryKeyJoinColumn(name="user_id")
public class Mentor extends User {
    private double averageRating;
}
