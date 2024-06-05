package com.hmdandelion.project_1410002.auth.type;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class CustomUser extends User {
    private Long employeeCode;

    public CustomUser(Long employeeCode, UserDetails userDetails) {
        super(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        this.employeeCode = employeeCode;
    }
}
