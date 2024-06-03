package com.hmdandelion.project_1410002.sales.domain.entity.returns;

import com.hmdandelion.project_1410002.sales.domain.type.ManageStatus;
import com.hmdandelion.project_1410002.sales.domain.type.ManageType;
import com.hmdandelion.project_1410002.sales.domain.type.ReturnStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_return")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Return {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long returnCode;
    @CreatedDate
    private LocalDateTime returnDatetime;
    private Long clientCode;
    private Long orderCode;
    @Enumerated(value = EnumType.STRING)
    private ManageType manageType;
    @Enumerated(value = EnumType.STRING)
    private ManageStatus manageStatus = ManageStatus.RETURN_RECEIVED;
    @Enumerated(value = EnumType.STRING)
    private ReturnStatus returnStatus = ReturnStatus.AWAITING_INSPECTION;
    private Long exchangeOrder;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "returnEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReturnProduct> returnProducts = new ArrayList<>();

    private Return(Long clientCode, Long orderCode, ManageType manageType) {
        this.clientCode = clientCode;
        this.orderCode = orderCode;
        this.manageType = manageType;
    }

    public static Return of(Long clientCode, Long orderCode, ManageType manageType) {
        return new Return(
                clientCode,
                orderCode,
                manageType
        );
    }

    public void modifyExchangeCode(Long exchangeOrderCode) {
        this.exchangeOrder = exchangeOrderCode;
    }

    public void modifyProducts(List<ReturnProduct> returnProducts) {
        this.returnProducts = returnProducts;
    }
}
