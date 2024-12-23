package com.example.real_prj.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CommentResDto {
    private String email;
    private Long boardId;
    private Long commentId;
    private String content;
    private LocalDateTime regDate;
}
