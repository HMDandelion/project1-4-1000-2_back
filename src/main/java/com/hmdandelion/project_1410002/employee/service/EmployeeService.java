package com.hmdandelion.project_1410002.employee.service;

import com.hmdandelion.project_1410002.auth.dto.LoginDTO;
import com.hmdandelion.project_1410002.common.exception.NotFoundException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.employee.domain.entity.Employee;
import com.hmdandelion.project_1410002.employee.domain.repository.EmployeeRepo;
import com.hmdandelion.project_1410002.employee.domain.type.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepo employeeRepo;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public LoginDTO findByEmployeeNo(String employeeNo) {
        Employee employee = employeeRepo.findByEmployeeNo(employeeNo)
                .orElseThrow(() -> new UsernameNotFoundException("해당 사번을 가진 사원이 존재하지 않습니다."));

        List<Authority> authorities = getEmployeeAuthorities(employee.getEmployeeCode());
        return LoginDTO.from(employee, authorities);
    }

    @Transactional(readOnly = true)
    public List<Authority> getEmployeeAuthorities(Long employeeCode) {
        return employeeRepo.findAuthNamesByEmployeeNo(employeeCode);
    }

    public void updateRefreshToken(String employeeNo, String refreshToken) {
        Employee employee = employeeRepo.findByEmployeeNo(employeeNo)
                .orElseThrow(() -> new UsernameNotFoundException("해당 사번을 가진 사원이 존재하지 않습니다."));

        employee.updateRefreshToken(refreshToken);
    }

    @Transactional(readOnly = true)
    public LoginDTO findByRefreshToken(String refreshToken) {
        Employee employee = employeeRepo.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_REFRESH_TOKEN));

        List<Authority> authorities = getEmployeeAuthorities(employee.getEmployeeCode());
        return LoginDTO.from(employee, authorities);
    }
}
