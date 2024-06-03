package com.hmdandelion.project_1410002.sales.presentation;

import com.hmdandelion.project_1410002.common.paging.Pagination;
import com.hmdandelion.project_1410002.common.paging.PagingButtonInfo;
import com.hmdandelion.project_1410002.common.paging.PagingResponse;
import com.hmdandelion.project_1410002.sales.dto.response.OrderResponse;
import com.hmdandelion.project_1410002.sales.dto.response.OrdersResponse;
import com.hmdandelion.project_1410002.sales.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<Void> applyOrder(@RequestParam Long estimateCode) {
        final Long orderCode = orderService.save(estimateCode);
        return ResponseEntity.created(URI.create("/api/v1/orders/" + orderCode)).build();
    }

    @GetMapping("/orders")
    public ResponseEntity<PagingResponse> getOrders(
            @RequestParam(defaultValue = "1") final Integer page,
            @RequestParam(required = false, defaultValue = "1900-01-01") @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate startDate,
            @RequestParam(required = false, defaultValue = "2999-01-01") @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate endDate,
            @RequestParam(required = false) final String clientName,
            @RequestParam(required = false) final String status,
            @RequestParam(required = false) final String productName,
            @RequestParam(required = false) final String sort
    ) {
        Page<OrdersResponse> orders = orderService.getOrders(page, startDate, endDate, clientName, status, productName, sort);
        final PagingButtonInfo pagingButtonInfo = Pagination.getPagingButtonInfo(orders);
        final PagingResponse pagingResponse = PagingResponse.of(orders.getContent(), pagingButtonInfo);

        return ResponseEntity.ok(pagingResponse);
    }

    @GetMapping("/orders/{orderCode}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable final Long orderCode) {
        OrderResponse orderResponse = orderService.getOrder(orderCode);
        return ResponseEntity.ok(orderResponse);
    }

    @PutMapping("/orders/{orderCode}")
    public ResponseEntity<Void> cancelOrder(@PathVariable final Long orderCode) {
        orderService.cancel(orderCode);
        return ResponseEntity.created(URI.create("/api/v1/orders/" + orderCode)).build();
    }
}
