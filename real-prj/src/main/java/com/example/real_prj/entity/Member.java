package com.example.real_prj.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="member")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ToString
public class Member {

    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // Primary Key

    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String pwd;
    private String name;
    private LocalDateTime regDate;
    private String imgPath;
}
