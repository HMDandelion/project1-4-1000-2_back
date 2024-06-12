package com.hmdandelion.project_1410002.inventory.domian.entity.warehouse;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tbl_warehouse")
@Getter
@NoArgsConstructor
@ToString
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long warehouseCode;
    private String name;
    private String location;
    private Long volume;
    private Long employeeCode;

    public Warehouse(String name, String location, Long volume, Long employeeCode) {
        this.name = name;
        this.location = location;
        this.volume = volume;
        this.employeeCode = employeeCode;
    }

    public static Warehouse of(String name, String location, Long volume, Long employeeCode) {
        return new Warehouse(
                name,
                location,
                volume,
                employeeCode
        );
    }

    public void modify(String name, String location, Long volume, Long employeeCode) {
        this.name = name;
        this.location = location;
        this.volume = volume;
        this.employeeCode = employeeCode;
    }
}
