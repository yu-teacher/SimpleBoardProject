package com.example.board.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Board extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer boardNum;

    @Column(nullable = false)
    private String title;


    @Column(nullable = false, length = 3000)
    private String content;

    private Integer viewCnt;

    @OneToMany(mappedBy = "boardFk")
    private List<Comment> comments;

    // 생성될 때 실행되는 어노테이션이다.
    @PrePersist
    private void defaultViewCnt(){
        this.viewCnt = this.viewCnt == null ? 0:this.viewCnt;
    }

    @Override
    public String toString(){
        return String.format(
                "글번호 : %d, 제목 : %s, 내용 : %s, 조회수 : %d",
                this.boardNum, this.title, this.content, this.viewCnt
        );
    }
}