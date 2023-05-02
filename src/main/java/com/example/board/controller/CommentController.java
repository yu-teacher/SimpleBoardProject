package com.example.board.controller;

import com.example.board.dto.CommentRequestDTO;
import com.example.board.dto.CommentResponseDTO;
import com.example.board.dto.CommentUpdateDTO;
import com.example.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<CommentResponseDTO> commentCreate(@RequestBody CommentRequestDTO dto){
        return new ResponseEntity<>(commentService.commentCreate(dto), HttpStatus.OK);
    }

    @GetMapping("/comment")
    public ResponseEntity<Page<CommentResponseDTO>> commentRead(@ModelAttribute CommentRequestDTO dto){
        return new ResponseEntity<>(commentService.commentList(dto), HttpStatus.OK);
    }

    @GetMapping("/comment/{commentNum}")
    public ResponseEntity<CommentResponseDTO> commentRead(@PathVariable Integer commentNum){
        return new ResponseEntity<>(commentService.commentRead(commentNum), HttpStatus.OK);
    }

    @PutMapping("/comment")
    public ResponseEntity<CommentResponseDTO> commentUpdate(@RequestBody CommentUpdateDTO dto){
        return new ResponseEntity<>(commentService.commentUpdate(dto), HttpStatus.OK);
    }

    @DeleteMapping("/comment/{commentNum}")
    public ResponseEntity<Boolean> commentDelete(@PathVariable Integer commentNum){
        return new ResponseEntity<>(commentService.commentDelete(commentNum), HttpStatus.NO_CONTENT);
    }

}
