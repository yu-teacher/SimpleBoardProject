package com.example.board.service;

import com.example.board.dto.CommentRequestDTO;
import com.example.board.dto.CommentResponseDTO;
import com.example.board.dto.CommentUpdateDTO;
import com.example.board.entity.Board;
import com.example.board.entity.Comment;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public CommentResponseDTO commentCreate(CommentRequestDTO dto){
        Board board = boardRepository.findById(dto.getBoardNum()).get();
        UserEntity user = userRepository.findById(dto.getUserNum()).get();
        Comment comment = commentRepository.save(new CommentRequestDTO().toEntity(dto, board, user));
        return new CommentResponseDTO().toDTO(comment);
    }

    public CommentResponseDTO commentRead(Integer commentNum){
        return new CommentResponseDTO().toDTO(commentRepository.findById(commentNum).get());
    }

    public CommentResponseDTO commentUpdate(CommentUpdateDTO dto){
        Board board = boardRepository.findById(dto.getBoardNum()).get();
        UserEntity user = userRepository.findById(dto.getUserNum()).get();
        Comment comment = commentRepository.save(new CommentUpdateDTO().toEntity(dto,board,user));
        return new CommentResponseDTO().toDTO(comment);
    }

    public Boolean commentDelete(Integer commentNum){
        commentRepository.deleteById(commentNum);
        return !commentRepository.existsByCommentNum(commentNum);
    }

    //목록&페이징
    public Page<CommentResponseDTO> commentList(CommentRequestDTO dto){
        Optional<Board> board = boardRepository.findById(dto.getBoardNum());
        Pageable pageable = PageRequest.of(dto.getPageNum() - 1, 5);

        if(commentRepository.findByBoardFk(board.get()).isEmpty()) return null;

        Page<Comment> commentPage = commentRepository.pagingCommentList(pageable, board.get());

        List<CommentResponseDTO> commentDTOList = commentPage
                .getContent()
                .stream()
                .map(comment -> new CommentResponseDTO().toDTO(comment))
                .collect(Collectors.toList());

        return new PageImpl<>(commentDTOList, pageable, commentPage.getTotalElements());
    }
}
