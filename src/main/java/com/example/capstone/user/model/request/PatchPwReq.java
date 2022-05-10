package com.example.capstone.user.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PatchPwReq {
    private String old_pw;
    private String new_pw;
    private String new_pw_check;
}
