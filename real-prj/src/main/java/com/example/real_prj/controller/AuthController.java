package com.example.real_prj.controller;

import com.example.real_prj.dto.LoginReqDto;
import com.example.real_prj.dto.MemberReqDto;
import com.example.real_prj.dto.MemberResDto;
import com.example.real_prj.dto.TokenDto;
import com.example.real_prj.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/auth") // 진입 경로
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService; // 의존성 주입
    
    // 회원 가입 여부 확인
    @GetMapping("/exists/{email}")
    public ResponseEntity<Boolean> isMember(@PathVariable String email){
        boolean isTrue = authService.isMember(email);
        return ResponseEntity.ok(!isTrue); // 존재하면 false (회원 가입 하면 안됨)
    }
    // 회원 가입
    @PostMapping("/signup")
    public ResponseEntity<MemberResDto> signUp(@RequestBody MemberReqDto memberReqDto){
        MemberResDto isSuccess = authService.signUp(memberReqDto);
        return ResponseEntity.ok(isSuccess);
    }
    // 로그인
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberReqDto memberReqDto){
        TokenDto isSuccess = authService.login(memberReqDto);
        return ResponseEntity.ok(isSuccess);
    }

}
