package com.hmdandelion.project_1410002.inventory.presentation;

import com.hmdandelion.project_1410002.common.paging.Pagination;
import com.hmdandelion.project_1410002.common.paging.PagingButtonInfo;
import com.hmdandelion.project_1410002.common.paging.PagingResponse;
import com.hmdandelion.project_1410002.inventory.dto.release.response.ReleaseOrderLackDTO;
import com.hmdandelion.project_1410002.inventory.dto.release.response.ReleaseOrderProduct;
import com.hmdandelion.project_1410002.inventory.dto.release.response.ReleasePossible;
import com.hmdandelion.project_1410002.inventory.dto.release.response.ReleaseStorage;
import com.hmdandelion.project_1410002.inventory.dto.stock.response.ReleaseCompleteDTO;
import com.hmdandelion.project_1410002.inventory.dto.stock.response.ReleaseShippingDTO;
import com.hmdandelion.project_1410002.inventory.dto.stock.response.ReleaseWaitDTO;
import com.hmdandelion.project_1410002.inventory.service.ReleaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
    public ResponseEntity<List<ReleaseOrderLackDTO>> getReleaseOrderLack(
            @PathVariable final Long orderCode
    ){
        List<ReleaseOrderLackDTO> releaseOrderLacks = releaseService.getReleaseOrderLack(orderCode);
        return ResponseEntity.ok(releaseOrderLacks);
    }

    /*출고 등록(해당하는 주문의 상품의 보관 이력 중 가장 오래 보관 된 것의 실수량 감소)*/
    @PostMapping("/release/{orderCode}")
    public ResponseEntity<Void> saveRelease(
            @PathVariable final Long orderCode
    ){
        Long releaseCode = releaseService.saveRelease(orderCode);
        return ResponseEntity.created(URI.create("/api/v1/releases/orders")).build();
    }

    /*주문 코드로 출고 재고 정보 상세 보기*/
    @GetMapping("/release/storage/{orderCode}")
    public ResponseEntity<List<ReleaseStorage>> getStorageByOrderCode(
            @PathVariable final Long orderCode
    ){
        List<ReleaseStorage> releaseStorages = releaseService.getStorageByOrderCode(orderCode);
        return ResponseEntity.ok(releaseStorages);
    }

    /*출고 대기 중인 재고 조회*/
    @GetMapping("/release/wait")
    public ResponseEntity<List<ReleaseWaitDTO>> getReleaseWait(
            @RequestParam(defaultValue = "true") final Boolean deadLineSort
    ){
        List<ReleaseWaitDTO> releaseWaits = releaseService.getReleaseWait(deadLineSort);
        return ResponseEntity.ok(releaseWaits);
    }

    /*출고 대기 중인 재고 배송*/
    @PutMapping("/release/shipping/{orderCode}")
    public ResponseEntity<Void> shippingOrder(
            @PathVariable final Long orderCode
    ){
        releaseService.shippingOrder(orderCode);
        return ResponseEntity.created(URI.create("/api/v1/releases/orders")).build();
    }

    /*배송 중인 재고 조회*/
    @GetMapping("/release/shipping")
    public ResponseEntity<List<ReleaseShippingDTO>> getReleaseShipping(
            @RequestParam(defaultValue = "true") final Boolean deadLineSort
    ){
        List<ReleaseShippingDTO> releaseShipping = releaseService.getReleaseShipping(deadLineSort);
        return ResponseEntity.ok(releaseShipping);
    }

    /*배송 완료*/
    @PutMapping("/release/complete/{orderCode}")
    public ResponseEntity<List<Void>> getReleaseComplete(
        @PathVariable final Long orderCode
    ){
        releaseService.completeOrder(orderCode);
        return ResponseEntity.created(URI.create("/api/v1/releases/orders")).build();
    }

    /*배송 완료 재고 조회*/
    @GetMapping("/release/complete")
    public ResponseEntity<List<ReleaseCompleteDTO>> getReleaseComplete(
        @RequestParam(defaultValue = "true") final  Boolean completeAt
    ){
        List<ReleaseCompleteDTO> releaseComplete = releaseService.getReleaseComplete(completeAt);
        return ResponseEntity.ok(releaseComplete);
    }

}