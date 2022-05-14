package com.example.capstone.job.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PatchAddressReq {
    private int job_idx;
    private String job_siNm;
    private double job_lat;
    private double job_lng;
}
