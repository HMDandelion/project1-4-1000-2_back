package com.hmdandelion.project_1410002.inventory.presentation;

import com.hmdandelion.project_1410002.inventory.dto.product.request.ProductSpecRequest;
import com.hmdandelion.project_1410002.inventory.dto.product.response.ProductSpecResponse;
import com.hmdandelion.project_1410002.inventory.service.ProductSpecService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProductSpecController {

    private final ProductSpecService productSpecService;

    /*상품 스펙 등록*/
    @PostMapping("/productSpec/product/{productCode}")
    public ResponseEntity<Void> saveProductSpec(
            @PathVariable final Long productCode,
            @RequestBody final ProductSpecRequest productSpecRequest
    ){
        productSpecService.saveProductSpec(productCode,productSpecRequest);
        return ResponseEntity.created(URI.create("/api/v1/productSpec")).build();
    }

    /*상품 스펙 전체 조회*/
    @GetMapping("/productSpec")
    public ResponseEntity<List<ProductSpecResponse>> getProductSpecs(){
        List<ProductSpecResponse> productSpecs = productSpecService.getProductSpecs();
        return ResponseEntity.ok(productSpecs);
    }

    /*상품 스펙 상품 코드로 조회*/
    @GetMapping("/productSpec/product/{productCode}")
    public ResponseEntity<List<ProductSpecResponse>> getProductSpecByProductCode(
            @PathVariable final Long productCode
    ){
        List<ProductSpecResponse> productSpecResponse = productSpecService.getProductSpec(productCode);
        return ResponseEntity.ok(productSpecResponse);
    }

    /*상품 스펙 수정*/
    @PutMapping("/productSpec/{code}")
    public ResponseEntity<Void> modifyProductSpecByProductSpecCode(
            @PathVariable final Long code,
            @RequestBody final ProductSpecRequest productSpecRequest
    ){
        productSpecService.modifyProductSpecByProductSpecCode(code,productSpecRequest);
        return ResponseEntity.created(URI.create("/api/v1/productSpec")).build();
    }

    /*상품 스펙 삭제*/
    @DeleteMapping("/productSpec/{code}")
    public ResponseEntity<Void> deleteProductSpec(
            @PathVariable final Long code
    ){
        productSpecService.deleteProductSpec(code);
        return ResponseEntity.noContent().build();
    }

}
