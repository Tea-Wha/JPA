package com.example.real_prj.controller;


import com.example.real_prj.dto.CommentReqDto;
import com.example.real_prj.dto.CommentResDto;
import com.example.real_prj.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/comment")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;
    // 댓글 등록
    @PostMapping("/write")
    public boolean commentWrite (@RequestBody CommentReqDto commentReqDto){
        return commentService.commentWrite(commentReqDto);
    }
    // 댓글 삭제
    @PostMapping("/delete")
    public boolean commentDelete (@RequestParam Long id, @RequestParam String email){
        return commentService.commentDelete(id, email);
    }

    // 댓글 목록 조회 (게시글 ID)
    @PostMapping("/viewall")
    public List<CommentResDto> commentViewAll (@RequestParam Long id){
        return commentService.commentViewAll(id);
    }
}

