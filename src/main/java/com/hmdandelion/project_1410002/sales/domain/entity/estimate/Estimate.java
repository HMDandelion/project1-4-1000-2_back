package com.hmdandelion.project_1410002.sales.domain.entity.estimate;

import com.hmdandelion.project_1410002.sales.domain.type.EstimateStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "tbl_estimate")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE tbl_estimate SET status = 'DELETED' WHERE estimate_code = ?")
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
    public EstimateStatus status = EstimateStatus.ACTIVE;
    public Boolean isOrdered = false;
    public Long clientCode;
    @OneToMany(mappedBy = "estimate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EstimateProduct> estimateProducts = new ArrayList<>();

    public Estimate(LocalDate deadline, Long clientCode) {
        this.deadline = deadline;
        this.clientCode = clientCode;
    }

    public static Estimate of(LocalDate deadline, Long clientCode) {
        return new Estimate(
                deadline,
                clientCode
        );
    }

    public void modifyProducts(List<EstimateProduct> products) {
        this.estimateProducts = products;
    }
}
