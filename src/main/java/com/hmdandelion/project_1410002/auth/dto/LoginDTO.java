package com.hmdandelion.project_1410002.auth.dto;

import com.hmdandelion.project_1410002.employee.domain.entity.Employee;
import com.hmdandelion.project_1410002.employee.domain.type.AuthorityType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginDTO {
    private final Long employeeCode;
    private final String employeeNo;
    private final String password;
    private final List<AuthorityType> authorities;

    public static LoginDTO from(Employee employee, List<AuthorityType> authorities) {
        return new LoginDTO(
                employee.getEmployeeCode(),
                employee.getEmployeeNo(),
                employee.getPassword(),
                authorities
        );
    }

    public List<GrantedAuthority> getGrantedAuthorities() {
        return this.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.name()))
                .collect(Collectors.toList());
    }
}
