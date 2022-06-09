package com.example.capstone.chat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Chatroom {
    private Integer chatroom_idx;
    private Integer my_idx;
    private String my_name;
    private Integer other_idx;
    private String other_name;

    public Chatroom(Integer chatroomIdx, Integer otherIdx, String otherName){
        this.chatroom_idx=chatroomIdx;
        this.other_idx= otherIdx;
        this.other_name=otherName;

    }
}
