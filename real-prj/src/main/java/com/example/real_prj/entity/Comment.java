package com.example.real_prj.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @ToString(exclude = {"board", "member"})
@NoArgsConstructor
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long commentId; // comment_id

    @ManyToOne
    @JoinColumn(name = "board_id") // Database // 참조키는 해당 객체의 기본키여야 함
    private Board board;
    // Many Comments -> One Board

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    // Many Comments -> One Member

    @Column(length = 1000)
    private String content;

    @Column(nullable = false, updatable = false)
    private LocalDateTime regDate;

    @PrePersist
    public void prePersist(){
        regDate = LocalDateTime.now();
    }

}
