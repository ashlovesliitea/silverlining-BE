package com.example.capstone.job.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Job {
    // 구인정보 인덱스
    @JsonProperty("job_idx")
    private Integer jobIdx;

    // 구인정보 제목
    @JsonProperty("job_title")
    private String jobTitle;

    // 회사 이름
    @JsonProperty("company_name")
    private String companyName;

    // 직종
    @JsonProperty("job_category_id")
    private Integer jobCategoryId;

    // 최소 연령
    @JsonProperty("job_min_age")
    private Integer jobMinAge;

    // 최대 연령
    @JsonProperty("job_max_age")
    private Integer jobMaxAge;

    // 성별
    @JsonProperty("job_gender")
    private Integer jobGender;

    // 급여
    @JsonProperty("job_wage")
    private String jobWage;

    // 근무시간
    @JsonProperty("job_working_time")
    private String jobWorkingTime;

    // 정원
    @JsonProperty("job_personnel")
    private Integer jobPersonnel;

    // 세부정보
    @JsonProperty("job_detail")
    private String jobDetail;

    // 구인 상태
    @JsonProperty("job_offer_status")
    private Integer jobOfferStatus;

    // 시도
    @JsonProperty("job_sinm")
    private String jobSinm;

    // 시군구
    @JsonProperty("job_sggnm")
    private String jobSggnm;

    // 읍면동
    @JsonProperty("job_emdnm")
    private String jobEmdnm;

    // 도로명
    @JsonProperty("job_streetnm")
    private String jobStreetnm;

    // 상세주소
    @JsonProperty("job_detailnm")
    private String jobDetailnm;

    // 위도
    @JsonProperty("job_lat")
    private Double jobLat;

    // 경도
    @JsonProperty("job_lng")
    private Double jobLng;

    @JsonProperty("job_publisher_idx")
    private Integer jobPublisherIdx;

    public Job(int job_idx,int job_gender,double job_lat,double job_lng){
        this.jobIdx=job_idx;
        this.jobGender=job_gender;
        this.jobLat=job_lat;
        this.jobLng=job_lng;
    }
}
