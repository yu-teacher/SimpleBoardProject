package com.example.board.dto;

import com.example.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.example.board.util.TimeChange.dateChangeString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponseDTO {

    private Integer boardNum;
    private String title;
    private String content;
    private Integer viewCnt;
    private String regDate;
    private String updateDate;

    public BoardResponseDTO toDTO(Board board){
        return BoardResponseDTO.builder()
                .boardNum(board.getBoardNum())
                .title(board.getTitle())
                .content(board.getContent())
                .viewCnt(board.getViewCnt())
                .regDate(dateChangeString(board.getRegDate()))
                .updateDate(dateChangeString(board.getUpdateDate()))
                .build();
    }
}
