package com.hmdandelion.project_1410002.inventory.presentation;


import com.hmdandelion.project_1410002.inventory.domian.entity.product.Product;
import com.hmdandelion.project_1410002.inventory.domian.type.ProductStatus;
import com.hmdandelion.project_1410002.inventory.dto.product.request.ProductRequest;
import com.hmdandelion.project_1410002.inventory.dto.product.response.ProductsResponse;
import com.hmdandelion.project_1410002.inventory.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/product")
    public List<Product> getProducts(
            @RequestParam (defaultValue = "1")final Integer page,
            @RequestParam(required = false)  final String productName,
            @RequestParam(required = false)  final String unit,
            @RequestParam(required = false) final ProductStatus status
    ){
        Pageable pageable = PageRequest.of(page-1,10);
        return productService.searchProducts(pageable,productName,unit,status);
    }

    @GetMapping("/product/{productCode}")
    public ResponseEntity<ProductsResponse> getProduct(
            @PathVariable final Long productCode
    ){
        final ProductsResponse product = productService.getProduct(productCode);

        return ResponseEntity.ok(product);
    }
    @PostMapping("/product")
    public ResponseEntity<Void> save(
            @RequestBody final ProductRequest productRequest
    ){
        final Long productCode = productService.save(productRequest);
        return ResponseEntity.created(URI.create("/api/v1/product"+productCode)).build();
    }
    @PutMapping("/product/{productCode}")
    public ResponseEntity<ProductsResponse> modifyProduct(
            @PathVariable final Long productCode,
            @RequestBody final ProductRequest productRequest
    ){
            productService.modifyProduct(productCode,productRequest);
            return ResponseEntity.created(URI.create("/api/v1/product"+productCode)).build();
    }
    @DeleteMapping("/product/{productCode}")
    public ResponseEntity<Void> delete(
            @PathVariable final Long productCode
    ){
        productService.updateStatus(productCode);
        return ResponseEntity.noContent().build();
    }



}
