package com.hmdandelion.project_1410002.auth.filter;

import com.hmdandelion.project_1410002.auth.dto.TokenDTO;
import com.hmdandelion.project_1410002.auth.service.AuthService;
import com.hmdandelion.project_1410002.auth.util.TokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String refreshToken = TokenUtils.getToken(request.getHeader("Refresh-Token"));

        if(refreshToken != null && TokenUtils.isValidToken(refreshToken)) {
            TokenDTO token = authService.checkRefreshTokenAndReIssueToken(refreshToken);
            response.setHeader("Access-Token", token.getAccessToken());
            response.setHeader("Refresh-Token", token.getRefreshToken());
            return;
        }

        String accessToken = TokenUtils.getToken(request.getHeader("Access-Token"));
        if(accessToken != null && TokenUtils.isValidToken(accessToken)) {
            String employeeNo = TokenUtils.getEmployeeNo(accessToken);
            authService.saveAuthentication(employeeNo);
        }

        filterChain.doFilter(request, response);
    }
}
