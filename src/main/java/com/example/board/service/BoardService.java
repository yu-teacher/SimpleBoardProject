package com.example.board.service;

import com.example.board.dto.BoardRequestDTO;
import com.example.board.dto.BoardResponseDTO;
import com.example.board.dto.BoardUpdateDTO;
import com.example.board.entity.Board;
import com.example.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    //등록
    public BoardResponseDTO boardCreate(BoardRequestDTO dto){
        Board board = boardRepository.save(new BoardRequestDTO().toEntity(dto));
        return new BoardResponseDTO().toDTO(board);
    }

    //수정
    public BoardResponseDTO boardUpdate(BoardUpdateDTO dto){
        Board board = boardRepository.save(new BoardUpdateDTO().toEntity(dto));
        return new BoardResponseDTO().toDTO(board);
    }

    //삭제
    public void boardDelete(Integer boardNum){
        boardRepository.deleteById(boardNum);
    }

    //조회
    public BoardResponseDTO boardRead(Integer boardNum){
        Optional<Board> board = boardRepository.findById(boardNum);
        return new BoardResponseDTO().toDTO(board.get());
    }

    //목록
    public List<Board> boardList(){
        List<Board> list = boardRepository.findAll();
        return list;
    }
}
