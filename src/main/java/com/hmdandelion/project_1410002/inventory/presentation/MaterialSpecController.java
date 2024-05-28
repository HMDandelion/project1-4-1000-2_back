package com.hmdandelion.project_1410002.inventory.presentation;

import com.hmdandelion.project_1410002.common.exception.BedRequestException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.inventory.dto.material.MaterialSpecDTO;
import com.hmdandelion.project_1410002.inventory.dto.material.request.SaveMaterialSpecRequest;
import com.hmdandelion.project_1410002.inventory.dto.material.response.MaterialSpecRes;
import com.hmdandelion.project_1410002.inventory.service.MaterialSpecCategoryService;
import com.hmdandelion.project_1410002.inventory.service.MaterialSpecService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public ResponseEntity<MaterialSpecRes> findAllSpec(
            @RequestParam(defaultValue = "1") final Integer page,
            @RequestParam(required = false) final String materialName
    ) {
        Pageable pageable = PageRequest.of(page - 1, 10);
        log.info("검색값...{}", materialName);
        log.info("현재 페이지번호...{}", pageable.getOffset());
        log.info("마지막 페이지번호...{}", pageable.getPageSize());
        List<MaterialSpecDTO> list = materialSpecService.searchMaterialSpec(pageable, materialName);
        log.info("조회된 값 총 {}개", list.size());
        for (MaterialSpecDTO dto : list) {
            log.info("조회된 객체...{}", dto.getMaterialName());
        }
        MaterialSpecRes res = MaterialSpecRes.from(list);
        return ResponseEntity.ok(res);
    }

    //스펙 등록/수정
    @PostMapping("/spec")
    public ResponseEntity<Void> registAndModifySpec(
            @RequestBody final SaveMaterialSpecRequest request
    ) {
        log.info("요청된 리퀘스트 내용 : {}",request);
        final Long specCode = materialSpecService.save(request);
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
}
