package com.hmdandelion.project_1410002.inventory.presentation;

import com.hmdandelion.project_1410002.common.paging.Pagination;
import com.hmdandelion.project_1410002.common.paging.PagingButtonInfo;
import com.hmdandelion.project_1410002.common.paging.PagingResponse;
import com.hmdandelion.project_1410002.inventory.domian.entity.product.Product;
import com.hmdandelion.project_1410002.inventory.dto.release.response.ReleaseOrderLack;
import com.hmdandelion.project_1410002.inventory.dto.release.response.ReleaseOrderProduct;
import com.hmdandelion.project_1410002.inventory.dto.release.response.ReleasePossible;
import com.hmdandelion.project_1410002.inventory.service.ReleaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReleaseController {

    private final ReleaseService releaseService;

    /*현재 주문 목록과 함께 출고 가능 여부를 함께 조회*/
    @GetMapping("/release/orders")
    public ResponseEntity<PagingResponse> getReleasePossibles(
            @RequestParam(defaultValue = "1") final Integer page,
            @RequestParam(required = false) final Boolean isReleasePossible,
            @RequestParam(defaultValue = "true") final Boolean createdSort
    ){
        final Page<ReleasePossible> releasePossibles = releaseService.getReleasePossibles(page, isReleasePossible, createdSort);
        final PagingButtonInfo pagingButtonInfo = Pagination.getPagingButtonInfo(releasePossibles);
        final PagingResponse pagingResponse = PagingResponse.of(releasePossibles.getContent(), pagingButtonInfo);

        return ResponseEntity.ok(pagingResponse);
    }

    /*주문 코드로 주문에 해당하는 상품명과 상품 갯수 출력*/
    @GetMapping("/release/order/{orderCode}")
    public ResponseEntity<List<ReleaseOrderProduct>> getReleaseOrderProduct(
            @PathVariable final Long orderCode
    ){
        List<ReleaseOrderProduct> releaseOrderProducts= releaseService.getReleaseOrderProduct(orderCode);
        return ResponseEntity.ok(releaseOrderProducts);
    }

    /*주문 코드 입력하여 상품 별 부족 갯수 출력*/
    @GetMapping("/release/order/lack/{orderCode}")
    public ResponseEntity<List<ReleaseOrderLack>> getReleaseOrderLack(
            @PathVariable final Long orderCode
    ){
        List<ReleaseOrderLack> releaseOrderLacks = releaseService.getReleaseOrderLack(orderCode);
        return ResponseEntity.ok(releaseOrderLacks);
    }

}
