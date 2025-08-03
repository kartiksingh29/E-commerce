package com.personal.productservice.orminheritancedemo.mappedsuperclass;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass // this is the inheritance type and means no database table for User superclass
public class User {
    @Id
    private Long id ;
    private String name;
    private String email;
}
