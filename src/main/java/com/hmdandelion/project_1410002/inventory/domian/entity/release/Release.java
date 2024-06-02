package com.hmdandelion.project_1410002.inventory.domian.entity.release;

import com.hmdandelion.project_1410002.inventory.domian.type.ReleaseStatus;
import com.hmdandelion.project_1410002.sales.domain.entity.order.Order;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_release")
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Release {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long releaseCode;

    private ReleaseStatus status;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "order_code")
    private Order order;
    @CreatedDate
    private LocalDateTime createdAt;

}
