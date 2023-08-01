package com.example.board.util;

import com.example.board.entity.UserEntity;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
@Data
@Log4j2
public class PrincipalDetails implements UserDetails {

    // PrincipalDetails 클래스의 필드로 User 객체를 가집니다. final로 선언되어 변경이 불가능하며, 생성자를 통해 초기화되어야 합니다.
    private final UserEntity userEntity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 권한 정보를 담는 Collection 객체를 생성합니다.
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        // User 객체의 RoleList를 반복하면서 권한 정보를 GrantedAuthority 타입으로 변환하여 authorities에 추가합니다.
        userEntity.getRoleList().forEach(r -> authorities.add(() -> r));

        // 생성된 authorities를 반환합니다.
        return authorities;
    }
    @Override
    public String getPassword() {
        return userEntity.getPassword();
    } // User 객체의 비밀번호를 반환합니다.

    @Override
    public String getUsername() {
        return userEntity.getUsername(); // User 객체의 사용자 이름을 반환합니다.
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정의 만료 여부를 항상 true로 반환합니다.
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정의 잠금 여부를 항상 true로 반환합니다.
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 사용자의 인증 정보의 만료 여부를 항상 true로 반환합니다.
    }

    @Override
    public boolean isEnabled() {
        return true; // 사용자의 활성화 여부를 항상 true로 반환합니다.
    }
}
