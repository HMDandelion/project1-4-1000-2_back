package com.hmdandelion.project_1410002.employee.domain.repository;

import com.hmdandelion.project_1410002.employee.domain.entity.Employee;
import com.hmdandelion.project_1410002.employee.domain.type.AuthorityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmployeeNo(String employeeNo);

    @Query(
            "SELECT a.authorityName " +
            "FROM Authority a, EmployeeAuth ea " +
            "WHERE ea.authorityCode = a.authorityCode " +
            "AND ea.employeeCode = :employeeCode"
    )
    List<AuthorityType> findAuthNamesByEmployeeNo(Long employeeCode);

    Optional<Employee> findByRefreshToken(String refreshToken);
}
