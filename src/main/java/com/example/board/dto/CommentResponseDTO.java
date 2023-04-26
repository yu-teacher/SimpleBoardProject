package com.example.board.dto;

import com.example.board.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.example.board.util.TimeChange.dateChangeString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDTO {

    private Integer commentNum;
    private String content;
    private String regDate;
    private String updateDate;
    private Integer boardNum;

    public CommentResponseDTO toDTO(Comment comment){
        return CommentResponseDTO
                .builder()
                .commentNum(comment.getCommentNum())
                .content(comment.getContent())
                .regDate(dateChangeString(comment.getRegDate()))
                .updateDate(dateChangeString(comment.getUpdateDate()))
                .boardNum(comment.getBoardFk().getBoardNum())
                .build();
    }


}
