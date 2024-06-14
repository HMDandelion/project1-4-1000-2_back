package com.hmdandelion.project_1410002.production.dto.response.production;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmdandelion.project_1410002.production.domain.type.ProductionStatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ProductionReportResponse {

    private Long productionStatusCode;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completedAt;
    private String stylizationName; // 커스텀 이름 (마스크 외 3건)
    private Integer  totalOrderedQuantity; // 총 지시 수량
    private Integer totalProductionQuantity;
    private String productionFile;
    private ProductionStatusType productionStatus;

    public static ProductionReportResponse of(Long productionStatusCode, LocalDateTime startAt, LocalDateTime completedAt,
                                              String stylizationName,Integer  totalOrderedQuantity, Integer totalProductionQuantity, String productionFile,
                                              ProductionStatusType productionStatus) {
        return new ProductionReportResponse(
                productionStatusCode,
                startAt,
                completedAt,
                stylizationName,
                totalOrderedQuantity,
                totalProductionQuantity,
                productionFile,
                productionStatus
        );
    }
}
//    public static ProductionReportResponse from(final ProductionManagement productionManagement) {
//        return new ProductionReportResponse(
//                productionManagement.getProductionStatusCode(),
//                productionManagement.getStartAt(),
//                productionManagement.getCompletedAt(),
//                productionManagement.getTotalProductionQuantity(),
//                productionManagement.getProductionFile(),
//                productionManagement.getProductionStatus()
//        );
//    }

//}