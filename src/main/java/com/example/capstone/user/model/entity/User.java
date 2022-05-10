package com.example.capstone.user.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class User {
    //유저 비번, 유저 직무 경험, 유저 운전 여부, 유저 주소+ 유저 위경도, 유저 보험 여부, 유저 중위 소득, 유저 프로필 이미지, 유저 질병 여부, 관심 분야
    private int user_idx;
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

    public User(String user_id, String user_pw) {
        this.user_id=user_id;
        this.user_pw=user_pw;
    }

    public User(Date user_birth,int user_gender,double user_lat,double user_lng){
        this.user_birth=user_birth;
        this.user_gender=user_gender;
        this.user_lat=user_lat;
        this.user_lng=user_lng;
    }

    public User(String user_name,Date user_birth,Integer user_gender,String user_phone,Integer user_drive_status,
                String user_exprience,String user_profile_img) {
        this.user_name = user_name;
        this.user_birth=user_birth;
        this.user_gender=user_gender;
        this.user_phone=user_phone;
        this.user_drive_status=user_drive_status;
        this.user_experience=user_exprience;
        this.user_profile_img=user_profile_img;

    }
}
