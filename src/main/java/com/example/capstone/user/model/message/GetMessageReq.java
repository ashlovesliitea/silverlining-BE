package com.example.capstone.user.model.message;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class GetMessageReq {
    private int user_idx;
    private String content;
}
