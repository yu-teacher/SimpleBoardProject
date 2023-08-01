package com.example.board.dto;

import com.example.board.entity.Board;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.CommentRepository;
import lombok.*;

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
    private Integer commentCnt;
    private String writer;
    private Integer userNum;
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
                .writer(board.getUser().getUsername())
                .userNum(board.getUser().getUserNum())
                .build();
    }

    public BoardResponseDTO toDTO(Board board,CommentRepository commentRepository){
        return BoardResponseDTO.builder()
                .boardNum(board.getBoardNum())
                .title(board.getTitle())
                .content(board.getContent())
                .viewCnt(board.getViewCnt())
                .commentCnt(commentRepository.countByBoardFk(board))
                .regDate(dateChangeString(board.getRegDate()))
                .updateDate(dateChangeString(board.getUpdateDate()))
                .writer(board.getUser().getUsername())
                .userNum(board.getUser().getUserNum())
                .build();
    }
}
