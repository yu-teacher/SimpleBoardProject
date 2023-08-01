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
public class CommentRequestDTO {

    private String content;
    private Integer boardNum;
    private Integer userNum;
    private Integer pageNum;

    public Comment toEntity(CommentRequestDTO dto, Board boardFk, UserEntity user){
        return Comment
                .builder()
                .content(dto.getContent())
                .boardFk(boardFk)
                .user(user)
                .build();
    }
}
