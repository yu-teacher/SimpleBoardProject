package com.example.board.dto;

import com.example.board.entity.Board;
import com.example.board.entity.Comment;
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

    public Comment toEntity(CommentRequestDTO dto, Board boardFk){
        return Comment
                .builder()
                .content(dto.getContent())
                .boardFk(boardFk)
                .build();
    }
}
