package com.example.board.commentTest;

import com.example.board.dto.CommentRequestDTO;
import com.example.board.dto.CommentUpdateDTO;
import com.example.board.service.CommentService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@Log4j2
public class CommentTest {
    private final CommentService commentService;

    @Autowired
    public CommentTest(CommentService commentService) {
        this.commentService = commentService;
    }

    @Test
    public void commentInsertTest(){
        for (int i = 0; i < 50; i++) {
            String content = "["+i+"] 번째 댓글 내용";
            commentService.commentCreate(
                    CommentRequestDTO
                    .builder()
                    .boardNum(3)
                    .content(content)
                    .build()
            );
        }
    }

    @Test
    public void commentReadTest(){
        System.out.println(commentService.commentList(
                CommentRequestDTO
                .builder()
                .boardNum(45)
                .build())
        );
    }

    @Test
    public void commentUpdateTest(){
        commentService.commentUpdate(
                CommentUpdateDTO
                        .builder()
                        .commentNum(2)
                        .content("변경된 콘텐츠")
                        .boardNum(132)
                        .build()
        );
    }

    @Test
    public void commentDeleteTest(){
        commentService.commentDelete(1);
    }
}
