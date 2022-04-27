package com.example.capstone.facility.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class GetFacilityRes {
    private String department;
    private String place_name;
    private String phone;
    private String road_address_name;
    private double lng;
    private double lat;
    private String place_url;
    private float distance;
}
