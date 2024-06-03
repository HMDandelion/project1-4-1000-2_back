package com.hmdandelion.project_1410002.inventory.domian.entity.material;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmdandelion.project_1410002.inventory.domian.entity.warehouse.Warehouse;
import com.hmdandelion.project_1410002.inventory.domian.type.StockDivision;
import com.hmdandelion.project_1410002.inventory.dto.material.request.MaterialStockCreateRequest;
import com.hmdandelion.project_1410002.inventory.dto.material.request.MaterialStockModifyRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Table(name = "tbl_material_stock")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class MaterialStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockCode;
    @Enumerated(value = EnumType.STRING)
    private StockDivision division;
    @ManyToOne
    @JoinColumn(name = "spec_code")
    private MaterialSpec materialSpec;
    @ManyToOne
    @JoinColumn(name = "warehouse_code")
    private Warehouse warehouse;
    private int incomingQuantity;
    private int actualQuantity;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime storageDatetime;
    private String remarks;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime inspectionDatetime;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @LastModifiedDate
    private LocalDateTime modificationDatetime;
    private String modificationReason;
    private Long orderCode; //TODO 수정필

    public static MaterialStock from(MaterialStockCreateRequest request, Warehouse warehouse, MaterialSpec spec) {
        return new MaterialStock(
                null,
                StockDivision.valueOf(request.getDivision()),
                spec,
                warehouse,
                request.getIncomingQuantity(),
                request.getActualQuantity(),
                request.getStorageDatetime(),
                request.getRemarks(),
                request.getInspectionDatetime(),
                request.getModificationDatetime(),
                request.getModificationReason(),
                request.getOrderCode()
        );
    }

    public void modifyFrom(MaterialStockModifyRequest request, Warehouse warehouse) {
        this.actualQuantity = request.getActualQuantity();
        this.modificationReason = request.getModificationReason();
        this.warehouse = warehouse;
    }
}

