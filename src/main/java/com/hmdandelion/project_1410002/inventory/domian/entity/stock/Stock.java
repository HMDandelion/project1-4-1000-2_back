package com.hmdandelion.project_1410002.inventory.domian.entity.stock;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmdandelion.project_1410002.inventory.domian.entity.product.Product;
import com.hmdandelion.project_1410002.inventory.domian.type.AssignmentStatus;
import com.hmdandelion.project_1410002.inventory.domian.type.StockType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_stock")
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE tbl_stock SET is_delete = 1 WHERE stock_code = ?")
@ToString
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockCode;

    private Long quantity;

    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    private Boolean isDelete = false;

    @Enumerated(EnumType.STRING)
    private StockType type;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "product_code")
    private Product product;

    @Enumerated(EnumType.STRING)
    private AssignmentStatus assignmentStatus;

    public Stock(Long quantity, StockType type, Product product) {
        this.quantity = quantity;
        this.type = type;
        this.product = product;
    }

    public static Stock of(Long quantity, StockType type, Product product) {
        return new Stock(
                quantity,
                type,
                product
        );
    }

    private void modify(Product product, StockType type) {
        this.product = product;
        this.type = type;
    }


    public void modifyStatus(AssignmentStatus change) {
        this.assignmentStatus = change;
    }
}
