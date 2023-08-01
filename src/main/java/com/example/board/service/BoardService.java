package com.example.board.service;

import com.example.board.dto.BoardRequestDTO;
import com.example.board.dto.BoardResponseDTO;
import com.example.board.dto.BoardUpdateDTO;
import com.example.board.entity.Board;
import com.example.board.entity.UserEntity;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.CommentRepository;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    //등록
    public BoardResponseDTO boardCreate(BoardRequestDTO dto){
        log.info(dto.toString());
        UserEntity user = userRepository.findById(dto.getUserNum()).get();
        Board board = boardRepository.save(new BoardRequestDTO().toEntity(dto, user));
        return new BoardResponseDTO().toDTO(board);
    }

    //조회
    public BoardResponseDTO boardRead(Integer boardNum){
        Board board = boardRepository.findById(boardNum).get();
        return new BoardResponseDTO().toDTO(board);
    }

    //수정
    public BoardResponseDTO boardUpdate(BoardUpdateDTO dto){
        UserEntity user = userRepository.findById(dto.getUserNum()).get();
        Board board = boardRepository.save(new BoardUpdateDTO().toEntity(dto,user));
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
                .map(board -> new BoardResponseDTO().toDTO(board,commentRepository))
                .collect(Collectors.toList());

        return new PageImpl<>(boardDTOList, pageable, boardPage.getTotalElements());
    }
    public void viewCntPlus(Integer boardNum){
        boardRepository.updateViewCnt(boardNum);
    }

}
