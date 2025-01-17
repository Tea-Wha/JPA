package com.example.real_prj.entity;


import com.example.real_prj.constant.Authority;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity // 해당 클래스가 Entity 임을 나타냄
@Table(name="member") // Table 이름 지정, 테이블 이름은 소문자, 카멜 표기법은 -> 스네이크 표기법 변환됨
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ToString // Overriding
public class Member {

    @Id // 해당 필드를 기본키로 지정
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.AUTO) // 기본키 생성 전략, JPA가 자동으로 생선 전략을 정함
    private Long id; // Primary Key

    @Column(nullable = false) // NOT NULL
    private String email;
    @Column(nullable = false)
    private String pwd;
    @Column(length = 50)
    private String name;
    @Column(name="reg_date")
    private LocalDateTime regDate;
    @Column(name="image_path")
    private String imgPath;
//    @PrePersist // JPA의 콜백 메서드로 엔티티가 저장되기 전에 실행, DB 데이터가 삽입 되기전에 자동 설정
//    protected void onCreate() {
//        this.regDate = LocalDateTime.now();
//    }

    // 게시글 목록에 대한 OneToMany
    // Many to one 쪽에서 참조한 키가 mappedBy에 들어가야함
    // Member 쪽에서 여러 Board 를 받고 있음
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Board> boards = new ArrayList<>();

    // Member 쪽에서 작성한 Board 는 여러 댓글을 받고 있음
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentsboard = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentsmember = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Authority authority;
    
    @Builder // NoArgsConstructor가 있어야함
    public Member(String email, String name, String pwd, String img, Authority authority){
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.imgPath = img;
        this.authority = authority;
        this.regDate = LocalDateTime.now();
    }
}
