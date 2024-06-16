package com.hmdandelion.project_1410002.auth.config;

import com.hmdandelion.project_1410002.auth.filter.CustomAuthenticationFilter;
import com.hmdandelion.project_1410002.auth.filter.JwtAuthenticationFilter;
import com.hmdandelion.project_1410002.auth.handler.JwtAccessDeniedHandler;
import com.hmdandelion.project_1410002.auth.handler.JwtAuthenticationEntryPoint;
import com.hmdandelion.project_1410002.auth.handler.LoginFailureHandler;
import com.hmdandelion.project_1410002.auth.handler.LoginSuccessHandler;
import com.hmdandelion.project_1410002.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManage -> sessionManage.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    //예원
                    auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                    auth.requestMatchers(HttpMethod.POST, "/api/v1/login").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/api/v1/clients/**").authenticated();
                    auth.requestMatchers(HttpMethod.GET, "/api/v1/estimates/**").authenticated();
                    auth.requestMatchers(HttpMethod.GET, "/api/v1/orders/**").authenticated();
                    auth.requestMatchers(HttpMethod.GET, "/api/v1/returns/**").authenticated();
                    auth.requestMatchers("/api/v1/clients/**").hasAuthority("SALES");
                    auth.requestMatchers("/api/v1/estimates/**").hasAuthority("SALES");
                    auth.requestMatchers("/api/v1/orders/**").hasAuthority("SALES");
                    auth.requestMatchers("/api/v1/returns/**").hasAuthority("SALES");
                    auth.anyRequest().authenticated();
//                    auth.anyRequest().permitAll();
                    //동환
                    auth.requestMatchers(HttpMethod.GET, "/api/v1/product/**").authenticated();
                    auth.requestMatchers(HttpMethod.GET, "/api/v1/products/**").authenticated();
                    auth.requestMatchers(HttpMethod.GET, "/api/v1/productSpec/**").authenticated();
                    auth.requestMatchers(HttpMethod.GET, "/api/v1/bom/**").authenticated();
                    auth.requestMatchers(HttpMethod.GET, "/api/v1/material/**").authenticated();
                    auth.requestMatchers(HttpMethod.GET, "/api/v1/stock/**").authenticated();
                    auth.requestMatchers(HttpMethod.GET, "/api/v1/storage/**").authenticated();
                    auth.requestMatchers(HttpMethod.GET, "/api/v1/warehouse/**").authenticated();
                    auth.requestMatchers(HttpMethod.GET, "/api/v1/employee/**").authenticated();
                    auth.requestMatchers(HttpMethod.GET, "/api/v1/release/**").authenticated();
                    auth.requestMatchers("/api/v1/product/**").hasAuthority("PRODUCTION");
                    auth.requestMatchers("/api/v1/products/**").hasAuthority("PRODUCTION");
                    auth.requestMatchers("/api/v1/productSpec/**").hasAuthority("PRODUCTION");
                    auth.requestMatchers("/api/v1/bom/**").hasAuthority("PRODUCTION");
                    auth.requestMatchers("/api/v1/material/**").hasAuthority("PRODUCTION");
                    auth.requestMatchers("/api/v1/stock/**").hasAuthority("PRODUCTION");
                    auth.requestMatchers("/api/v1/storage/**").hasAuthority("PRODUCTION");
                    auth.requestMatchers("/api/v1/warehouse/**").hasAuthority("PRODUCTION");
                    auth.requestMatchers("/api/v1/release/**").hasAuthority("PRODUCTION");
                    //auth.anyRequest().authenticated();
                    auth.anyRequest().permitAll();
                })
                .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter(), BasicAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling -> {
                    exceptionHandling.accessDeniedHandler(jwtAccessDeniedHandler());
                    exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint());
                })
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000","https://project1-4-1000-2-front-ip1h.vercel.app"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "DELETE"));
        corsConfiguration.setAllowedHeaders(Arrays.asList(
                "Access-Control-Allow-Origin", "Access-Control-Allow-Headers",
                "Content-Type", "Authorization", "X-Requested-With", "Access-Token", "Refresh-Token"));

        corsConfiguration.setExposedHeaders(Arrays.asList("Access-Token", "Refresh-Token"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

    @Bean
    AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(authService);
        return new ProviderManager(provider);
    }

    /* 로그인 실패 핸들러 */
    @Bean
    LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }

    /* 로그인 성공 핸들러 */
    @Bean
    LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler(authService);
    }

    @Bean
    CustomAuthenticationFilter customAuthenticationFilter() {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter();

        customAuthenticationFilter.setAuthenticationManager(authenticationManager());
        customAuthenticationFilter.setAuthenticationFailureHandler(loginFailureHandler());
        customAuthenticationFilter.setAuthenticationSuccessHandler(loginSuccessHandler());

        return customAuthenticationFilter;
    }

    /* JWT Token 인증 필터 */
    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(authService);
    }

    /* 인증 실패 시 동작 핸들러 */
    @Bean
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint();
    }

    /* 인가 실패 시 동작 핸들러 */
    @Bean
    JwtAccessDeniedHandler jwtAccessDeniedHandler() {
        return new JwtAccessDeniedHandler();
    }
}
