package com.example.board.repository;

import com.example.board.entity.Board;
import com.example.board.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserRepository extends JpaRepository<UserEntity,Integer> {

    @Query("select u from UserEntity u order by u.userNum DESC")
    Page<UserEntity> pagingUserList(Pageable pageable);

    UserEntity findByUserId(String id);
    boolean existsByUserId(String userId);
}
