package com.hmdandelion.project_1410002.production.domain.entity.line;

import com.hmdandelion.project_1410002.production.domain.type.LineStatusType;
import com.hmdandelion.project_1410002.employee.domain.entity.Employee;
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

    private Integer lineProduction; //  라인별 생산량

    @Column(length = 20)
    @Enumerated(value = EnumType.STRING)
    private LineStatusType lineStatus;

}

