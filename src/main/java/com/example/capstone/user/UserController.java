package com.example.capstone.user;

import com.example.capstone.config.ResponseException;
import com.example.capstone.config.ResponseObj;
import com.example.capstone.config.annotation.NoAuth;
import com.example.capstone.job.model.entity.Job;
import com.example.capstone.user.model.entity.User;
import com.example.capstone.user.model.request.PostLoginReq;
import com.example.capstone.user.model.request.PostUserReq;
import com.example.capstone.user.model.response.GetApplyRes;
import com.example.capstone.user.model.response.PostLoginRes;
import com.example.capstone.user.model.response.PostUserRes;
import com.example.capstone.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.example.capstone.config.ResponseStatusCode.*;
import static com.example.capstone.utils.ValidationRegex.*;

@Controller
@RequestMapping("/app/users")
public class UserController {
    private PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtService jwtService;
    static final double median_income=1757194;

    @Autowired
    public UserController(PasswordEncoder passwordEncoder, UserService userService, JwtService jwtService) {
        this.passwordEncoder=passwordEncoder;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @NoAuth
    @ResponseBody
    @PostMapping()
    public ResponseObj<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) throws ResponseException {
        if(postUserReq.getUser_id()== null){
            return new ResponseObj<>(POST_USERS_EMPTY_EMAIL);
        }
        //이메일 정규표현
        if(!isRegexEmail(postUserReq.getUser_id())){
            return new ResponseObj<>(POST_USERS_INVALID_EMAIL);
        }

        int user_idx= userService.getLastId()+1;
        Integer percent=null;
        if(postUserReq.getUser_median_income()!=null){
            double income=postUserReq.getUser_median_income();
            percent= (int) ((income/median_income)*100);
        }
        User user= new User(user_idx,postUserReq.getUser_id(), postUserReq.getUser_pw(), postUserReq.getUser_name(),postUserReq.getUser_birth(),
                postUserReq.getUser_gender(),postUserReq.getUser_phone(), postUserReq.getUser_company_status(),postUserReq.getUser_experience(), postUserReq.getUser_drive_status(), postUserReq.getUser_siNm(),
                postUserReq.getUser_sggNm(), postUserReq.getUser_emdNm(), postUserReq.getUser_streetNm(), postUserReq.getUser_detailNm(), postUserReq.getUser_lat(), postUserReq.getUser_lng(),
                postUserReq.getUser_insurance_status(), percent, postUserReq.getUser_guardian_phone(), postUserReq.getUser_profile_img(), postUserReq.getUser_job_cate_list(),
                postUserReq.getUser_disease_list());
        System.out.println("user.getUser_guardian_phone() = " + user.getUser_guardian_phone());
        PostUserRes postUserRes=userService.createUser(user);

        return new ResponseObj<>(postUserRes);
    }

    @NoAuth
    @ResponseBody
    @PostMapping("/sign-in")
    public ResponseObj<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq) throws ResponseException{

        if (postLoginReq.getUser_id() == null) {
            return new ResponseObj<>(POST_USERS_EMPTY_EMAIL);
        }
        //이메일 정규표현
        if (!isRegexEmail(postLoginReq.getUser_id())) {
            return new ResponseObj<>(POST_USERS_INVALID_EMAIL);
        }
        String user_id= postLoginReq.getUser_id();
        String user_pw= postLoginReq.getUser_pw();
        User user=new User(user_id,user_pw);
        PostLoginRes postLoginRes = userService.logIn(user);
        return new ResponseObj<>(postLoginRes);
    }

    @NoAuth
    @ResponseBody
    @GetMapping("/{userIdx}")
    public ResponseObj<User> findUserInfoByUserIdx(@PathVariable("userIdx")int user_idx){
        User user=userService.findUserInfo(user_idx);
        return new ResponseObj<>(user);

    }

    @NoAuth
    @ResponseBody
    @GetMapping("")
    public ResponseObj<List<User>> findUserInfo(@RequestParam(required = false,name="user-id")String user_id){
        List<User> user_list=new ArrayList<>();
        if(user_id!=null){
            int user_idx=userService.findUserIdx(user_id);
            User user=userService.findUserInfo(user_idx);
            user_list.add(user);
            return new ResponseObj<>(user_list);

        }
        else{
            List<Integer>userIdxList=userService.findUserList();
            for(int user_idx:userIdxList){
                User user=userService.findUserInfo(user_idx);
                user_list.add(user);
            }
            return new ResponseObj<>(user_list);
        }
    }

    @ResponseBody
    @GetMapping("/{userNum}/applications")
    public ResponseObj<List<GetApplyRes>> findMyApplications(@PathVariable("userNum")int userIdx,
                                                     HttpServletRequest request){
        int loginIdx=(int) request.getAttribute("userIdx");
        if(loginIdx==userIdx){
       List<GetApplyRes>jobList= userService.findMyApplications(userIdx);
        return new ResponseObj<>(jobList);}
        else return new ResponseObj<>(INVALID_USER_JWT);
    }
}
