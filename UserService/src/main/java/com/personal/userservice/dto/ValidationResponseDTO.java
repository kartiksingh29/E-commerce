package com.personal.userservice.dto;

import com.personal.userservice.models.Role;
import com.personal.userservice.models.User;
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

    public static ValidationResponseDTO from(User user){
        ValidationResponseDTO dto = new ValidationResponseDTO();
        dto.setUserId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRoles(user.getRoles());
        return dto;
    }
}
