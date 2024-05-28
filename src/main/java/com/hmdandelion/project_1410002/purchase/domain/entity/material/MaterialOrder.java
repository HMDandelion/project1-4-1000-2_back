package com.hmdandelion.project_1410002.purchase.domain.entity.material;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmdandelion.project_1410002.purchase.model.MOrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Table(name = "tbl_material_order")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class MaterialOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderCode;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate orderDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate deliveryDueDate;
    private Long clientCode;    //TODO 수정필
    @Enumerated(value = EnumType.STRING)
    private MOrderStatus status;
    private boolean isRegularContract;
    private Long employeeCode;//TODO 수정필
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime arrivalDatetime;
    private Long planCode;//TODO 수정필
    private boolean isDeleted;
    private String deletionReason;
}
