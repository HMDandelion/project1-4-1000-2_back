package com.hmdandelion.project_1410002.sales.service;

import com.hmdandelion.project_1410002.common.exception.BadRequestException;
import com.hmdandelion.project_1410002.common.exception.NotFoundException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.sales.domain.entity.estimate.Estimate;
import com.hmdandelion.project_1410002.sales.domain.entity.order.Order;
import com.hmdandelion.project_1410002.sales.domain.entity.order.OrderProduct;
import com.hmdandelion.project_1410002.sales.domain.repository.estimate.EstimateRepo;
import com.hmdandelion.project_1410002.sales.domain.repository.order.OrderRepo;
import com.hmdandelion.project_1410002.sales.domain.type.OrderStatus;
import com.hmdandelion.project_1410002.sales.dto.response.OrderResponse;
import com.hmdandelion.project_1410002.sales.dto.response.OrdersResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final EstimateRepo estimateRepo;
    private final OrderRepo orderRepo;

    private Pageable getPageable(final Integer page) {
        return PageRequest.of(page - 1, 10);
    }

    public Long save(Long estimateCode) {
        Estimate estimate = estimateRepo.findByEstimateCodeAndIsOrderedFalse(estimateCode)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_ESTIMATE_CODE));

        LocalDate today = LocalDate.now();
        if(estimate.getDeadline().isBefore(today) || estimate.getDeadline().isEqual(today)) {
            throw new BadRequestException(ExceptionCode.BAD_REQUEST_DEADLINE_PASSED);
        }

        final Order newOrder =  Order.of(
                estimate.getDeadline(),
                estimate.getClientCode(),
                estimateCode
        );

        List<OrderProduct> orderProducts = estimate.getEstimateProducts().stream()
                .map(product -> {
                    return OrderProduct.of(
                            product.getQuantity(),
                            product.getPrice(),
                            product.getProductCode(),
                            newOrder
                    );
                }).toList();

        newOrder.modifyProducts(orderProducts);
        final Order order = orderRepo.save(newOrder);
        estimate.updateOrdered();

        return order.getOrderCode();
    }

    @Transactional(readOnly = true)
    public Page<OrdersResponse> getOrders(
            Integer page, LocalDate startDate, LocalDate endDate,
            String clientName, String status, String productName, String sort
    ) {
        Page<OrdersResponse> orders = orderRepo.search(getPageable(page), startDate, endDate, clientName, status, productName, sort);
        return orders;
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrder(Long orderCode) {
        OrderResponse orderResponse = orderRepo.getOrder(orderCode)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_ORDER_CODE));
        return orderResponse;
    }

    public void cancel(Long orderCode) {
        Order order = orderRepo.findByOrderCodeAndStatus(orderCode, OrderStatus.ORDER_RECEIVED)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_ORDER_CODE));

        order.modifyStatus(OrderStatus.CANCELED);
    }
}
