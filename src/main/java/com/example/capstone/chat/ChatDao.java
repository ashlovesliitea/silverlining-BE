package com.example.capstone.chat;

import com.example.capstone.chat.model.Chatroom;
import com.example.capstone.chat.model.PostChatroomRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ChatDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Chatroom> getChatroomList(int userIdx) {
        System.out.println("userIdx = " + userIdx);
        String chatroomIdxListQuery="select distinct chatroom_idx from chatroom_member where user_idx=?";
        List<Integer> chatroomIdxList= this.jdbcTemplate.query(chatroomIdxListQuery,
                (rs,rowNum)-> rs.getInt(1),userIdx);

        String usernameQuery="select user_name from user where user_idx=?";
        String username= this.jdbcTemplate.queryForObject(usernameQuery,String.class,userIdx);

        List<Chatroom> chatroomList=new ArrayList<>();
        for(int chatroomIdx: chatroomIdxList){
            chatroomList.add(getChatroom(chatroomIdx,userIdx,username));
        }

        return chatroomList;
    }

    public Chatroom getChatroom(int chatroomIdx, int userIdx, String userName){
        String otherMemberQuery="SELECT  u.user_idx, u.user_name FROM chatroom_member cm " +
                "INNER JOIN user u on cm.user_idx= u.user_idx where chatroom_idx=? and cm.user_idx !=?";

        Object[] chatroomParams={chatroomIdx,userIdx};

        return this.jdbcTemplate.queryForObject(otherMemberQuery,
                (rs,rowNum)->{
            Integer other_idx=rs.getInt(1);
            String other_name=rs.getString(2);
                    System.out.println("other_name = " + other_name);
            return new Chatroom(
                        chatroomIdx,
                        userIdx,
                        userName,
                        other_idx,
                        other_name);}
                ,chatroomParams);


    }

    public int createChatroom(PostChatroomRes postChatroomRes) {
        String findChatroomIdxQuery="select max(chatroom_idx) from chatroom_idx";
        int lastChatroomIdx=this.jdbcTemplate.queryForObject(findChatroomIdxQuery,int.class);
        System.out.println("lastChatroomIdx = " + lastChatroomIdx);
        String newChatroomIdxQuery="insert into chatroom_idx(chatroom_idx) values(?)";
        this.jdbcTemplate.update(newChatroomIdxQuery,lastChatroomIdx+1);
        lastChatroomIdx=this.jdbcTemplate.queryForObject(findChatroomIdxQuery,int.class);
        System.out.println("newChatroomIdxQuery = " + newChatroomIdxQuery);

        ArrayList<Integer> memberList=new ArrayList<>();
        memberList.add(postChatroomRes.getMy_idx());
        memberList.add(postChatroomRes.getOther_idx());


        String lastChatroomMemberIdxQuery="select max(chatroom_member_idx) from chatroom_member";
        for(int member:memberList){
            int lastChatroomMemberIdx=this.jdbcTemplate.queryForObject(lastChatroomMemberIdxQuery,int.class);
            System.out.println("lastChatroomMemberIdx = " + lastChatroomMemberIdx);
            Object[] chatroomMemberParams={lastChatroomMemberIdx+1,lastChatroomIdx,member};

            String addChatroomMemberQuery="insert into chatroom_member(chatroom_member_idx, chatroom_idx,user_idx) values(?,?,?)";
            this.jdbcTemplate.update(addChatroomMemberQuery,chatroomMemberParams);
        }

        return lastChatroomIdx;

    }

    public boolean checkFriend(PostChatroomRes postChatroomRes) {
        int person1 = postChatroomRes.getMy_idx();
        String checkFriendQuery = "select chatroom_idx from chatroom_member where user_idx = ?";
        List<Integer> person1JoinedChatroomList= this.jdbcTemplate.query(checkFriendQuery,
                (rs,rowNum)->rs.getInt(1)
                ,person1);

        String checkIsFriendQuery="select exists(select * from chatroom_member where chatroom_idx=? and user_idx=?)";
        for(int chatroomIdx: person1JoinedChatroomList){
            Object[] checkFriendParams={chatroomIdx,postChatroomRes.getOther_idx()};
            int check= this.jdbcTemplate.queryForObject(checkIsFriendQuery,int.class,checkFriendParams);
            System.out.println("check = " + check);
            if(check==1) return true;
        }

        return false;

    }

    public int deleteChatroom(int chatroom_idx) {
        String deleteChatroomIdxQuery="delete from chatroom_idx where chatroom_idx=?";
        this.jdbcTemplate.update(deleteChatroomIdxQuery,chatroom_idx);
        String deleteChatroomMemberIdxQuery="delete from chatroom_member where chatroom_idx=?";

        return this.jdbcTemplate.update(deleteChatroomMemberIdxQuery,chatroom_idx);
    }
}