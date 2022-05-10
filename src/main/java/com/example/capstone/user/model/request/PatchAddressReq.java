package com.example.capstone.user.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class PatchAddressReq {
    private String siNm;
    private double user_lat;
    private double user_lng;
}
