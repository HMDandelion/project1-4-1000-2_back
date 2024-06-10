package com.hmdandelion.project_1410002.employee.presentation;

import com.hmdandelion.project_1410002.employee.dto.EmployeeInfoDTO;
import com.hmdandelion.project_1410002.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/employee/${employeeNo}")
    public ResponseEntity<EmployeeInfoDTO> getEmployeeInfo(@PathVariable String employeeNo) {
        EmployeeInfoDTO employeeInfo = employeeService.getInfoByEmployeeNo(employeeNo);
        return ResponseEntity.ok(employeeInfo);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal UserDetails userDetails) {
        employeeService.updateRefreshToken(userDetails.getUsername(), null);
        return ResponseEntity.ok().build();
    }
}
