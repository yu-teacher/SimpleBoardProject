package com.example.board.repository;

import com.example.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import javax.transaction.Transactional;

public interface BoardRepository extends JpaRepository<Board,Integer> {

    //조회수 증가
    @Transactional
    @Modifying
    @Query("update Board set viewCnt = viewCnt + 1 where boardNum = :boardNum")
    void updateViewCnt(@Param("boardNum") Integer boardNum);

    @Query("select b from Board b order by b.regDate DESC")
    Page<Board> pagingBoardList(Pageable pageable);

}