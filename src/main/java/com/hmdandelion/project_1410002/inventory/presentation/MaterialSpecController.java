package com.hmdandelion.project_1410002.inventory.presentation;

import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialSpec;
import com.hmdandelion.project_1410002.inventory.dto.material.MaterialSpecDTO;
import com.hmdandelion.project_1410002.inventory.dto.material.response.MaterialSpecRes;
import com.hmdandelion.project_1410002.inventory.service.MaterialSpecService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/v1/material")
@RequiredArgsConstructor
public class MaterialSpecController {

    private static final Logger log = LoggerFactory.getLogger(MaterialSpecController.class);
    private final MaterialSpecService materialSpecService;

    //모든 스펙 조회
    @GetMapping("/spec")
    public ResponseEntity<MaterialSpecRes> findAllSpec(
            @RequestParam(defaultValue = "1") final Integer page,
            @RequestParam(required = false) final String materialName
    ) {
        Pageable pageable = PageRequest.of(page-1, 10);
        log.info("검색값...{}",materialName);
        log.info("현재 페이지번호...{}",pageable.getOffset());
        log.info("마지막 페이지번호...{}",pageable.getPageSize());
        List<MaterialSpecDTO> list = materialSpecService.searchMaterialSpec(pageable,materialName);
        log.info("조회된 값 총 {}개",list.size());
        for (MaterialSpecDTO dto : list) {
            log.info("조회된 객체...{}",dto.getMaterialName());
        }
        MaterialSpecRes res = MaterialSpecRes.from(list);
        return ResponseEntity.ok(res);
    }
}
