package com.example.board.repository;

import com.example.board.entity.Board;
import com.example.board.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment,Integer> {

    boolean existsByCommentNum(Integer commentNum);

    @Query("select c from Comment c where boardFk = :boardFk order by c.regDate DESC")
    Page<Comment> pagingCommentList(Pageable pageable,@Param("boardFk") Board boardFk);
}
