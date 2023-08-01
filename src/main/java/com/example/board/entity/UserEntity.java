package com.example.board.entity;

import com.example.board.dto.UserInfoDTO;
import com.example.board.util.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userNum;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Board> boards;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER) // Enum 타입의 컬렉션으로 저장되며, 즉시 로딩됨
    @Enumerated(EnumType.STRING) // 그걸 문자열로 저장함
    private Set<Role> roles; // 사용자의 역할 정보를 나타내는 필드

    public List<String> getRoleList() {
        List<String> roleList = new ArrayList<>();
        for (Role role : roles) roleList.add(role.name());
        return roleList;
    }

    public UserInfoDTO toDTO(){
        return UserInfoDTO
                .builder()
                .userNum(userNum)
                .userId(userId)
                .username(username)
                .email(email)
                .roles(roles)
                .build();
    }

}
