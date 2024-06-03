package com.hmdandelion.project_1410002.sales.presentation;

import com.hmdandelion.project_1410002.common.paging.Pagination;
import com.hmdandelion.project_1410002.common.paging.PagingButtonInfo;
import com.hmdandelion.project_1410002.common.paging.PagingResponse;
import com.hmdandelion.project_1410002.sales.domain.type.ManageType;
import com.hmdandelion.project_1410002.sales.dto.request.ReturnCreateRequest;
import com.hmdandelion.project_1410002.sales.dto.response.ReturnResponse;
import com.hmdandelion.project_1410002.sales.dto.response.ReturnsResponse;
import com.hmdandelion.project_1410002.sales.service.ReturnService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReturnController {

    private final ReturnService returnService;

    @PostMapping("/returns")
    public ResponseEntity<Void> applyReturn(@RequestBody final ReturnCreateRequest returnRequest) {
        final Long returnCode = returnService.save(returnRequest);
        return ResponseEntity.created(URI.create("/api/v1/returns/" + returnCode)).build();
    }

    @GetMapping("/returns")
    public ResponseEntity<PagingResponse> getReturns(
            @RequestParam(defaultValue = "1") final Integer page,
            @RequestParam(required = false) final Long orderCode,
            @RequestParam(required = false) final String manageType,
            @RequestParam(required = false) final String clientName,
            @RequestParam(required = false) final String productName,
            @RequestParam(required = false) final String sort
    ) {
        Page<ReturnsResponse> returns = returnService.getReturns(page, orderCode, manageType, clientName, productName, sort);
        final PagingButtonInfo pagingButtonInfo = Pagination.getPagingButtonInfo(returns);
        final PagingResponse pagingResponse = PagingResponse.of(returns.getContent(), pagingButtonInfo);

        return ResponseEntity.ok(pagingResponse);
    }

    @GetMapping("/returns/{returnCode}")
    public ResponseEntity<ReturnResponse> getReturn(@PathVariable final Long returnCode) {
        ReturnResponse returnResponse = returnService.getReturn(returnCode);
        return ResponseEntity.ok(returnResponse);
    }

    @PutMapping("/returns/{returnCode}")
    public ResponseEntity<Void> cancelReturn(@PathVariable final Long returnCode) {
        returnService.cancel(returnCode);
        return ResponseEntity.created(URI.create("/api/v1/returns/" + returnCode)).build();
    }
}
