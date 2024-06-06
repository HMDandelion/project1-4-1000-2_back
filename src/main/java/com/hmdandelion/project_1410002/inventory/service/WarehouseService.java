package com.hmdandelion.project_1410002.inventory.service;

import com.hmdandelion.project_1410002.common.exception.CustomException;
import com.hmdandelion.project_1410002.common.exception.NotFoundException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.employee.domain.entity.Employee;
import com.hmdandelion.project_1410002.employee.domain.repository.EmployeeRepo;
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

import static com.hmdandelion.project_1410002.common.exception.type.ExceptionCode.NOT_FOUND_WAREHOUSE_CODE;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WarehouseService {

    private final WarehouseRepo warehouseRepository;
    private final EmployeeRepo employeeRepo;

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
    public List<WarehouseResponse> getWarehouses() {
        List<WarehouseResponse> resultList = new ArrayList<>();

        List<Warehouse> warehouses  = warehouseRepository.findAll();

        for(Warehouse warehouse : warehouses) {
            Employee employee = employeeRepo.findById(warehouse.getEmployeeCode()).orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_EMPLOYEE_CODE));
            WarehouseResponse warehouseResponse = WarehouseResponse.from(
                    warehouse,
                    employee
            );
            resultList.add(warehouseResponse);
        }
        return resultList;
    }
    @Transactional(readOnly = true)
    public WarehouseResponse getWarehouse(Long warehouseCode) {
        Warehouse warehouse = warehouseRepository.findById(warehouseCode).orElseThrow(() ->
            new CustomException(ExceptionCode.NOT_FOUND_WAREHOUSE_CODE)
        );
        System.out.println("employee"+warehouse.getEmployeeCode());
        Employee employee = employeeRepo.findById(warehouse.getEmployeeCode()).orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_EMPLOYEE_CODE));
        WarehouseResponse warehouseResponse = WarehouseResponse.of(
                warehouse.getWarehouseCode(),
                warehouse.getName(),
                warehouse.getLocation(),
                warehouse.getVolume(),
                employee.getEmployeeName()
        );
        return warehouseResponse;
    }

    public void modify(Long warehouseCode, WarehouseUpdateRequest warehouseUpdateRequest) {
        Warehouse warehouse = warehouseRepository.findById(warehouseCode).orElseThrow(() ->
                new CustomException(ExceptionCode.NOT_FOUND_WAREHOUSE_CODE)
        );
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
