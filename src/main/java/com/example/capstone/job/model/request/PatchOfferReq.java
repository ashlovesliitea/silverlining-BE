package com.example.capstone.job.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PatchOfferReq {
    private int job_idx;
    private int job_offer_status;
}
