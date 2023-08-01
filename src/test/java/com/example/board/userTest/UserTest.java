package com.example.board.userTest;

import com.example.board.dto.UserPrivacyDTO;
import com.example.board.service.UserService;
import com.example.board.util.Role;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Log4j2
public class UserTest {

    @Autowired
    private UserService userService;

    @Test
    public void userCreate(){
        Set<Role> role = Collections.singleton(Role.USER);
        for (int i = 1; i <= 50; i++) {
            UserPrivacyDTO user = UserPrivacyDTO
                    .builder()
                    .roles(role)
                    .email("User"+i+"@gmail.com")
                    .userId("user"+i)
                    .password("1234")
                    .username("시 공 조 앗! "+i)
                    .build();
            userService.userRegister(user);
        }
    }
}
