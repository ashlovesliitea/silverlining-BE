package com.example.capstone.user;

import com.example.capstone.config.ResponseException;
import com.example.capstone.config.ResponseObj;
import com.example.capstone.config.annotation.NoAuth;
import com.example.capstone.user.model.entity.User;
import com.example.capstone.user.model.message.SendSmsResponse;
import com.example.capstone.user.model.request.*;
import com.example.capstone.user.model.response.*;
import com.example.capstone.utils.JwtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.capstone.config.ResponseStatusCode.*;
import static com.example.capstone.utils.ValidationRegex.*;

@Controller
@RequestMapping("/app/users")
public class UserController {
    private PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final SmsService smsService;
    private final JwtService jwtService;
    static final double median_income = 1944812;

    @Autowired
    public UserController(PasswordEncoder passwordEncoder, UserService userService, SmsService smsService, JwtService jwtService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.smsService = smsService;
        this.jwtService = jwtService;
    }

    @NoAuth
    @ResponseBody
    @PostMapping()
    public ResponseObj<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) throws ResponseException {
        if (postUserReq.getUser_id() == null) {
            return new ResponseObj<>(POST_USERS_EMPTY_EMAIL);
        }
        //이메일 정규표현
        if (!isRegexEmail(postUserReq.getUser_id())) {
            return new ResponseObj<>(POST_USERS_INVALID_EMAIL);
        }

        int user_idx = userService.getLastId() + 1;
        Integer percent = null;
        if (postUserReq.getUser_median_income() != null) {
            percent = getMedianIncome(postUserReq.getUser_median_income());
        }
        User user = new User(user_idx, postUserReq.getUser_id(), postUserReq.getUser_pw(), postUserReq.getUser_name(), postUserReq.getUser_birth(),
                postUserReq.getUser_gender(), postUserReq.getUser_phone(), postUserReq.getUser_company_status(), postUserReq.getUser_experience(), postUserReq.getUser_drive_status(), postUserReq.getUser_siNm(),
                postUserReq.getUser_sggNm(), postUserReq.getUser_emdNm(), postUserReq.getUser_streetNm(), postUserReq.getUser_detailNm(), postUserReq.getUser_lat(), postUserReq.getUser_lng(),
                postUserReq.getUser_insurance_status(), percent, postUserReq.getUser_guardian_phone(), postUserReq.getUser_profile_img(), postUserReq.getUser_job_cate_list(),
                postUserReq.getUser_disease_list());
        System.out.println("user.getUser_guardian_phone() = " + user.getUser_guardian_phone());
        PostUserRes postUserRes = userService.createUser(user);

        return new ResponseObj<>(postUserRes);
    }


    @NoAuth
    @ResponseBody
    @GetMapping("/phone-validation")
    public ResponseObj<GetValidRes> phoneValidate(@RequestParam(value = "recipient") String recipientNum) throws UnsupportedEncodingException, ParseException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException {

        if (userService.checkUserPhone(recipientNum) == 0) {
            Random rand = new Random();
            String numStr = "";
            for (int i = 0; i < 4; i++) {
                String ran = Integer.toString(rand.nextInt(10)); //인증번호 네자리 생성
                numStr += ran;
            }
            String content = "인증번호 [" + numStr + "] 를 입력해주세요";

            SendSmsResponse sendSmsResponse = smsService.sendSms(recipientNum, content);
            GetValidRes getValidRes = new GetValidRes(numStr, sendSmsResponse);
            return new ResponseObj<>(getValidRes);
        }
        return new ResponseObj(ALREADY_JOINED_PHONE);
    }


    @NoAuth
    @ResponseBody
    @PostMapping("/sign-in")
    public ResponseObj<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq) throws ResponseException {

        if (postLoginReq.getUser_id() == null) {
            return new ResponseObj<>(POST_USERS_EMPTY_EMAIL);
        }
        //이메일 정규표현
        if (!isRegexEmail(postLoginReq.getUser_id())) {
            return new ResponseObj<>(POST_USERS_INVALID_EMAIL);
        }
        String user_id = postLoginReq.getUser_id();
        String user_pw = postLoginReq.getUser_pw();
        User user = new User(user_id, user_pw);
        PostLoginRes postLoginRes = userService.logIn(user);
        return new ResponseObj<>(postLoginRes);
    }

    @NoAuth
    @ResponseBody
    @GetMapping("/{userIdx}")
    public ResponseObj<User> findUserInfoByUserIdx(@PathVariable("userIdx") int user_idx) {
        User user = userService.findUserInfo(user_idx);
        return new ResponseObj<>(user);

    }


    @NoAuth
    @ResponseBody
    @GetMapping("")
    public ResponseObj<List<User>> findUserInfo(@RequestParam(required = false, name = "user-id") String user_id) {
        List<User> user_list = new ArrayList<>();
        if (user_id != null) {
            int user_idx = userService.findUserIdx(user_id);
            User user = userService.findUserInfo(user_idx);
            user_list.add(user);
            return new ResponseObj<>(user_list);

        }
        else {
            List<Integer> userIdxList = userService.findUserList();
            for (int user_idx : userIdxList) {
                User user = userService.findUserInfo(user_idx);
                user_list.add(user);
            }
            return new ResponseObj<>(user_list);
        }
    }

    @ResponseBody
    @GetMapping("/{userNum}/applications")
    public ResponseObj<List<GetApplyRes>> findMyApplications(@PathVariable("userNum") int userIdx,
                                                             HttpServletRequest request) {
        int loginIdx = (int) request.getAttribute("userIdx");
        if (loginIdx == userIdx) {
            List<GetApplyRes> jobList = userService.findMyApplications(userIdx);
            return new ResponseObj<>(jobList);
        } else return new ResponseObj<>(INVALID_USER_JWT);
    }
    //유저 비번, 유저 직무 경험, 유저 운전 여부, 유저 주소+ 유저 위경도, 유저 보험 여부, 유저 중위 소득, 유저 프로필 이미지, 유저 질병 여부, 관심 분야


    @ResponseBody
    @PatchMapping("/{userNum}/password")
    public ResponseObj<String> modifyPassword(@PathVariable("userNum") int userIdx,
                                              @RequestBody PatchPwReq patchPwReq) {
        if (userService.checkPw(userIdx, patchPwReq.getOld_pw())) {
            if (patchPwReq.getNew_pw().equals(patchPwReq.getNew_pw_check())) {
                int modifyCheck = userService.modifyPassword(userIdx, patchPwReq.getNew_pw());
                if (modifyCheck != 0) return new ResponseObj<>("");
                else return new ResponseObj(MODIFY_PW_FAIL);
            } else return new ResponseObj(NEW_PW_CHECK_FAIL);
        } else return new ResponseObj(OLD_PW_DOESNT_MATCH);
    }

    @ResponseBody
    @PatchMapping("/{userNum}/diseases")
    public ResponseObj<String> modifyDisease(@PathVariable("userNum")int userIdx,
                                             @RequestBody PatchDiseaseReq patchDiseaseReq){
        userService.modifyDisease(userIdx,patchDiseaseReq.getUser_disease_list());
        return new ResponseObj<>("");
    }

    @ResponseBody
    @PatchMapping("/{userNum}/address")
    public ResponseObj<String> modifyAddress(@PathVariable("userNum")int userIdx,
                                             @RequestBody PatchAddressReq patchAddressReq){
        int modifyCheck= userService.modifyAddress(userIdx,patchAddressReq);
        if(modifyCheck!=0) return new ResponseObj<>("");
        else return new ResponseObj(MODIFY_ADDRESS_FAIL);
    }

    //보험여부, 운전면허 여부, 직무 경험(user_experience), 상세 주소(user_detailNm) 수정 기능이 필요합니다

    @ResponseBody
    @PatchMapping("/{userNum}/insurance")
    public ResponseObj<String> modifyInsurance(@PathVariable("userNum")int userIdx,
                                             @RequestBody PatchInsuranceReq patchInsuranceReq) {
      if(userIdx==patchInsuranceReq.getUser_idx()){
          int modifyCheck= userService.modifyInsurance(patchInsuranceReq);
        if(modifyCheck!=0) return new ResponseObj<>("");
        else return new ResponseObj<>(MODIFY_INSURANCE_FAIL);
      }else{
          return new ResponseObj<>(REQUEST_ERROR);
      }
    }
    @ResponseBody
    @PatchMapping("/{userNum}/drive-status")
        public ResponseObj<String> modifyDrive(@PathVariable("userNum")int userIdx,
        @RequestBody PatchDriveReq patchDriveReq){

        if(userIdx==patchDriveReq.getUser_idx()){
            int modifyCheck= userService.modifyDrive(patchDriveReq);
            if(modifyCheck!=0) return new ResponseObj<>("");
            else return new ResponseObj<>(MODIFY_DRIVE_FAIL);
        }else{
            return new ResponseObj<>(REQUEST_ERROR);
        }
    }


    @ResponseBody
    @PatchMapping("/{userNum}/experience")
        public ResponseObj<String> modifyExperience(@PathVariable("userNum")int userIdx,
        @RequestBody PatchExpReq patchExpReq){

        if(userIdx==patchExpReq.getUser_idx()){
            int modifyCheck= userService.modifyExperience(patchExpReq);
            if(modifyCheck!=0) return new ResponseObj<>("");
            else return new ResponseObj<>(MODIFY_EXP_FAIL);
        }else{
            return new ResponseObj<>(REQUEST_ERROR);
        }
    }

    @ResponseBody
    @PatchMapping("/{userNum}/detail-address")
        public ResponseObj<String> modifyDetailAddress(@PathVariable("userNum")int userIdx,
        @RequestBody PatchDetailAddressReq patchDetailAddressReq){

        if(userIdx==patchDetailAddressReq.getUser_idx()){
            int modifyCheck= userService.modifyDetailAddress(patchDetailAddressReq);
            if(modifyCheck!=0) return new ResponseObj<>("");
            else return new ResponseObj<>(MODIFY_EXP_FAIL);
        }else{
            return new ResponseObj<>(REQUEST_ERROR);
        }
        }
    @ResponseBody
    @PatchMapping("/{userNum}/guardian")
        public ResponseObj<String> modifyGuardian(@PathVariable("userNum")int userIdx,
        @RequestBody PatchGuardianReq patchGuardianReq){
        if(userIdx==patchGuardianReq.getUser_idx()){
            int modifyCheck= userService.modifyGuardian(patchGuardianReq);
            if(modifyCheck!=0) return new ResponseObj<>("");
            else return new ResponseObj<>(MODIFY_GUARDIAN_FAIL);
        }else{
            return new ResponseObj<>(REQUEST_ERROR);
        }
    }


    private int getMedianIncome(double income) {
        int percent = (int) Math.round((income / median_income) * 100);
        System.out.println("percent = " + percent);
        return percent;
    }

    @ResponseBody
    @PatchMapping("/{userNum}/income")
    public ResponseObj<String> modifyIncome(@PathVariable("userNum")int userIdx,
                                              @RequestBody PatchIncomeReq patchIncomeReq){
        if(userIdx==patchIncomeReq.getUser_idx()){

            patchIncomeReq.setUser_income(getMedianIncome(patchIncomeReq.getUser_income()));

            int modifyCheck= userService.modifyIncome(patchIncomeReq);
            if(modifyCheck!=0) return new ResponseObj<>("");
            else return new ResponseObj<>(MODIFY_INCOME_FAIL);
        }else{
            return new ResponseObj<>(REQUEST_ERROR);
        }
    }



}
