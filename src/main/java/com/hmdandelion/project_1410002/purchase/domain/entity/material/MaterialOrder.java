package com.hmdandelion.project_1410002.purchase.domain.entity.material;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmdandelion.project_1410002.purchase.dto.material.request.MaterialOrderCreateRequest;
import com.hmdandelion.project_1410002.purchase.dto.material.request.MaterialOrderModifyRequest;
import com.hmdandelion.project_1410002.purchase.model.MaterialOrderStatus;
import com.hmdandelion.project_1410002.purchase.service.MaterialOrderService;
import com.hmdandelion.project_1410002.sales.domain.entity.client.Client;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    //    private Long clientCode;    //TODO 수정필
    @ManyToOne
    @JoinColumn(name = "client_code")
    private Client client;
    @Enumerated(value = EnumType.STRING)
    private MaterialOrderStatus status;
    private boolean isRegularContract;
    private Long employeeCode;//TODO 수정필
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime arrivalDatetime;
    private Long planCode;//TODO 수정필
    private boolean isDeleted;
    private String deletionReason;

    public MaterialOrder(LocalDate orderDate, LocalDate deliveryDueDate, Client client, Long employeeCode, Long planCode) {
        this.orderDate = orderDate;
        this.deliveryDueDate = deliveryDueDate;
        this.client = client;
        this.employeeCode =employeeCode;
        this.planCode = planCode;

        this.status = MaterialOrderStatus.ORDER_COMPLETED;
        this.isDeleted = false;
    }

    public static MaterialOrder from(MaterialOrderCreateRequest request, Client client) {
        return new MaterialOrder(
                request.getOrderDate(),
                request.getDeliveryDueDate(),
                client,
                request.getEmployeeCode(),
                request.getPlanCode()
        );
    }

    public void delete(String deletionReason) {
        this.isDeleted = true;
        this.deletionReason = deletionReason;
    }

    public void modify(MaterialOrderModifyRequest request, Client client) {
        if (request.getOrderDate() != null) {
            this.orderDate = request.getOrderDate();
        }
        if (request.getDeliveryDueDate() != null) {
            this.deliveryDueDate = request.getDeliveryDueDate();
        }
        if (client != null) {
            this.client = client;
        }
        if (request.getEmployeeCode() != null) {
            this.employeeCode = request.getEmployeeCode();
        }
        if (request.getPlanCode() != null) {
            this.planCode = request.getPlanCode();
        }
    }

    public void setArrival(LocalDateTime now) {
        this.arrivalDatetime = now;
        this.status = MaterialOrderStatus.DELIVERY_COMPLETED;
    }
}
