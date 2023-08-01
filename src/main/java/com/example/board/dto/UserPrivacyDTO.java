package com.example.board.dto;

import com.example.board.entity.UserEntity;
import com.example.board.util.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPrivacyDTO {

    private Integer userNum;
    private String userId;
    private String password;
    private String email;
    private String username;
    private Set<Role> roles;

    public UserEntity toEntity(){
        return UserEntity
                .builder()
                .userNum(userNum)
                .userId(userId)
                .username(username)
                .password(password)
                .email(email)
                .roles(roles)
                .build();
    }

}
