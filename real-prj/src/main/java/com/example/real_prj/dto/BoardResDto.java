package com.example.real_prj.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardResDto {
  private Long boardId;
  private String title;
  private String content;
  private String imgPath;
  private LocalDateTime regDate;
  private String email;

}
