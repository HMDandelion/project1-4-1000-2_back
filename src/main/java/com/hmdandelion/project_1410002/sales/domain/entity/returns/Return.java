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
    private ManageType manageType;
    private ManageStatus manageStatus = ManageStatus.RETURN_RECEIVED;
    private ReturnStatus returnStatus = ReturnStatus.AWAITING_INSPECTION;
    private Long exchangeOrder;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "return", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReturnProduct> returnProducts = new ArrayList<>();
}
