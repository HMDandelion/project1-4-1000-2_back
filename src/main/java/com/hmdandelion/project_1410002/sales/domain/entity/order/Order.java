package com.hmdandelion.project_1410002.sales.domain.entity.order;

import com.hmdandelion.project_1410002.sales.domain.type.OrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "tbl_order")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderCode;
    @CreatedDate
    private LocalDateTime orderDatetime;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    private LocalDate deadline;
    @Enumerated(value = EnumType.STRING)
    private OrderStatus status = OrderStatus.ORDER_RECEIVED;
    private LocalDateTime completedAt;
    private Long clientCode;
    private Long estimateCode;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    private Order(LocalDate deadline, Long clientCode, Long estimateCode) {
        this.deadline = deadline;
        this.clientCode = clientCode;
        this.estimateCode = estimateCode;
    }

    public static Order of(LocalDate deadline, Long clientCode, Long estimateCode) {
        return new Order(
                deadline,
                clientCode,
                estimateCode
        );
    }

    public void modifyProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public void modifyStatus(OrderStatus orderStatus) {
        this.status = orderStatus;
    }
}
