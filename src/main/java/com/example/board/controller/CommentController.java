package com.example.board.controller;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    @PostMapping("/comment")
    public ResponseEntity<?> commentCreate(){
        throw new NotImplementedException("구현이 되지 않은 서비스입니다.");
    }

    @GetMapping("/comment")
    public ResponseEntity<?> commentRead(){
        throw new NotImplementedException("구현이 되지 않은 서비스입니다.");
    }

    @PutMapping("/comment")
    public ResponseEntity<?> commentUpdate(){
        throw new NotImplementedException("구현이 되지 않은 서비스입니다.");
    }

    @DeleteMapping("/comment")
    public ResponseEntity<?> commentDelete(){
        throw new NotImplementedException("구현이 되지 않은 서비스입니다.");
    }
}
