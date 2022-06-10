package com.example.capstone.chat;

import com.example.capstone.chat.model.Chatroom;
import com.example.capstone.chat.model.PostChatroomRes;
import com.example.capstone.config.ResponseObj;
import com.example.capstone.config.ResponseStatusCode;
import com.example.capstone.config.annotation.NoAuth;
import com.example.capstone.user.UserService;
import com.example.capstone.chat.model.GetPhoneRes;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.example.capstone.config.ResponseStatusCode.*;

@RestController
@AllArgsConstructor
@RequestMapping("/app/chats")
public class ChatController {
    private ChatService chatService;
    private UserService userService;


    @GetMapping("/chatrooms")
    @ResponseBody
    public ResponseObj<List<Chatroom>> getChatroomList(HttpServletRequest request,
                                                       @RequestParam(name="user-idx",required=true)int user_idx) {
        int userIdx = (int) request.getAttribute("userIdx");
        if(user_idx==userIdx){
        List<Chatroom> chatroomList = chatService.getChatroomList(userIdx);
        return new ResponseObj<>(chatroomList);}
        else return new ResponseObj(INVALID_USER_JWT);
    }


    @PostMapping("/chatrooms")
    @ResponseBody
    public ResponseObj<String> createChatroom(@RequestBody PostChatroomRes postChatroomRes){
        int my_idx= postChatroomRes.getMy_idx();
        int other_idx=postChatroomRes.getOther_idx();

        if(userService.getUserByIdx(my_idx)==1&&userService.getUserByIdx(other_idx)==1){
        boolean checkFriends=chatService.checkFriend(postChatroomRes);
        if(checkFriends==false){
            int chatroomIdx=chatService.createChatroom(postChatroomRes);
            System.out.println("chatroomIdx = " + chatroomIdx);
            return new ResponseObj<>("");}
        else return new ResponseObj(ResponseStatusCode.ALREADY_FRIEND);}
        else return new ResponseObj(INVALID_USER_IDX);
    }

    @DeleteMapping("/chatrooms/{chatroomIdx}")
    @ResponseBody
    public ResponseObj<String> deleteChatroom(@PathVariable("chatroomIdx")int chatroom_idx){
        int deleteStatus= chatService.deleteChatroom(chatroom_idx);
        return new ResponseObj<>("");
    }

    @ResponseBody
    @GetMapping("/users")
    public ResponseObj<GetPhoneRes> findUserInfoByPhone(@RequestParam(required=false, name="user-phone")String user_phone) {
        if(userService.checkUserPhone(user_phone)!=0){
            GetPhoneRes getPhoneRes=userService.findByUserPhone(user_phone);
            return new ResponseObj<>(getPhoneRes);
        }
        else return new ResponseObj(NO_USER_JOINED_BY_THIS_PHONE_NUM);
    }


}
