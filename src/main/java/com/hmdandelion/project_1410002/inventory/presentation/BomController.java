package com.hmdandelion.project_1410002.inventory.presentation;

import com.hmdandelion.project_1410002.common.paging.Pagination;
import com.hmdandelion.project_1410002.common.paging.PagingButtonInfo;
import com.hmdandelion.project_1410002.common.paging.PagingResponse;
import com.hmdandelion.project_1410002.inventory.domian.entity.product.Bom;
import com.hmdandelion.project_1410002.inventory.dto.product.request.BomCreateRequest;
import com.hmdandelion.project_1410002.inventory.dto.product.request.BomRequest;
import com.hmdandelion.project_1410002.inventory.dto.product.request.BomUpdateRequest;
import com.hmdandelion.project_1410002.inventory.dto.product.response.BomResponse;
import com.hmdandelion.project_1410002.inventory.service.BomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BomController {
    private final BomService bomService;

    /*BOM 전체 조회*/
    @GetMapping("/bom")
    public ResponseEntity<List<Bom>> getBoms(){
        List<Bom> boms =  bomService.getBoms();

        return ResponseEntity.ok(boms);
    }

    /*bomCode로 BOM조회*/
    @GetMapping("/bom/{bomCode}")
    public ResponseEntity<Bom> getBomByBomCode(
            @PathVariable final Long bomCode
    ){
        Bom bom = bomService.getBomByBomCode(bomCode);
        return ResponseEntity.ok(bom);
    }

    /*상품 코드로 BOM조회(BOM상세 보기)*/
    @GetMapping("/bom/product/{productCode}")
    public ResponseEntity<List<Bom>> getBomByProductCode(
            @PathVariable final Long productCode
    ){
        List<Bom> boms = bomService.getBomByProductCode(productCode);

        return ResponseEntity.ok(boms);
    }

    /*상품 코드로 BOM조회(BOM상세 보기) 페이징*/
    @GetMapping("/bom/product/page/{productCode}")
    public ResponseEntity<PagingResponse> getBomByPageProductCode(
            @PathVariable final Long productCode,
            @RequestParam(defaultValue = "1") final Integer page
    ){
        Page<Bom> boms = bomService.getBomByPageProductCode(productCode,page);
        final PagingButtonInfo pagingButtonInfo = Pagination.getPagingButtonInfo(boms);
        final PagingResponse pagingResponse = PagingResponse.of(boms.getContent(), pagingButtonInfo);

        return ResponseEntity.ok(pagingResponse);
    }

    /*productCode에 해당하는 상품의 BOM 추가*/
    @PostMapping("/bom/product/{productCode}")
    public ResponseEntity<Void> saveBomByProductCode(
            @PathVariable final Long productCode,
            @RequestBody final BomCreateRequest bomRequest
    ){
        Long bomCode = bomService.saveBomByProductCode(productCode,bomRequest);
        return ResponseEntity.created(URI.create("/api/v1/bom/product"+bomCode)).build();
    }

    /*bomCode에해당하는 상품의 BOM 수정*/
    @PutMapping("/bom/{bomCode}")
    public ResponseEntity<Void> modifyBomByProductCode(
            @PathVariable final Long bomCode,
            @RequestBody final BomUpdateRequest bomRequest
    ){
        bomService.modifyBomByBomCode(bomCode,bomRequest);
        return ResponseEntity.created(URI.create("/api/v1/bom"+bomCode)).build();
    }

    /*bomCode에 해당 하는 BOM삭제*/
    @DeleteMapping("/bom/{bomCode}")
    public ResponseEntity<Void> deleteByBomCode(
            @PathVariable final Long bomCode
    ){
        bomService.deleteByBomCode(bomCode);
        return ResponseEntity.noContent().build();
    }
}
