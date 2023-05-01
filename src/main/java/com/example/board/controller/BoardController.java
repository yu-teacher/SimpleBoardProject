package com.example.board.controller;

import com.example.board.dto.BoardRequestDTO;
import com.example.board.dto.BoardResponseDTO;
import com.example.board.dto.BoardUpdateDTO;
import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/board")
    public ResponseEntity<BoardResponseDTO> register(@RequestBody BoardRequestDTO dto){
        return new ResponseEntity<>(boardService.boardCreate(dto), HttpStatus.OK);
    }

    @GetMapping("/board/{boardNum}")
    public ResponseEntity<BoardResponseDTO> read(@PathVariable Integer boardNum){
        return new ResponseEntity<>(boardService.boardRead(boardNum), HttpStatus.OK);
    }

    @PutMapping("/board")
    public ResponseEntity<BoardResponseDTO> modify(@RequestBody BoardUpdateDTO dto){
        return new ResponseEntity<>(boardService.boardUpdate(dto), HttpStatus.OK);
    }

    @DeleteMapping("/board/{boardNum}")
    public void remove(@PathVariable Integer boardNum){
        boardService.boardDelete(boardNum);
    }

    @GetMapping("/board")
    public List<BoardResponseDTO> list(){
        return boardService.boardList();
    }
}
