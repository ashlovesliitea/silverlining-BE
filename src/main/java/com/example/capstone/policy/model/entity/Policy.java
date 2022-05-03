package com.example.capstone.policy.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Policy {

    // 정책 id
    @JsonProperty("policy_idx")
    private Integer policyIdx;

    // 정책 이름
    @JsonProperty("policy_name")
    private String policyName;

    // 지원자 연령
    @JsonProperty("applicant_age")
    private Integer applicantAge;

    // 지원자 소득
    @JsonProperty("applicant_income")
    private Integer applicantIncome;

    // 지원 대상
    @JsonProperty("applicant_subject")
    private String applicantSubject;

    // 지원 주체
    @JsonProperty("policy_operation")
    private String policyOperation;

    // 지원 내용
    @JsonProperty("policy_support_detail")
    private String policySupportDetail;

    // 신청 방법
    @JsonProperty("policy_apply_method")
    private String policyApplyMethod;

    // 문의 전화번호
    @JsonProperty("poilcy_phone")
    private String poilcyPhone;

    public Policy(int idx,int age,int income) {
        this.policyIdx=idx;
        this.applicantAge=age;
        this.applicantIncome=income;
    }
}
