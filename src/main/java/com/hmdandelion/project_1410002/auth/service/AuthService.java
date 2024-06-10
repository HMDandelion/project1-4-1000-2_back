package com.hmdandelion.project_1410002.auth.service;

import com.hmdandelion.project_1410002.auth.dto.LoginDTO;
import com.hmdandelion.project_1410002.auth.dto.TokenDTO;
import com.hmdandelion.project_1410002.auth.type.CustomUser;
import com.hmdandelion.project_1410002.auth.util.TokenUtils;
import com.hmdandelion.project_1410002.employee.dto.EmployeeInfoDTO;
import com.hmdandelion.project_1410002.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final EmployeeService employeeService;

    @Override
    public UserDetails loadUserByUsername(String employeeNo) throws UsernameNotFoundException {
        LoginDTO loginDTO = employeeService.findByEmployeeNo(employeeNo);

        // List<GrantedAuthority> grantedAuthorities = loginDTO.getAuthorities().stream()
        //         .map(authority -> new SimpleGrantedAuthority(authority.name()))
        //         .collect(Collectors.toList());

        return User.builder()
                .username(loginDTO.getEmployeeNo())
                .password(loginDTO.getPassword())
                .authorities(loginDTO.getGrantedAuthorities())
                .build();
    }

    public void updateRefreshToken(String employeeNo, String refreshToken) {
        employeeService.updateRefreshToken(employeeNo, refreshToken);
    }

    public TokenDTO checkRefreshTokenAndReIssueToken(String refreshToken) {
        LoginDTO loginDTO = employeeService.findByRefreshToken(refreshToken);
        String reIssuedRefreshToken = TokenUtils.createRefreshToken();
        String reIssuedAccessToken = TokenUtils.createAccessToken(getMemberInfo(loginDTO));

        employeeService.updateRefreshToken(loginDTO.getEmployeeNo(), reIssuedRefreshToken);
        return TokenDTO.of(reIssuedAccessToken, reIssuedRefreshToken);
    }

    private Map<String, Object> getMemberInfo(LoginDTO loginDTO) {
        EmployeeInfoDTO employeeInfo = employeeService.getInfoByEmployeeNo(loginDTO.getEmployeeNo());

        return Map.of(
                "employeeNo", loginDTO.getEmployeeNo(),
                "authorities", loginDTO.getAuthorities(),
                "employeeName", employeeInfo.getEmployeeName(),
                "email", employeeInfo.getEmail(),
                "departmentName", employeeInfo.getDepartmentName(),
                "positionName", employeeInfo.getPositionName()
        );
    }

    public void saveAuthentication(String employeeNo) {
        LoginDTO loginDTO = employeeService.findByEmployeeNo(employeeNo);
        EmployeeInfoDTO employeeInfo = employeeService.getInfoByEmployeeNo(employeeNo);

        UserDetails user = User.builder()
                .username(loginDTO.getEmployeeNo())
                .password(loginDTO.getPassword())
                .authorities(loginDTO.getGrantedAuthorities())
                .build();

        CustomUser customUser = new CustomUser(
                loginDTO.getEmployeeCode(),
                employeeInfo,
                user
        );

        Authentication authentication = new UsernamePasswordAuthenticationToken(customUser, null, customUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
