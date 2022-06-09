package com.example.capstone.chat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class GetPhoneRes {
    private Integer user_idx;
    private String user_name;
}
