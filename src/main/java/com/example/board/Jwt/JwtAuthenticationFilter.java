package com.example.board.Jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.board.entity.UserEntity;
import com.example.board.util.PrincipalDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

// 스프링 시큐리티에 UsernamePasswordAuthenticationFilter 존재
// /login 요청시 username, password 전송하면 (POST)
// UsernamePasswordAuthenticationFilter이게 작동함
@RequiredArgsConstructor
@Log4j2
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final Environment env;

    // /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        log.info("jwt 로그인 시도중");

        // 1. username, password 받아오기
        ObjectMapper objectMapper = new ObjectMapper();
        UserEntity userEntity = null;
        try {
            userEntity = objectMapper.readValue(request.getInputStream(), UserEntity.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 2. 인증 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userEntity.getUserId(), userEntity.getPassword());

        // 3. 인증 시도
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // 4. 인증 완료 후 처리
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        log.info("로그인 완료: " + principalDetails.getUserEntity().getUserId());

        return authentication;
    }

    // 위 메소드 실행 후 인증이 되면 이 함수 실행
    // JWT 토큰을 만들어서 리퀘 요청한 사용자에게 JWT토큰을 발행하면 됨
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException {

        log.info("인증이 완료 됨");

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        String jwtToken = JWT.create()
                .withSubject(principalDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 86400000)) // 1일 (24시간) 설정
                .withClaim("userNum", principalDetails.getUserEntity().getUserNum())
                .withClaim("userRole", principalDetails.getUserEntity().getRoleList())
                .sign(Algorithm.HMAC512(env.getProperty("jwt_secret")));

        response.addHeader("Authorization", "Bearer " + jwtToken);

        // JSON 형식의 문자열을 생성
        String jsonResponse = String.format("{\"Authorization\": \"Bearer %s\"}", jwtToken);

        // HTTP Response Body에 추가
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }

}
