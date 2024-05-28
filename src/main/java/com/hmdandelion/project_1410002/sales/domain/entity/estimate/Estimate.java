package com.hmdandelion.project_1410002.sales.domain.entity.estimate;

import com.hmdandelion.project_1410002.sales.model.EstimateStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "tbl_estimate")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Estimate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long estimateCode;
    @CreatedDate
    public LocalDateTime createdAt;
    @LastModifiedDate
    public LocalDateTime updatedAt;
    public LocalDate deadline;
    @Enumerated(value = EnumType.STRING)
    public EstimateStatus status;
    public Boolean isOrdered;
    public Long clientCode;
    @OneToMany(mappedBy = "estimate")
    private List<EstimateProduct> estimateProducts = new ArrayList<>();
}
