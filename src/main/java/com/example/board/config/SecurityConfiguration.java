package com.example.board.config;

import com.example.board.Jwt.JwtAuthenticationFilter;
import com.example.board.Jwt.JwtAuthorizationFilter;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserRepository userRepository;
    private final Environment env;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // CSRF 보안 설정 비활성화
        // 세션 생성 정책 설정 (무상태 세션)
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable() // 폼 로그인 설정 비활성화
                .httpBasic().disable() // HTTP 기본 인증 설정 비활성화
                // JwtAuthenticationFilter 필터 추가 (AuthenticationManager 필요)
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), env))
                // JwtAuthorizationFilter 필터 추가 (AuthenticationManager, UserRepository 필요)
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository, env))
                .authorizeRequests()
                // "/user/**" 패턴에 대한 요청은
                .antMatchers("/user/**")
                // 'ROLE_USER', 'ROLE_MANAGER', 'ROLE_ADMIN' 권한을 가진 사용자만 접근 가능
                .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                // 'ROLE_USER', 'ROLE_MANAGER', 'ROLE_ADMIN' 권한을 가진 사용자만 접근 가능
                // "/manager/**" 패턴에 대한 요청은
                .antMatchers("/manager/**")
                // 'ROLE_MANAGER', 'ROLE_ADMIN' 권한을 가진 사용자만 접근 가능
                .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                // "/admin/**" 패턴에 대한 요청은
                .antMatchers("/admin/**")
                // 'ROLE_ADMIN' 권한을 가진 사용자만 접근 가능
                .access("hasRole('ROLE_ADMIN')")
                // 나머지 요청에 대해서는 모두 접근 가능
                .anyRequest().permitAll();
    }

    @Bean // 패스워드 인코딩을 위한 의존성 설정
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
