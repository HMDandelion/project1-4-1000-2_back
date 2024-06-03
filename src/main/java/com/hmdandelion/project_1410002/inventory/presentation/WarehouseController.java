package com.hmdandelion.project_1410002.inventory.presentation;

import com.hmdandelion.project_1410002.common.paging.Pagination;
import com.hmdandelion.project_1410002.common.paging.PagingButtonInfo;
import com.hmdandelion.project_1410002.common.paging.PagingResponse;
import com.hmdandelion.project_1410002.inventory.domian.entity.warehouse.Warehouse;
import com.hmdandelion.project_1410002.inventory.dto.warehouse.request.WarehouseCreateRequest;
import com.hmdandelion.project_1410002.inventory.dto.warehouse.request.WarehouseUpdateRequest;
import com.hmdandelion.project_1410002.inventory.dto.warehouse.response.WarehouseResponse;
import com.hmdandelion.project_1410002.inventory.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PostMapping("/warehouse")
    public ResponseEntity<Void> getWarehouses(
            @RequestBody final WarehouseCreateRequest warehouseCreateRequest
    ) {
        Long warehouseCode = warehouseService.save(warehouseCreateRequest);
        return ResponseEntity.created(URI.create("/api/v1" + warehouseCode)).build();
    }

    @GetMapping("/warehouse")
    public ResponseEntity<List<WarehouseResponse>> getWarehouses(

    ) {
        final List<WarehouseResponse> warehouseResponses = warehouseService.getWarehouses();


        return ResponseEntity.ok(warehouseResponses);
    }

    @GetMapping("/warehouse/{warehouseCode}")
    public ResponseEntity<WarehouseResponse> getWarehouse(
            @PathVariable final Long warehouseCode
    ) {
        Warehouse warehouse = warehouseService.getWarehouse(warehouseCode);
        WarehouseResponse warehouseResponse = WarehouseResponse.of(
                warehouse.getWarehouseCode(),
                warehouse.getName(),
                warehouse.getLocation(),
                warehouse.getVolume(),
                warehouse.getEmployeeCode()
        );
        return ResponseEntity.ok(warehouseResponse);
    }

    @PutMapping("/warehouse/{warehouseCode}")
    public ResponseEntity<Void> modify(
            @PathVariable final Long warehouseCode,
            @RequestBody final WarehouseUpdateRequest warehouseUpdateRequest
    ) {
        warehouseService.modify(warehouseCode, warehouseUpdateRequest);

        return ResponseEntity.created(URI.create("/api/v1" + warehouseCode)).build();
    }

    @DeleteMapping("/warehouse/{warehouseCode}")
    public ResponseEntity<Void> remove(
            @PathVariable final Long warehouseCode
    ) {
        warehouseService.delete(warehouseCode);
        return ResponseEntity.noContent().build();
    }
}
