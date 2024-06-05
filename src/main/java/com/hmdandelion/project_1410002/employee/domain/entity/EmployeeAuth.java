package com.hmdandelion.project_1410002.employee.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tbl_emp_auth")
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class EmployeeAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long empAuthCode;
    private Long authorityCode;
    private Long employeeCode;
}
