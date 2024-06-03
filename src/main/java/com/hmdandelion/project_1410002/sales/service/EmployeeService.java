package com.hmdandelion.project_1410002.sales.service;

import com.hmdandelion.project_1410002.common.exception.NotFoundException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.sales.domain.entity.employee.Employee;
import com.hmdandelion.project_1410002.sales.domain.repository.employee.EmployeeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepo employeeRepo;

    public Employee findById(Long employeeCode) {
        return employeeRepo.findById(employeeCode).orElseThrow(
                ()-> new NotFoundException(ExceptionCode.NOT_FOUND_EMPLOYEE_CODE)
        );
    }

    public String findDepartmentNameById(Long departmentCode) {
        return employeeRepo.getDepartmentName(departmentCode);
    }

    public String findPositionNameById(Long positionCode) {
        return employeeRepo.getPositonName(positionCode);
    }
}
