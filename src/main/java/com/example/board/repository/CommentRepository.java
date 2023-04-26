package com.example.board.repository;

import com.example.board.entity.Board;
import com.example.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Integer> {

    List<Comment> findByBoardFk(Board boardFk);
    boolean existsByCommentNum(Integer commentNum);
}
