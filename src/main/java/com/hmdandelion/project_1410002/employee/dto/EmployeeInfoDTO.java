package com.hmdandelion.project_1410002.employee.dto;

import com.hmdandelion.project_1410002.employee.domain.entity.Department;
import com.hmdandelion.project_1410002.employee.domain.entity.Employee;
import com.hmdandelion.project_1410002.employee.domain.entity.Position;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EmployeeInfoDTO {
    private final String employeeName;
    private final String email;
    private final String positionName;
    private final String departmentName;

    public EmployeeInfoDTO(Employee e, Position p, Department d) {
        this.employeeName = e.getEmployeeName();
        this.email = e.getEmail();
        this.positionName = p.getPositionName();
        this.departmentName = d.getDepartmentName();
    }
}
