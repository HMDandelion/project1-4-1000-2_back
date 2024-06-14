package com.hmdandelion.project_1410002.sales.domain.repository.order;

import com.hmdandelion.project_1410002.sales.dto.response.OrderResponse;
import com.hmdandelion.project_1410002.sales.dto.response.OrdersResponse;
import com.hmdandelion.project_1410002.sales.dto.response.PlanningOrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface OrderRepoCustom {
    Page<OrdersResponse> search(Pageable pageable, LocalDate startDate, LocalDate endDate, String clientName, String status, String productName, String sort);

    Optional<OrderResponse> getOrder(Long orderCode);

    Page<PlanningOrderResponse> getPlanningOrders(Pageable pageable, LocalDate startDate, LocalDate endDate, String clientName, String status, String productName, String sort);
}
