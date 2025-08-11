package com.personal.productservice.dto;

import com.personal.productservice.models.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ValidationResponseDTO {
    private Long userId;
    private String name;
    private String email ;
    private List<Role> roles ;
}
