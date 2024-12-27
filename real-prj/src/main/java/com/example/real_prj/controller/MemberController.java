package com.example.real_prj.controller;

import com.example.real_prj.dto.MemberReqDto;
import com.example.real_prj.dto.MemberResDto;
import com.example.real_prj.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 4. MemberController : 회원 전체 조회, 개별 회원 조회, 회원 정보 수정, 회원 삭제
@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    // 전체회원 조회
    @GetMapping("/list")
    public ResponseEntity<List<MemberResDto>> memberList() {
        List<MemberResDto> list = memberService.getMemberList();
        return ResponseEntity.ok(list);
    }
    // 회원 상세 조회
    @GetMapping("/{email}")
    public ResponseEntity<MemberResDto> memberDetail(@PathVariable String email) {
        MemberResDto memberResDto = memberService.getMemberDetail(email);
        return ResponseEntity.ok(memberResDto);
    }
    // 회원 수정
    @PutMapping("/modify")
    public ResponseEntity<Boolean> memberModify(@RequestBody MemberReqDto memberReqDto) {
        boolean isSuccess = memberService.modifyMember(memberReqDto);
        return ResponseEntity.ok(isSuccess);
    }
    // 회원 삭제
    @DeleteMapping("/{email}")
    public ResponseEntity<Boolean> memberDelete(@PathVariable String email) {
        boolean isSuccess = memberService.deleteMember(email);
        return ResponseEntity.ok(isSuccess);
    }
}
