package com.hmdandelion.project_1410002.purchase.presentation;

import com.hmdandelion.project_1410002.common.paging.PagingResponse;
import com.hmdandelion.project_1410002.inventory.dto.material.dto.MaterialGraphModel;
import com.hmdandelion.project_1410002.inventory.dto.material.response.MaterialGraphResponse;
import com.hmdandelion.project_1410002.purchase.dto.material.MaterialClientDTO;
import com.hmdandelion.project_1410002.purchase.service.MaterialOrderAnalyzeService;
import com.hmdandelion.project_1410002.purchase.service.MaterialOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/material")
@RequiredArgsConstructor
public class MaterialOrderAnalyzeController {

    private final MaterialOrderAnalyzeService materialOrderAnalyzeService;

    //필요 자재량 대비 주문량
    @GetMapping("/order-quantity/{planCode}")
    public ResponseEntity<MaterialGraphResponse> findOrderByMaterialRequirementRatio(@PathVariable Long planCode) {
        List<MaterialGraphModel> list = materialOrderAnalyzeService.findOrderByMaterialRequirementRatio(planCode);

        MaterialGraphResponse res = MaterialGraphResponse.from(list);
        return ResponseEntity.ok(res);
    }
    //원자재 목록으로 주문 가능한 업체 조회
    @GetMapping("/order-materials")
    public ResponseEntity<PagingResponse> getClientBySpecList(
            @RequestParam(defaultValue = "1") final int page,
            @RequestParam final List<Long> specCodes
    ) {
        Pageable pageable = PageRequest.of(page - 1, 100); //페이지 구분을 줄이기위해 100개로 제한

        List<MaterialClientDTO> materialClients = materialOrderAnalyzeService.getClientBySpecList(specCodes);

        PagingResponse res = new PagingResponse(materialClients, null);

        return ResponseEntity.ok(res);
    }
}
