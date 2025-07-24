package com.personal.productservice.dto;

import com.personal.productservice.models.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDTO {
    private Long id ;
    private String title ;
    private Double price ;
    private String description;
    private String category ;
    private String image ;
}
