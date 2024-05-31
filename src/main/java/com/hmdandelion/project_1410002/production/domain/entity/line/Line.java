package com.hmdandelion.project_1410002.production.domain.entity.line;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmdandelion.project_1410002.sales.domain.entity.employee.Employee;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "tbl_line")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Line {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lineCode;

    @Column(nullable = false)
    private String lineName;

    private Integer lineProduction;

    @Column(length = 20)
    private String lineStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_code")
    private Employee employeeCode;


}

