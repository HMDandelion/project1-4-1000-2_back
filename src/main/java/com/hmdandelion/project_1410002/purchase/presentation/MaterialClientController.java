package com.hmdandelion.project_1410002.purchase.presentation;

import com.hmdandelion.project_1410002.common.paging.Pagination;
import com.hmdandelion.project_1410002.common.paging.PagingButtonInfo;
import com.hmdandelion.project_1410002.common.paging.PagingResponse;
import com.hmdandelion.project_1410002.purchase.dto.material.MaterialClientDTO;
import com.hmdandelion.project_1410002.purchase.dto.material.response.MaterialClientDetailResponse;
import com.hmdandelion.project_1410002.purchase.service.MaterialClientService;
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
public class MaterialClientController {

    private final MaterialClientService materialClientService;

    //전체 공급업체 조회 및 검색
    @GetMapping("/clients")
    public ResponseEntity<PagingResponse> searchClients(
            @RequestParam(defaultValue = "1") final int page,
            @RequestParam(required = false) final String clientName
    ) {
        Pageable pageable = PageRequest.of(page - 1, 10);
        List<MaterialClientDTO> clients = materialClientService.searchClients(pageable, clientName);
        Page<MaterialClientDTO> toPage = new PageImpl<>(clients, pageable, clients.size());

        PagingButtonInfo pagingButtonInfo = Pagination.getPagingButtonInfo(toPage);
        PagingResponse res = new PagingResponse(clients, pagingButtonInfo);
        return ResponseEntity.ok(res);
    }

    // 공급업체 상세 조회
    @GetMapping("/clients/{clientCode}")
    public ResponseEntity<MaterialClientDetailResponse> getDetail(
            @PathVariable final Long clientCode
    ) {
        MaterialClientDetailResponse res = materialClientService.getDetail(clientCode);
        return ResponseEntity.ok(res);
    }

    //공급업체 삭제
    @DeleteMapping("/clients")
    public ResponseEntity<Void> deleteClients(
            @RequestParam final Long clientCode
    ) {
        materialClientService.deleteClients(clientCode);
        return ResponseEntity.noContent().build();
    }
    // 공급업체 상세 조회

}
