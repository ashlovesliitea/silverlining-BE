package com.example.capstone.facility;

import com.example.capstone.config.ResponseObj;
import com.example.capstone.facility.model.response.GetFacilityRes;
import com.example.capstone.utils.JwtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/facilities")
public class FacilityController {
    private final FacilityService facilityService;
    private final JwtService jwtService;

    @Autowired
    public FacilityController(FacilityService facilityService, JwtService jwtService) {
        this.facilityService = facilityService;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @GetMapping("")
    public ResponseObj<List<GetFacilityRes>> searchFacilities(@RequestParam(value="user-num",required = false)int userIdx) throws JsonProcessingException {

        List<GetFacilityRes> getFacilityResList= facilityService.searchFacilities(userIdx);
        return new ResponseObj<>(getFacilityResList);

    }
}
