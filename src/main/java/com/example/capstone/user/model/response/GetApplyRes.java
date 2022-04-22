package com.example.capstone.user.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class GetApplyRes {
    private int job_apply_idx; //지원번호
    private int user_idx;
    private int job_idx;
    private String job_title;
}
