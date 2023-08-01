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
public class BoardUpdateDTO {

    private Integer boardNum;
    private String title;
    private String content;
    private Integer viewCnt;
    private Integer userNum;

    public Board toEntity(BoardUpdateDTO dto, UserEntity user) {
        return Board.builder()
                .boardNum(dto.getBoardNum())
                .title(dto.getTitle())
                .content(dto.getContent())
                .viewCnt(dto.getViewCnt())
                .user(user)
                .build();
    }
}
