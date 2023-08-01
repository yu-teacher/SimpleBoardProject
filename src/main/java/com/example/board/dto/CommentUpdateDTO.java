package com.example.board.dto;

import com.example.board.entity.Board;
import com.example.board.entity.Comment;
import com.example.board.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpdateDTO {

    private Integer commentNum;
    private String content;
    private Integer boardNum;
    private Integer userNum;

    public Comment toEntity(CommentUpdateDTO dto, Board boardFk, UserEntity user){
        return Comment
                .builder()
                .commentNum(dto.getCommentNum())
                .content(dto.getContent())
                .boardFk(boardFk)
                .user(user)
                .build();
    }

}
