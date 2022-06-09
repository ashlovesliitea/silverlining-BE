package com.example.capstone.user.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class PatchDetailAddressReq {
    private int user_idx;
    private String user_detailNm;
}
