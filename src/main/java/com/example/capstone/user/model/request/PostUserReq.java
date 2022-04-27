package com.example.capstone.user.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostUserReq {
    private String user_id;
    private String user_pw;
    private String user_name;
    private Date user_birth;
    private Integer user_gender;
    private String user_phone;
    private Integer user_company_status;
    private String user_experience;
    private Integer user_drive_status;
    private String user_siNm;
    private String user_sggNm;
    private String user_emdNm;
    private String user_streetNm;
    private String user_detailNm;
    private Double user_lat;
    private Double user_lng;
    private Integer user_insurance_status;
    private Integer user_median_income;
    private String user_guardian_phone;
    private String user_profile_img;
    private List<Integer> user_job_cate_list;
    private List<Integer> user_disease_list;

}
