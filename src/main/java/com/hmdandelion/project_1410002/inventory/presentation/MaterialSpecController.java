package com.hmdandelion.project_1410002.inventory.presentation;

import com.hmdandelion.project_1410002.common.paging.Pagination;
import com.hmdandelion.project_1410002.common.paging.PagingButtonInfo;
import com.hmdandelion.project_1410002.common.paging.PagingResponse;
import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialSpec;
import com.hmdandelion.project_1410002.inventory.dto.material.dto.MaterialSpecDTO;
import com.hmdandelion.project_1410002.inventory.dto.material.request.MaterialSpecCreateRequest;
import com.hmdandelion.project_1410002.inventory.dto.material.request.MaterialSpecModifyRequest;
import com.hmdandelion.project_1410002.inventory.service.MaterialSpecCategoryService;
import com.hmdandelion.project_1410002.inventory.service.MaterialSpecService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class MaterialSpecController {

    private static final Logger log = LoggerFactory.getLogger(MaterialSpecController.class);
    private final MaterialSpecService materialSpecService;
    private final MaterialSpecCategoryService materialSpecCategoryService;

    //모든 스펙 조회
    @GetMapping("/spec")
    public ResponseEntity<PagingResponse> findAllSpec(
            @RequestParam(defaultValue = "1") final Integer page,
            @RequestParam(required = false) final String materialName
    ) {
        Pageable pageable = PageRequest.of(page - 1, 10);

        final List<MaterialSpecDTO> list = materialSpecService.searchMaterialSpec(pageable, materialName);
        for (MaterialSpecDTO dto : list) {
            log.info("조회된 객체...{}", dto.getMaterialName());
        }

        final Page<MaterialSpecDTO> toPage = new PageImpl<>(list, pageable, list.size());
        final PagingButtonInfo pagingButtonInfo = Pagination.getPagingButtonInfo(toPage);
        PagingResponse res = PagingResponse.of(toPage.getContent(), pagingButtonInfo);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/spec/list")
    public ResponseEntity<List<MaterialSpec>> findAllSpecList(){
        List<MaterialSpec> materialSpecs = materialSpecService.findAllSpecList();
        return ResponseEntity.ok(materialSpecs);
    }

    @GetMapping("/spec/{specCode}")
    public ResponseEntity<MaterialSpec> findSpec(
            @PathVariable final Long specCode
    ){
        MaterialSpec materialSpec = materialSpecService.findSpec(specCode);
        return ResponseEntity.ok(materialSpec);
    }

    //스펙 등록
    @PostMapping("/spec")
    public ResponseEntity<Void> createSpec(
            @RequestBody final MaterialSpecCreateRequest request
    ) {
        log.info("요청된 리퀘스트 내용 : {}", request);
        final Long specCode = materialSpecService.save(request);
        return ResponseEntity.created(URI.create("/api/v1/material/spec/" + specCode)).build();
    }

    //스펙 수정
    @PutMapping("/spec")
    public ResponseEntity<Void> modifySpec(
            @RequestBody final MaterialSpecModifyRequest request
    ) {
        final Long specCode = materialSpecService.modifySpec(request);

        return ResponseEntity.created(URI.create("/api/v1/material/spec/" + specCode)).build();
    }

    //스펙 삭제
    @DeleteMapping("/spec")
    public ResponseEntity<Void> deleteSpec(
            @RequestBody(required = false) final List<Long> specCodes
    ) {

        String message = materialSpecService.removeByList(specCodes);
        log.info(message);
        return ResponseEntity.noContent().build();
    }

    //스펙 분류 등록
    @PostMapping("/spec/category")
    public ResponseEntity<Void> registSpecCategory(@RequestParam final String newCategoryName) {
        materialSpecCategoryService.save(newCategoryName);
        return ResponseEntity.created(URI.create("/api/v1/material/spec/category")).build();
    }

    //스펙 분류 삭제
    @DeleteMapping("/spec/category")
    public ResponseEntity<Void> deleteSpecCateogry(@RequestParam final String categoryName) {
        materialSpecCategoryService.deleteByName(categoryName);
        return ResponseEntity.noContent().build();
    }
}
