package com.example.board.boardTest;

import com.example.board.dto.BoardRequestDTO;
import com.example.board.dto.BoardResponseDTO;
import com.example.board.dto.BoardUpdateDTO;
import com.example.board.service.BoardService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

@SpringBootTest
@Log4j2
public class BoardTest {

    private final BoardService boardService;

    @Autowired
    public BoardTest(BoardService boardService) {
        this.boardService = boardService;
    }

    //등록 테스트
    @Test
    public void boardInsertTest(){
        for(int i = 0; i < 10; i++){
            boardService.boardCreate(
                    BoardRequestDTO.builder()
                            .title(i+"번째 글 제목")
                            .content(i+"번째 글 내용")
                            .build()
            );
        }
    }

    //수정 테스트
    @Test
    public void boardUpdateTest(){
        boardService.boardUpdate(
                BoardUpdateDTO.builder()
                        .boardNum(1)
                        .title("수정된 제목")
                        .content("수정된 내용")
                        .build()
        );
    }

    //삭제 테스트
    @Test
    public void boardDeleteTest(){
        boardService.boardDelete(2);
    }

    //조회 테스트
    @Test
    public void boardReadTest(){
        System.out.println(boardService.boardRead(10));
    }

    //목록 테스트
    @Test
    public void boardListTest(){
        Page<BoardResponseDTO> list = boardService.boardList(1);

        System.out.println(list.getTotalElements());
        System.out.println(list.getTotalPages());

        for(BoardResponseDTO dto : list){
            System.out.println(dto);
        }
    }

}