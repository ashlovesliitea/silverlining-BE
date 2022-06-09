package com.example.capstone.chat;

import com.example.capstone.chat.model.Chatroom;
import com.example.capstone.chat.model.PostChatroomRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class ChatService {
   @Autowired
    public ChatService(ChatDao chatDao) {
        this.chatDao = chatDao;
    }

    private ChatDao chatDao;


    public List<Chatroom> getChatroomList(int userIdx) {
        return chatDao.getChatroomList(userIdx);
    }

    @Transactional
    public int createChatroom(PostChatroomRes postChatroomRes) {
        return chatDao.createChatroom(postChatroomRes);
    }

    public boolean checkFriend(PostChatroomRes postChatroomRes) {
        return chatDao.checkFriend(postChatroomRes);
    }

    public int deleteChatroom(int chatroom_idx) {
        return chatDao.deleteChatroom(chatroom_idx);
    }
}
