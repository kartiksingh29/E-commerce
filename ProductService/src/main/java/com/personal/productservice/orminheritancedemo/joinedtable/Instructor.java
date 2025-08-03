package com.personal.productservice.orminheritancedemo.joinedtable;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="jt_instructor")
@PrimaryKeyJoinColumn(name="user_id")
// It means that primary key of this table ( instructor), joins on the PK
// of the parent table (user). This join is on the user_id column on this (mentor) table.
public class Instructor extends User {
    private String company;
}
