package com.example.board.Jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.example.board.repository.UserRepository;
import com.example.board.util.PrincipalDetails;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 시큐리티가 filter를 가지고 있는데 그중에 BasicAuthenticationFilter 있음
// 권한이나 인증이 필요한 특정 주소를 탈 때 위 필터를 타게 되어있음
// 아니면 안탐

@Log4j2
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserRepository userRepository;
    private final Environment env;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  UserRepository userRepository,
                                  Environment env) {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.env = env;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        // HTTP 요청 헤더에서 "Authorization" 헤더의 값을 가져옵니다.
        String jwtHeader = request.getHeader("Authorization");

        // "Authorization" 헤더가 없거나 "Bearer "로 시작하지 않으면 다음 필터로 이동합니다.
        if (jwtHeader == null || !jwtHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // "Authorization" 헤더의 값을 가져와 "Bearer " 부분을 제거하여 JWT 토큰을 추출합니다.
        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");

        Integer userNum = null;

        try {
            userNum = JWT
                    .require(Algorithm.HMAC512(env.getProperty("jwt_secret")))
                    .build()
                    .verify(jwtToken)
                    .getClaim("userNum") // 토큰의 "userId" 클레임 값을 가져옵니다.
                    .asInt(); // "userId" 값을 문자열로 변환합니다.

        }catch (JWTDecodeException | SignatureVerificationException e) {
            chain.doFilter(request, response);
        }

        // JWT 토큰의 서명 알고리즘과 비밀 키를 설정하여 토큰을 검증합니다.

        // 서명이 유효한 경우
        if (userNum != null) {
            // 사용자 정보를 기반으로 PrincipalDetails 객체를 생성합니다.
            PrincipalDetails principalDetails = new PrincipalDetails(userRepository.findById(userNum).get());

            // 서명이 유효하면 Authentication 객체를 생성하여 SecurityContextHolder에 저장합니다.
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(principalDetails,
                            null,
                            principalDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
