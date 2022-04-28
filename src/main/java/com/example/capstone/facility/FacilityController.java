package com.example.capstone.facility;

import com.example.capstone.config.ResponseObj;
import com.example.capstone.config.ResponseStatusCode;
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
    public ResponseObj<List<GetFacilityRes>> searchFacilities(@RequestParam(value="user-idx",required = false)Integer userIdx,
                                                                @RequestParam(value="welfare",required = false)Integer welfare,
                                                                @RequestParam(value="hospital",required = false)Integer hospital) throws JsonProcessingException {

        if(hospital!=null){
            List<GetFacilityRes> getFacilityResList= facilityService.searchHospitals(userIdx);
            return new ResponseObj<>(getFacilityResList);}
        else if(welfare!=null){
            List<GetFacilityRes> getFacilityResList= facilityService.searchWelfares(userIdx);
            return new ResponseObj<>(getFacilityResList);
        }
        else return new ResponseObj<>(ResponseStatusCode.REQUEST_ERROR);

    }
}
