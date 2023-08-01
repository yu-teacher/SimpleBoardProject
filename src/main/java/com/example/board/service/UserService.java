package com.example.board.service;

import com.example.board.dto.BoardResponseDTO;
import com.example.board.dto.UserInfoDTO;
import com.example.board.dto.UserPrivacyDTO;
import com.example.board.entity.BaseEntity;
import com.example.board.entity.Board;
import com.example.board.entity.UserEntity;
import com.example.board.repository.UserRepository;
import com.example.board.util.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService extends BaseEntity {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserInfoDTO userRegister(UserPrivacyDTO dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        dto.setRoles(Collections.singleton(Role.USER));
        return userRepository.save(dto.toEntity()).toDTO();
    }

    public UserInfoDTO userRead(Integer userNum) {
        return userRepository.findById(userNum).get().toDTO();
    }

    public Page<UserInfoDTO> userList(Integer pageNum){
        Pageable pageable = PageRequest.of(pageNum - 1, 10);
        Page<UserEntity> userPage = userRepository.pagingUserList(pageable);

        List<UserInfoDTO> userList = userPage
                .getContent()
                .stream()
                .map(user -> user.toDTO())
                .collect(Collectors.toList());

        return new PageImpl<>(userList, pageable, userPage.getTotalElements());
    }

    public UserInfoDTO userUpdate(UserPrivacyDTO dto) {
        userRepository.deleteById(dto.getUserNum());
        dto.setRoles(Collections.singleton(Role.USER));
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        return userRepository.save(dto.toEntity()).toDTO();
    }

    public void userDelete(Integer userNum){
        userRepository.deleteById(userNum);
    }
    public boolean idCheck(String userid) { return !userRepository.existsByUserId(userid); }
}
