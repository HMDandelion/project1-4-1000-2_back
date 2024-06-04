package com.hmdandelion.project_1410002.sales.domain.entity.employee;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="tbl_employee")
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeCode;

    @Column(nullable = false)
    private String employeeNo;
    @Column(nullable = false)
    private String employeeName;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String ssn;
    @Column(nullable = false)
    private Long positionCode;
    private Long departmentCode; //integer로 되어있어서 수정함
    private String profileImage;
    private LocalDate hireDate;
    private LocalDate resignDate;
    @Column(nullable = false, length = 20, columnDefinition = "VARCHAR(20) DEFAULT 'ACTIVE'")
    private String status;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @Column(length = 300)
    private String refreshToken;
}
