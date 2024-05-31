package com.hmdandelion.project_1410002.purchase.presentation;

import com.hmdandelion.project_1410002.common.paging.Pagination;
import com.hmdandelion.project_1410002.common.paging.PagingButtonInfo;
import com.hmdandelion.project_1410002.common.paging.PagingResponse;
import com.hmdandelion.project_1410002.purchase.domain.entity.material.MaterialOrder;
import com.hmdandelion.project_1410002.purchase.dto.material.MaterialOrderDTO;
import com.hmdandelion.project_1410002.purchase.service.MaterialOrderService;
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
}
