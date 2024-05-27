package com.hmdandelion.project_1410002.product.presentation;

import com.hmdandelion.project_1410002.common.paging.Pagenation;
import com.hmdandelion.project_1410002.common.paging.PagingButtonInfo;
import com.hmdandelion.project_1410002.common.paging.PagingResponse;
import com.hmdandelion.project_1410002.product.domain.dto.request.ProductRequest;
import com.hmdandelion.project_1410002.product.domain.dto.response.ProductsResponse;
import com.hmdandelion.project_1410002.product.domain.entity.Product;
import com.hmdandelion.project_1410002.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/product")
    public ResponseEntity<PagingResponse> getProducts(
            @RequestParam(defaultValue = "1") Integer page
    ){
        final Page<ProductsResponse> products = productService.getProducts(page);
        final PagingButtonInfo pagingButtonInfo = Pagenation.getPagingButtonInfo(products);
        final PagingResponse pagingResponse = PagingResponse.of(products.getContent(), pagingButtonInfo);

        return ResponseEntity.ok(pagingResponse);
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
