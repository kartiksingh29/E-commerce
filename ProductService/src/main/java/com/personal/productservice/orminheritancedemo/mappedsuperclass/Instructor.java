package com.personal.productservice.orminheritancedemo.mappedsuperclass;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="ms_instructor") // this annotation is to ask to create a table and give table name
public class Instructor extends User {
    private String company;
}
