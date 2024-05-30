package com.hmdandelion.project_1410002.purchase.presentation;

import com.hmdandelion.project_1410002.inventory.dto.material.dto.MaterialGraphModel;
import com.hmdandelion.project_1410002.inventory.dto.material.response.MaterialGraphResponse;
import com.hmdandelion.project_1410002.purchase.service.MaterialOrderAnalyzeService;
import com.hmdandelion.project_1410002.purchase.service.MaterialOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/material")
@RequiredArgsConstructor
public class MaterialOrderAnalyzeController {

    private final MaterialOrderAnalyzeService materialOrderAnalyzeService;

    @GetMapping("/order-quantity/{planCode}")
    public ResponseEntity<MaterialGraphResponse> findOrderByMaterialRequirementRatio(@PathVariable Long planCode) {
        List<MaterialGraphModel> list = materialOrderAnalyzeService.findOrderByMaterialRequirementRatio(planCode);

        MaterialGraphResponse res = MaterialGraphResponse.from(list);
        return ResponseEntity.ok(res);
    }
}
