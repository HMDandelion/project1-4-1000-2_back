package com.hmdandelion.project_1410002.production.domain.entity.material;

import com.hmdandelion.project_1410002.production.dto.material.request.StockUsageCreateRequest;
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
    private long usedQuantity;
    private Long stockCode;
    private Long usageCode;
    private boolean transmissionStatus;

    public StockUsage(long usedQuantity, Long stockCode, Long usageCode) {
        this.usedQuantity = usedQuantity;
        this.stockCode = stockCode;
        this.usageCode = usageCode;
        this.transmissionStatus = false;
    }

    public static StockUsage from(StockUsageCreateRequest request) {
        return new StockUsage(
                request.getUsedQuantity(),
                request.getStockCode(),
                request.getUsageCode()
        );
    }
}