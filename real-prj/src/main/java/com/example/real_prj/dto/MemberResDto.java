package com.example.real_prj.dto;


import com.example.real_prj.entity.Member;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResDto {
    private String email;
    private String name;
    private String imagePath;
    private LocalDateTime regDate;

    private List<BoardResDto> boards;

    public static MemberResDto of(Member member) {
        return MemberResDto.builder()
                .name(member.getName())
                .email(member.getEmail())
                .imagePath(member.getImgPath())
                .regDate(member.getRegDate())
                .build();
    }
}
