package com.hmdandelion.project_1410002.production.domain.entity.material;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "tbl_stock_usage")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class StockUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockUsageCode;
    private int usedQuantity;
    private Long stockCode;
    private Long usageCode;
    private boolean transmissionStatus;
}