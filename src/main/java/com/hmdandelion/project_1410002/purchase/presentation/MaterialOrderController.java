package com.hmdandelion.project_1410002.purchase.presentation;

import com.hmdandelion.project_1410002.common.paging.Pagination;
import com.hmdandelion.project_1410002.common.paging.PagingButtonInfo;
import com.hmdandelion.project_1410002.common.paging.PagingResponse;
import com.hmdandelion.project_1410002.purchase.dto.material.MaterialClientDTO;
import com.hmdandelion.project_1410002.purchase.dto.material.MaterialOrderDTO;
import com.hmdandelion.project_1410002.purchase.dto.material.request.MaterialOrderCreateRequest;
import com.hmdandelion.project_1410002.purchase.dto.material.request.MaterialOrderModifyRequest;
import com.hmdandelion.project_1410002.purchase.dto.material.response.MaterialOrderResponse;
import com.hmdandelion.project_1410002.purchase.dto.material.response.MaterialOrderWeeklyResponse;
import com.hmdandelion.project_1410002.purchase.service.MaterialOrderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/material")
@RequiredArgsConstructor
public class MaterialOrderController {

    private final MaterialOrderService materialOrderService;

    //주문 조회(계획코드로 조회, 전체조회, 공급업체 명으로)
    @GetMapping("/orders")
    public ResponseEntity<PagingResponse> getOrders(
            @RequestParam(defaultValue = "1") final int page,
            @RequestParam(required = false) final Long planCode,
            @RequestParam(required = false) final String clientName
    ) {
        Pageable pageable = PageRequest.of(page - 1, 10);

        List<MaterialOrderDTO> orders = materialOrderService.getOrders(planCode, clientName, pageable);
        Page<MaterialOrderDTO> toPage = new PageImpl<>(orders, pageable, orders.size());

        PagingButtonInfo pagingButtonInfo = Pagination.getPagingButtonInfo(toPage);
        PagingResponse res = new PagingResponse(orders, pagingButtonInfo);
        return ResponseEntity.ok(res);
    }


    //주문 상세 조회
    @GetMapping("/orders/{orderCode}")
    public ResponseEntity<MaterialOrderResponse> findDetail(
            @PathVariable final Long orderCode
    ) {
        MaterialOrderResponse res = materialOrderService.findDetail(orderCode);

        return ResponseEntity.ok(res);
    }
    // 주문 삭제
    @DeleteMapping("/orders/{orderCode}")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable final Long orderCode,
            @RequestParam final String deletionReason
    ) {
        materialOrderService.deleteOrder(orderCode, deletionReason);
        return ResponseEntity.noContent().build();
    }

    //주문 등록
    @PostMapping("/orders")
    public ResponseEntity<Void> createOrder(
            @RequestBody final MaterialOrderCreateRequest request
    ) {
        final Long orderCode = materialOrderService.createOrder(request);

        return ResponseEntity.created(URI.create("api/vi/material/orders/" + orderCode)).build();
    }

    //주문 수정
    @PutMapping("/orders")
    public ResponseEntity<Void> modifyOrder(
            @RequestBody final MaterialOrderModifyRequest request
    ) {
        final Long orderCode = materialOrderService.modifyOrder(request);

        return ResponseEntity.created(URI.create("api/vi/material/orders/" + orderCode)).build();
    }

    //금일 입고 예정목록 조회
    @GetMapping("/order-today")
    public ResponseEntity<PagingResponse> orderToday() {
        Pageable pageable = PageRequest.of(0, 100);

        List<MaterialOrderDTO> orders = materialOrderService.getOrderToday(pageable);
        Page<MaterialOrderDTO> toPage = new PageImpl<>(orders, pageable, orders.size());

        PagingButtonInfo pagingButtonInfo = Pagination.getPagingButtonInfo(toPage);
        PagingResponse res = new PagingResponse(orders, pagingButtonInfo);
        return ResponseEntity.ok(res);
    }

    //주간 입고 예정건 조회
    @GetMapping("/order-weekly")
    public ResponseEntity<MaterialOrderWeeklyResponse> orderWeekly() {
        MaterialOrderWeeklyResponse res = materialOrderService.orderWeekly();
        return ResponseEntity.ok(res);
    }

    @PutMapping("/order-arrival/{orderCode}")
    public ResponseEntity<Void> orderArrival(
            @PathVariable final Long orderCode
    ) {
        materialOrderService.orderArrival(orderCode);

        return ResponseEntity.created(URI.create("/api/v1/material/orders/" + orderCode)).build();
    }


}
