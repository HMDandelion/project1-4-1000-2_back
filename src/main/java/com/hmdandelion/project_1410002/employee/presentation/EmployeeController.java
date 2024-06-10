package com.hmdandelion.project_1410002.employee.presentation;

import com.hmdandelion.project_1410002.employee.domain.entity.Employee;
import com.hmdandelion.project_1410002.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal UserDetails userDetails) {
        employeeService.updateRefreshToken(userDetails.getUsername(), null);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/employee/list")
    public ResponseEntity<List<Employee>> getEmployee(){
        List<Employee> employee = employeeService.getEmployee();
        return ResponseEntity.ok(employee);
    }
}
