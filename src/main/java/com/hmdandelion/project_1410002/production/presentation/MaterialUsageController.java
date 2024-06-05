package com.hmdandelion.project_1410002.production.presentation;

import com.hmdandelion.project_1410002.common.paging.Pagination;
import com.hmdandelion.project_1410002.common.paging.PagingButtonInfo;
import com.hmdandelion.project_1410002.common.paging.PagingResponse;
import com.hmdandelion.project_1410002.production.domain.entity.material.MaterialUsage;
import com.hmdandelion.project_1410002.production.dto.material.MaterialUsageDTO;
import com.hmdandelion.project_1410002.production.dto.material.response.MaterialUsageResponse;
import com.hmdandelion.project_1410002.production.service.MaterialUsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/material")
public class MaterialUsageController {

    private final MaterialUsageService materialUsageService;

    //사용 조회
    @GetMapping("/use")
    public ResponseEntity<PagingResponse> searchUse(
            @RequestParam(defaultValue = "1") final int page,
            @RequestParam(required = false) String materialName,
            @RequestParam(defaultValue = "not_complete") String sortType
    ) {
        Pageable pageable = PageRequest.of(page - 1, 10);
        List<MaterialUsageDTO> list = materialUsageService.searchUse(pageable, materialName, sortType);
        Page<MaterialUsageDTO> toPage = new PageImpl<>(list, pageable, list.size());

        PagingButtonInfo pagingButtonInfo = Pagination.getPagingButtonInfo(toPage);
        PagingResponse res = new PagingResponse(list, pagingButtonInfo);

        return ResponseEntity.ok(res);
    }

    //상세조회
    @GetMapping("/use/{usageCode}")
    public ResponseEntity<MaterialUsageResponse> getUse(
            @PathVariable final Long usageCode
    ) {

    }
}
