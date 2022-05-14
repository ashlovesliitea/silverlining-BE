package com.example.capstone.job.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PatchAgeReq {
    private int job_idx;
    private Integer job_min_age;
    private Integer job_max_age;
}
