package com.example.real_prj.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRestDto {
    private String email;
    private String name;
    private String imagePath;
    private LocalDateTime regDate;
}
