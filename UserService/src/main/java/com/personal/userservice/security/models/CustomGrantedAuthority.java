package com.personal.userservice.security.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.personal.userservice.models.Role;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@JsonDeserialize
public class CustomGrantedAuthority implements GrantedAuthority {

    //private Role role ;
    private String authority;

    public CustomGrantedAuthority(){}

    public CustomGrantedAuthority(Role role) {
        this.authority = role.getName();
    }

    @Override
    public String getAuthority() {
        return authority;
    }

}
