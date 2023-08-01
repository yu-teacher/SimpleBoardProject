package com.example.board.dto;

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
public class UserInfoDTO {

    private Integer userNum;
    private String userId;
    private String email;
    private String username;
    private Set<Role> roles;

}
