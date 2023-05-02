package com.example.board.repository;

import com.example.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Integer> {

    //조회수 증가
    @Transactional
    @Modifying
    @Query("update Board set viewCnt = viewCnt + 1 where boardNum = :boardNum")
    void updateViewCnt(Integer boardNum);

    @Query("select b from Board b order by b.regDate DESC limit 5 offset :pageNum * 5")
    List<Board> pagingBoardList(Integer pageNum);
}