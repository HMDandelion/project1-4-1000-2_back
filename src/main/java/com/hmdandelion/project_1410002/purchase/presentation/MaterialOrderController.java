package com.hmdandelion.project_1410002.purchase.presentation;

import com.hmdandelion.project_1410002.common.paging.Pagination;
import com.hmdandelion.project_1410002.common.paging.PagingButtonInfo;
import com.hmdandelion.project_1410002.common.paging.PagingResponse;
import com.hmdandelion.project_1410002.purchase.dto.material.MaterialClientDTO;
import com.hmdandelion.project_1410002.purchase.dto.material.MaterialOrderDTO;
import com.hmdandelion.project_1410002.purchase.dto.material.response.MaterialOrderResponse;
import com.hmdandelion.project_1410002.purchase.service.MaterialOrderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    //원자재 목록으로 주문 가능한 업체 조회
    @GetMapping("/order-materials")
    public ResponseEntity<PagingResponse> getClientBySpecList(
            @RequestParam(defaultValue = "1") final int page,
            @RequestParam final List<Long> specCodes
    ) {
        Pageable pageable = PageRequest.of(page - 1, 100); //페이지 구분을 줄이기위해 100개로 제한

        List<MaterialClientDTO> materialClients = materialOrderService.getClientBySpecList(specCodes);

        PagingResponse res = new PagingResponse(materialClients, null);

        return ResponseEntity.ok(res);
    }

    @GetMapping("/orders/{orderCode}")
    public ResponseEntity<MaterialOrderResponse> findDetail(
            @PathVariable final Long orderCode
    ) {
        MaterialOrderResponse res = materialOrderService.findDetail(orderCode);

        return null;
    }
}
