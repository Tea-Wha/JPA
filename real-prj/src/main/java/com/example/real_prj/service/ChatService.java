package com.example.real_prj.service;



import com.example.real_prj.dto.ChatMessageDto;
import com.example.real_prj.dto.ChatRoomReqDto;
import com.example.real_prj.dto.ChatRoomResDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
    private final ObjectMapper objectMapper;
    private Map<String, ChatRoomResDto> chatRooms;

    @PostConstruct
    private void init(){
        chatRooms = new LinkedHashMap<>();
    }
    public List<ChatRoomResDto> findAllRoom(){
        return new ArrayList<>(chatRooms.values());
    }
    public ChatRoomResDto findRoomById(String roomId) {
        return chatRooms.get(roomId);
    }
    public ChatRoomResDto createRoom(String name){
        String randomId = UUID.randomUUID().toString();
        log.info("UUID : " + randomId);
        ChatRoomResDto chatRoom = ChatRoomResDto.builder()
                .roomId(randomId)
                .name(name)
                .regDate(LocalDateTime.now())
                .build();
        chatRooms.put(randomId, chatRoom);

        return chatRoom;
    }
    public void removeRoom(String roomId){
        ChatRoomResDto room = chatRooms.get(roomId);
        if (room != null){
            if (room.isSessionEmpty()){
                chatRooms.remove(roomId);
            }
        }
    }
    public void addSessionAndHandleEnter (String roomId, WebSocketSession session, ChatMessageDto chatMessage){
        ChatRoomResDto room = findRoomById(roomId);
        if (room != null){
            room.getSessions().add(session);
            if (chatMessage.getSender() != null){
                chatMessage.setMessage(chatMessage.getSender() +"님이 입장했습니다.");
                sendMessageToAll(roomId, chatMessage);
            }
            log.debug("New session added : " + session);
        }
    }
    public  void removeSessionAndHandleExit (String roomId, WebSocketSession session, ChatMessageDto chatMessage){
        ChatRoomResDto room = findRoomById(roomId);
        if (room != null) {
            room.getSessions().remove(session);
            if (chatMessage.getSender() != null){
                chatMessage.setMessage(chatMessage.getSender()+ "님이 퇴장했습니다.");
                sendMessageToAll(roomId, chatMessage);
            }
            log.debug("Session removed : " + session);
            if (room.isSessionEmpty()){
                removeRoom(roomId);
            }
        }
    }
    public void sendMessageToAll(String roomId, ChatMessageDto message){
        ChatRoomResDto room = findRoomById(roomId);
        if (room != null){
            for (WebSocketSession session : room.getSessions()){
                sendMessage(session, message);
            }
        }
    }
    public <T> void sendMessage(WebSocketSession session, T message){
        try{
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        }
        catch (IOException e){
            log.error(e.getMessage(), e);
        }
    }
}
