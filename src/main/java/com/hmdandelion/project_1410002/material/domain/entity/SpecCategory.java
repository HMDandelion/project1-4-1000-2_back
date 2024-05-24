package com.hmdandelion.project_1410002.material.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "tbl_spec_category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class SpecCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long specCategoryCode;
    private String specCategoryName;
}
