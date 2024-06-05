package com.hmdandelion.project_1410002.sales.domain.repository.employee;

import com.hmdandelion.project_1410002.sales.domain.entity.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<Employee,Long>,EmployeeRepoCustom {
}
