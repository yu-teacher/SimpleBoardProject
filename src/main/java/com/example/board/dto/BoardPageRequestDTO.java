package com.example.board.dto;

import lombok.*;
import org.springframework.data.domain.Pageable;


@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class BoardPageRequestDTO implements Pageable {

    private final int pageNumber;
    private final int pageSize;
    private final Sort sort;

    public BoardPageRequestDTO(){
        this.pageNumber = 1;
        this.pageSize = 10;
    }



}
