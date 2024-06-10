package com.hmdandelion.project_1410002.auth.handler;

import com.hmdandelion.project_1410002.auth.service.AuthService;
import com.hmdandelion.project_1410002.auth.util.TokenUtils;
import com.hmdandelion.project_1410002.employee.dto.EmployeeInfoDTO;
import com.hmdandelion.project_1410002.employee.service.EmployeeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final AuthService authService;
    private final EmployeeService employeeService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Map<String, Object> memberInfo = getMemberInfo(authentication);
        log.info("로그인 성공 후 인증 객체에서 꺼낸 정보 : {}", memberInfo);

        String accessToken = TokenUtils.createAccessToken(memberInfo);
        String refreshToken = TokenUtils.createRefreshToken();
        log.info("발급된 accessToken : {}", accessToken);
        log.info("발급된 refreshToken : {}", refreshToken);

        authService.updateRefreshToken((String)memberInfo.get("employeeNo"), refreshToken);

        response.setHeader("Access-Token", accessToken);
        response.setHeader("Refresh-Token", refreshToken);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private Map<String, Object> getMemberInfo(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        EmployeeInfoDTO employeeInfo = employeeService.getInfoByEmployeeNo(userDetails.getUsername());

        String authorities = userDetails.getAuthorities()
                .stream()
                .map(auth -> auth.getAuthority().toString()).collect(Collectors.joining());

        return Map.of(
                "employeeNo", userDetails.getUsername(),
                "authorities", authorities,
                "employeeName", employeeInfo.getEmployeeName(),
                "email", employeeInfo.getEmail(),
                "departmentName", employeeInfo.getDepartmentName(),
                "positionName", employeeInfo.getPositionName()
        );
    }
}
