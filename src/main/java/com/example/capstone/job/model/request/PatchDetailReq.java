package com.example.capstone.job.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PatchDetailReq {
    private int job_idx;
    private String job_detail;
}
