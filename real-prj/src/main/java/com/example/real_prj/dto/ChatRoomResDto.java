package com.example.real_prj.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Slf4j
public class ChatRoomResDto {
    private String roomId;
    private String name;
    private LocalDateTime regDate;

    @JsonIgnore
    private Set<WebSocketSession> sessions;
    public boolean isSessionEmpty(){
        return this.sessions.isEmpty();
    }

    @Builder
    public ChatRoomResDto(String roomId, String name, LocalDateTime regDate){
        this.roomId = roomId;
        this.name = name;
        this.regDate = regDate;
        this.sessions = Collections.newSetFromMap(new ConcurrentHashMap<>());
    }
}
