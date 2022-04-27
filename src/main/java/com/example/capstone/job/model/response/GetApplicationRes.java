package com.example.capstone.job.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class GetApplicationRes {
    private String user_name;
    private Date user_birth;
    private Integer user_age;
    private String user_gender;
    private String user_phone;
    private String user_drive_status;
    private String user_exprience;
    private String user_profile_img;
    private List<String> user_job_cate_list;
}
