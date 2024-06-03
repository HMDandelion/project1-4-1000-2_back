package com.hmdandelion.project_1410002.inventory.service;

import com.hmdandelion.project_1410002.common.exception.CustomException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.common.exception.type.NotWarehouseException;
import com.hmdandelion.project_1410002.inventory.domian.entity.warehouse.Warehouse;
import com.hmdandelion.project_1410002.inventory.domian.repository.warehouse.WarehouseRepo;
import com.hmdandelion.project_1410002.inventory.dto.warehouse.request.WarehouseCreateRequest;
import com.hmdandelion.project_1410002.inventory.dto.warehouse.request.WarehouseUpdateRequest;
import com.hmdandelion.project_1410002.inventory.dto.warehouse.response.WarehouseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class WarehouseService {

    private final WarehouseRepo warehouseRepository;

    private Pageable getPageable(final Integer page) {
        return PageRequest.of(page - 1, 10, Sort.by("warehouseCode"));
    }

    public Long save(WarehouseCreateRequest warehouseCreateRequest) {
        Warehouse newWarehouse = Warehouse.of(
                warehouseCreateRequest.getName(),
                warehouseCreateRequest.getLocation(),
                warehouseCreateRequest.getVolume(),
                warehouseCreateRequest.getEmployeeCode()
        );

        Warehouse warehouse = warehouseRepository.save(newWarehouse);
        return warehouse.getWarehouseCode();
    }
    @Transactional(readOnly = true)
    public Page<WarehouseResponse> getWarehouses(Integer page) {
        Page<Warehouse> warehouses = warehouseRepository.findAll(getPageable(page));
        return warehouses.map(WarehouseResponse::from);
    }
    @Transactional(readOnly = true)
    public Warehouse getWarehouse(Long warehouseCode) {
        Warehouse warehouse = warehouseRepository.findById(warehouseCode).orElseThrow(() ->
            new NotWarehouseException(ExceptionCode.NOT_FOUND_WAREHOUSE_CODE)
        );

        return warehouse;
    }

    public void modify(Long warehouseCode, WarehouseUpdateRequest warehouseUpdateRequest) {
        Warehouse warehouse = getWarehouse(warehouseCode);
        warehouse.modify(
                warehouseUpdateRequest.getName(),
                warehouseUpdateRequest.getLocation(),
                warehouseUpdateRequest.getVolume(),
                warehouseUpdateRequest.getEmployeeCode()
        );

    }

    public void delete(Long warehouseCode) {
        Warehouse warehouse = warehouseRepository.findById(warehouseCode).orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_WAREHOUSE_CODE));
        warehouseRepository.delete(warehouse);
    }
}
