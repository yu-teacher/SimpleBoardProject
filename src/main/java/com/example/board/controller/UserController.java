package com.example.board.controller;

import com.example.board.Jwt.JwtTokenReader;
import com.example.board.dto.UserInfoDTO;
import com.example.board.dto.UserPrivacyDTO;
import com.example.board.repository.UserRepository;
import com.example.board.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Log4j2
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final Environment env;

    @GetMapping("/member")
    public ResponseEntity<?> userRead(
            @RequestParam(name = "userNum", required = false, defaultValue = "") Integer userNum,
            @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum){
        return (userNum != null)?
                new ResponseEntity<>(userService.userRead(userNum), HttpStatus.OK):
                new ResponseEntity<>(userService.userList(pageNum), HttpStatus.OK);
    }

    @PostMapping("/member")
    public ResponseEntity<UserInfoDTO> userCreate(@RequestBody UserPrivacyDTO dto){
        return new ResponseEntity<>(userService.userRegister(dto), HttpStatus.OK);
    }

    @PutMapping("/member")
    public ResponseEntity<UserInfoDTO> userUpdate(@RequestBody UserPrivacyDTO dto){
        return new ResponseEntity<>(userService.userUpdate(dto), HttpStatus.OK);
    }

    @DeleteMapping("/member/{userNum}")
    public ResponseEntity<Integer> userDelete(@PathVariable Integer userNum){
        userService.userDelete(userNum);
        return new ResponseEntity<>(userNum,HttpStatus.NO_CONTENT);
    }

    @GetMapping("/id/duplication/{userId}") // 아이디 중복 체크
    public ResponseEntity<Boolean> idCheck(@PathVariable String userId) {
        return new ResponseEntity<>(userService.idCheck(userId), HttpStatus.OK);
    }

    @GetMapping("/auth/check")
    public @ResponseBody String getAuthState(@RequestHeader("Authorization") String authorizationHeader) {
        //Todo : 토큰에 문제 있으면 알아서 Exception 나온다 지금은 토큰에 문제가 있으면 500에러가 나온다. 이를 바꿔주는 코드 추가
        //Todo : RoelDto 추가 ?
        log.info(authorizationHeader);
        if(authorizationHeader.equals("")) return null;
        else if(authorizationHeader == null) return null;
        else return "{ \"userNum\" : \""+new JwtTokenReader(env,userRepository,authorizationHeader).getNumber()+"\"}";
    }

}
