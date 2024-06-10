package com.hmdandelion.project_1410002.auth.type;

import com.hmdandelion.project_1410002.employee.dto.EmployeeInfoDTO;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class CustomUser extends User {
    private Long employeeCode;
    private String employeeName;
    private String email;
    private String positionName;
    private String departmentName;

    public CustomUser(Long employeeCode, EmployeeInfoDTO employeeInfo, UserDetails userDetails) {
        super(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        this.employeeCode = employeeCode;
        this.employeeName = employeeInfo.getEmployeeName();
        this.email = employeeInfo.getEmail();
        this.positionName = employeeInfo.getPositionName();
        this.departmentName = employeeInfo.getDepartmentName();
    }
}
