package com.hmdandelion.project_1410002.production.domain.entity.line;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmdandelion.project_1410002.production.domain.type.LineStatusType;
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


    private Line(String lineName, Integer lineProduction, LineStatusType lineStatusType
    ) {
        this.lineName = lineName;
        this.lineProduction = lineProduction;
        this.lineStatus = lineStatusType;
    }
    public static Line of(String lineName, Integer lineProduction, LineStatusType lineStatusType) {
        return new Line(
                lineName,
                lineProduction,
                lineStatusType
        );
    }

    public void modify(String lineName, Integer lineProduction, LineStatusType lineStatusType) {

        this.lineName = lineName;
        this.lineProduction = lineProduction;
        this.lineStatus = lineStatusType;
    }
}

