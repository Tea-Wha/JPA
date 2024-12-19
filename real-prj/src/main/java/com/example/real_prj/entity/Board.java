package com.example.real_prj.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="board")
@Getter @Setter @ToString
@NoArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "board_id")
    private Long id;

    @Column(nullable = false)
    private String title; // 글 제목

    @Lob
    @Column(length = 1000)
    private String content; // 글 내용

    private String imgPath; // 게시글 이미지 경로
    private LocalDateTime regDate; // 게시글 등록 일자

    @PrePersist
    public void prePersist(){
        regDate = LocalDateTime.now();
    }
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
