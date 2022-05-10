package com.example.capstone.user.model.response;

import com.example.capstone.user.model.message.SendSmsResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetValidRes {
    private String valid_str;
    private SendSmsResponse sendSmsResponse;
}
