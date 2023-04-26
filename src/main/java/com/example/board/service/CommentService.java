package com.example.board.service;

import com.example.board.dto.CommentRequestDTO;
import com.example.board.dto.CommentResponseDTO;
import com.example.board.dto.CommentUpdateDTO;
import com.example.board.entity.Board;
import com.example.board.entity.Comment;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public CommentResponseDTO commentCreate(CommentRequestDTO dto){
        Optional<Board> board = boardRepository.findById(dto.getBoardNum());
        Comment comment = commentRepository.save(new CommentRequestDTO().toEntity(dto,board.get()));
        return new CommentResponseDTO().toDTO(comment);
    }

    public List<CommentResponseDTO> commentRead(CommentRequestDTO dto){
        Optional<Board> board = boardRepository.findById(dto.getBoardNum());
        List<Comment> comments = commentRepository.findByBoardFk(board.get());
        return comments
                .stream()
                .map(comment -> new CommentResponseDTO().toDTO(comment))
                .collect(Collectors.toList());
    }

    public CommentResponseDTO commentUpdate(CommentUpdateDTO dto){
        Optional<Board> board = boardRepository.findById(dto.getBoardNum());
        log.info(commentRepository.save(new CommentUpdateDTO().toEntity(dto,board.get())));
        return new CommentResponseDTO().toDTO(commentRepository.findById(dto.getCommentNum()).get());
    }

    public Boolean commentDelete(Integer commentNum){
        commentRepository.deleteById(commentNum);
        return !commentRepository.existsByCommentNum(commentNum);
    }
}
