package com.example.board.dto;

import com.example.board.entity.Board;
import com.example.board.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequestDTO {

    private String title;
    private String content;
    private Integer userNum;

    public Board toEntity(BoardRequestDTO dto, UserEntity user){
        return Board.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .user(user)
                .build();
    }
}
