package com.example.board.service;

import com.example.board.dto.BoardRequestDTO;
import com.example.board.dto.BoardResponseDTO;
import com.example.board.dto.BoardUpdateDTO;
import com.example.board.entity.Board;
import com.example.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    //등록
    public BoardResponseDTO boardCreate(BoardRequestDTO dto){
        Board board = boardRepository.save(new BoardRequestDTO().toEntity(dto));
        return new BoardResponseDTO().toDTO(board);
    }

    //조회
    public BoardResponseDTO boardRead(Integer boardNum){
        Optional<Board> board = boardRepository.findById(boardNum);
        return new BoardResponseDTO().toDTO(board.get());
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

    //목록&페이징
    public Page<BoardResponseDTO> boardList(Integer pageNum){
        Pageable pageable = PageRequest.of(pageNum - 1, 5);
        Page<Board> boardPage = boardRepository.pagingBoardList(pageable);

        List<BoardResponseDTO> boardDTOList = boardPage
                .getContent()
                .stream()
                .map(board -> new BoardResponseDTO().toDTO(board))
                .collect(Collectors.toList());

        return new PageImpl<>(boardDTOList, pageable, boardPage.getTotalElements());
    }
    public void viewCntPlus(Integer boardNum){
        boardRepository.updateViewCnt(boardNum);
    }

}
