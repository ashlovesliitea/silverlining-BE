package com.example.capstone.facility;

import com.example.capstone.facility.model.response.GetFacilityRes;
import com.example.capstone.user.UserService;
import com.example.capstone.user.model.entity.User;
import com.example.capstone.utils.JwtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class FacilityService {

    private final FacilityDao facilityDao;
    private final JwtService jwtService;
    private final RestTemplate restTemplate;
    private final UserService userService;

    @Value("${spring.kakao.rest-api-key}")
    private String REST_API_KEY;

    @Value("${spring.kakao.rest-api-url}")
    private String REST_API_URL;

    @Autowired
    public FacilityService(FacilityDao facilityDao, JwtService jwtService, RestTemplate restTemplate, UserService userService) {
        this.facilityDao = facilityDao;
        this.jwtService = jwtService;
        this.restTemplate = restTemplate;
        this.userService = userService;
    }

    public List<GetFacilityRes> searchHospitals(int userIdx) throws JsonProcessingException {
       User user=userService.findUserInfo(userIdx);
       String lng=user.getUser_lng().toString();
       String lat=user.getUser_lat().toString();

       List<String> keywordList=facilityDao.findKeywordList(userIdx);
       //환자가 가지고 있는 질병 키워드들
        System.out.println("keywordList = " + keywordList);
        List<GetFacilityRes> facilityResList=new ArrayList<>();

        return getGetFacilityRes(lng, lat,1000, keywordList, facilityResList);

    }

    public List<GetFacilityRes> searchWelfares(int userIdx){
        User user=userService.findUserInfo(userIdx);
        String lng=user.getUser_lng().toString();
        String lat=user.getUser_lat().toString();

        List<String> keywordList=new ArrayList<>();
        keywordList.add("노인주거복지시설");
        keywordList.add("노인여가복지시설");
        keywordList.add("재가노인복지시설");
        keywordList.add("노인보호전문기관");
        keywordList.add("노인일자리지원기관");
        System.out.println("keywordList = " + keywordList);

        List<GetFacilityRes> facilityResList=new ArrayList<>();

        return getGetFacilityRes(lng,lat,5000,keywordList,facilityResList);

    }

    private List<GetFacilityRes> getGetFacilityRes(String lng, String lat,
                                                   int radius,List<String> keywordList, List<GetFacilityRes> facilityResList) {
        for(String keyword: keywordList) {
            final HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", REST_API_KEY);

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(REST_API_URL)
                    .queryParam("x", lng)
                    .queryParam("y", lat)
                    .queryParam("radius", radius);

            final HttpEntity<String> entity = new HttpEntity<String>(headers);

            ParameterizedTypeReference<Map<String, Object>> typeRef = new ParameterizedTypeReference<Map<String, Object>>() {
            };

            String uri = builder.encode().toUriString() + "&query=" + keyword;

            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(uri, HttpMethod.GET, entity, typeRef);
            System.out.println(response.getBody().get("documents").getClass());
            List<LinkedHashMap<String, String>> facilityInfoList = (List<LinkedHashMap<String, String>>) response.getBody().get("documents");

            ObjectMapper objectMapper = new ObjectMapper();

            for (LinkedHashMap<String, String> facilityInfo : facilityInfoList) {

                GetFacilityRes getFacilityRes = new GetFacilityRes(
                        keyword,
                        facilityInfo.get("place_name"),
                        facilityInfo.get("phone"),
                        facilityInfo.get("road_address_name"),
                        Double.parseDouble(facilityInfo.get("x")),
                        Double.parseDouble(facilityInfo.get("y")),
                        facilityInfo.get("place_url"),
                        Float.parseFloat(facilityInfo.get("distance")) / 1000
                );

                facilityResList.add(getFacilityRes);

            }
        }
        return facilityResList;
    }
}
