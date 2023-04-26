package com.example.board.dto;

import com.example.board.entity.Board;
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

    public Board toEntity(BoardRequestDTO dto){
        return Board.builder()
                    .title(dto.getTitle())
                    .content(dto.getContent())
                    .build();
    }
}
