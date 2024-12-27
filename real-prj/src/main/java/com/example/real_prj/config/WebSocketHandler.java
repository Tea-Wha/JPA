package com.example.real_prj.config;


import com.example.real_prj.dto.ChatMessageDto;
import com.example.real_prj.service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Slf4j
@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final ChatService chatService;
    private final Map<WebSocketSession, String> sessionRoomIdMap = new ConcurrentHashMap<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
        String payload = message.getPayload();
        log.warn("{}",payload);
        ChatMessageDto chatMessage = objectMapper.readValue(payload, ChatMessageDto.class);
        String roomId = chatMessage.getRoomId();

        if (chatMessage.getType() == ChatMessageDto.MessageType.ENTER){
            sessionRoomIdMap.put(session, chatMessage.getRoomId());
            chatService.addSessionAndHandleEnter(roomId, session, chatMessage);
        }
        else if (chatMessage.getType() == ChatMessageDto.MessageType.CLOSE){
            chatService.removeSessionAndHandleExit(roomId, session, chatMessage);
        }
        else{
            chatService.sendMessageToAll(roomId, chatMessage);
        }
    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
        log.info("afterConnectionClosed : {}", session);
        String roomId = sessionRoomIdMap.remove(session);
        if (roomId != null){
            ChatMessageDto chatMessage = new ChatMessageDto();
            chatMessage.setType(ChatMessageDto.MessageType.CLOSE);
            chatService.removeSessionAndHandleExit(roomId, session, chatMessage);
        }
    }
}
