package com.example.board.Jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.example.board.repository.UserRepository;
import com.example.board.util.Role;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.env.Environment;

@AllArgsConstructor
@Log4j2
public class JwtTokenReader {

    private Environment env;
    private UserRepository userRepository;
    private String token;

    public Integer getNumber()
            throws
            JWTDecodeException,
            SignatureVerificationException {
        return JWT
                .require(Algorithm.HMAC512(env.getProperty("jwt_secret")))
                .build()
                .verify(this.token.replace("Bearer ", ""))
                .getClaim("userNum") // 토큰의 "userNum" 클레임 값을 가져옵니다.
                .asInt(); // "userNum" 값을 숫자로 변환합니다.;
    }

    public Role getRole()
            throws
            JWTDecodeException,
            SignatureVerificationException {

        Integer userNum = this.getNumber();
        if (userNum != null)
            return Role.valueOf(userRepository.findById(userNum).get().getRoleList().get(0));

        return null;
    }


}
