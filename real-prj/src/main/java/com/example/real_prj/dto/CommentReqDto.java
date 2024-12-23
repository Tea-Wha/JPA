package com.example.real_prj.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CommentReqDto { // JWT 적용 시 email 정보는 빼고 token이 들어감
    private String email;
    private Long boardId;
    private String content;

}
