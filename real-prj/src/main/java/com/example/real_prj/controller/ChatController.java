package com.example.real_prj.controller;

import com.example.real_prj.dto.ChatRoomReqDto;
import com.example.real_prj.dto.ChatRoomResDto;
import com.example.real_prj.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;
    @PostMapping("/new") // 채팅방 개설
    public ResponseEntity<String> createRoom(@RequestBody ChatRoomReqDto chatRoomDto){
        log.warn("chatRoomDto : {}", chatRoomDto);
        ChatRoomResDto room = chatService.createRoom(chatRoomDto.getName());
        System.out.println(room.getRoomId());
        return new ResponseEntity<>(room.getRoomId(), HttpStatus.OK);
    }
    @GetMapping("/list")
    public List<ChatRoomResDto> findAllRoom(){
        return chatService.findAllRoom();
    }

    @GetMapping("/room/{roomId}")
    public ChatRoomResDto findRoomById(@PathVariable String roomId){
        return chatService.findRoomById(roomId);
    }
}
