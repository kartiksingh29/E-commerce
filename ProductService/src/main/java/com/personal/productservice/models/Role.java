package com.personal.productservice.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Role extends BaseModel {
    String name;
}

// this class is copied in product service just for the ValidationResponseDTO received
// from user-service for token validation.
