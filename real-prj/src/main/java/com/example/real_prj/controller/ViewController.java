package com.example.real_prj.controller;

import com.example.real_prj.dto.MemberReqDto;
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

//    @GetMapping("/all")
//    public ResponseEntity<List<MemberRestDto>> memberList(){
//        return ResponseEntity.ok(viewService.getMemberList());
//    }

    @PostMapping("/detail")
    public MemberRestDto memberDetail(@RequestBody String email){
        return viewService.getMemberDetail(email);
    }

//    @GetMapping("/{email}")
//    public ResponseEntity<MemberRestDto> memberDetail(@PathVariable String email){
//        return ResponseEntity.ok(viewService.getMemberDetail(email));
//    }

    @PostMapping("/delete")
    public boolean memberDelete(@RequestBody String email){
        return viewService.deleteMember(email);
    }

//    @DeleteMapping("/{email}")
//    public ResponseEntity<Boolean> memberDelete(@PathVariable String email){
//        return ResponseEntity.ok(viewService.deleteMember(email));
//    }

    @PostMapping("/modify")
    public Boolean memberModify(@RequestBody MemberReqDto memberReqDto){
        return viewService.modifyMember(memberReqDto);
    }

//    @PutMapping("/modify")
//    public ResponseEntity<Boolean> memberModify(@RequestBody MemberReqDto memberReqDto){
//        return ResponseEntity.ok(viewService.modifyMember(memberReqDto));
//    }


}
