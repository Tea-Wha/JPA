package com.example.real_prj.controller;

import com.example.real_prj.dto.MemberRestDto;
import com.example.real_prj.entity.Member;
import com.example.real_prj.service.ViewService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/view")
@AllArgsConstructor
public class ViewController {
    private final ViewService viewService;

    @GetMapping("/all")
    public List<MemberRestDto> memberList(){
        return viewService.getMemberList();
    }


}
