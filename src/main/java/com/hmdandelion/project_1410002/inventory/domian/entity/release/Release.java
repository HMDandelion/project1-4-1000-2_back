package com.hmdandelion.project_1410002.inventory.domian.entity.release;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmdandelion.project_1410002.inventory.domian.type.ReleaseStatus;
import com.hmdandelion.project_1410002.sales.domain.entity.order.Order;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static com.hmdandelion.project_1410002.inventory.domian.type.ReleaseStatus.*;

@Entity
@Table(name = "tbl_release")
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Release {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long releaseCode;
    @Enumerated(EnumType.STRING)
    private ReleaseStatus status=WAIT;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "order_code")
    private Order order;
    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    public Release(Order order) {
        this.order = order;
    }

    public static Release of(Order order) {
        return new Release(order);
    }

    public void shipping() {
        this.status = SHIPPING;
    }

    public void complete() {
        this.status = DELIVERY_COMPLETED;
    }
}
