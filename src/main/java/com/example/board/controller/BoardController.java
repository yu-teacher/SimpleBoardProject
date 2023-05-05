package com.example.board.controller;

import com.example.board.dto.BoardRequestDTO;
import com.example.board.dto.BoardResponseDTO;
import com.example.board.dto.BoardUpdateDTO;
import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    //등록
    @PostMapping("/board")
    public ResponseEntity<BoardResponseDTO> register(@RequestBody BoardRequestDTO dto){
        return new ResponseEntity<>(boardService.boardCreate(dto), HttpStatus.OK);
    }

    //조회
    @GetMapping("/board/{boardNum}")
    public ResponseEntity<BoardResponseDTO> read(@PathVariable Integer boardNum){
        return new ResponseEntity<>(boardService.boardRead(boardNum), HttpStatus.OK);
    }

    //수정
    @PutMapping("/board")
    public ResponseEntity<BoardResponseDTO> modify(@RequestBody BoardUpdateDTO dto){
        return new ResponseEntity<>(boardService.boardUpdate(dto), HttpStatus.OK);
    }

    @PutMapping("/board/view/{boardNum}")
    public void viewCnt(@PathVariable Integer boardNum){
        boardService.viewCntPlus(boardNum);
    }

    //삭제
    @DeleteMapping("/board/{boardNum}")
    public void remove(@PathVariable Integer boardNum){
        boardService.boardDelete(boardNum);
    }

    //목록&페이징
    @GetMapping("/board/list/{pageNum}")
    public Page<BoardResponseDTO> list(@PathVariable Integer pageNum){
        return boardService.boardList(pageNum);
    }

}
