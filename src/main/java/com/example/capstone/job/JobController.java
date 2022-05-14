package com.example.capstone.job;


import com.example.capstone.config.ResponseException;
import com.example.capstone.config.ResponseObj;
import com.example.capstone.config.ResponseStatusCode;
import com.example.capstone.config.annotation.NoAuth;
import com.example.capstone.job.model.entity.Job;
import com.example.capstone.job.model.request.*;
import com.example.capstone.job.model.response.GetApplicationRes;
import com.example.capstone.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/app/jobs")
public class JobController {

    private PasswordEncoder passwordEncoder;
    private final JobService jobService;
    private final JwtService jwtService;

    @Autowired
    public JobController(PasswordEncoder passwordEncoder, JobService jobService, JwtService jwtService) {
        this.passwordEncoder=passwordEncoder;
        this.jobService = jobService;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @PostMapping("")
    public ResponseObj<String> createJob(HttpServletRequest request,@RequestBody PostJobReq postJobReq) throws ResponseException {

        int userIdx=(int)request.getAttribute("userIdx");
        int checkCompanyStatus=jobService.checkCompany(userIdx);

        if(checkCompanyStatus==1){
        int jobIdx=jobService.findLastIdx();

        Job newJob=new Job(jobIdx+1,postJobReq.getJobTitle(),postJobReq.getCompanyName(),postJobReq.getJobCategoryId(),postJobReq.getJobMinAge(),postJobReq.getJobMaxAge(),
                postJobReq.getJobGender(),postJobReq.getJobWage(),postJobReq.getJobWorkingTime(),postJobReq.getJobPersonnel(),postJobReq.getJobDetail(),postJobReq.getJobOfferStatus(),
                postJobReq.getJobSinm(),postJobReq.getJobSggnm(),postJobReq.getJobEmdnm(),postJobReq.getJobStreetnm(),postJobReq.getJobDetailnm(), postJobReq.getJobLat(), postJobReq.getJobLng(),
                userIdx);

        int jobCreatedStatus=jobService.createJob(newJob);

        if(jobCreatedStatus!=0) return new ResponseObj<>("");
        else return new ResponseObj(ResponseStatusCode.FAIL_TO_CREATE_JOB);}
        else return new ResponseObj<>(ResponseStatusCode.NOT_FOR_PERSONAL_USERS);

    }

    //Entity 클래스 Job을 GetJobRes로 바꾸도록 하기
    @NoAuth
    @ResponseBody
    @GetMapping("/{jobIdx}")
    public ResponseObj<Job> findJob(@PathVariable("jobIdx")int job_idx){
        Job newJob= jobService.findJobByIdx(job_idx);
        return new ResponseObj<>(newJob);
    }

    @ResponseBody
    @GetMapping("")
    public ResponseObj<List<Job>> findJobList(HttpServletRequest request,
                                              @RequestParam(value = "user",required = false) Integer userNum,
                                              @RequestParam(value="publisher",required=false)Integer publisherNum){

        if(userNum!=null){
            //유저 정보로 필터링한 공고
            System.out.println("user= " +userNum);
            List<Job> jobListFilteredByUser= jobService.getJobByUser(userNum);
            return new ResponseObj<>(jobListFilteredByUser);
        }


        if(publisherNum!=null){
            //기업에서 올린 직업공고 조회
        int userIdx=(int)request.getAttribute("userIdx");
        int checkCompanyStatus=jobService.checkCompany(userIdx);



                List<Job> jobListByPublisher= jobService.getJobByPublisher(publisherNum);
                return new ResponseObj<>(jobListByPublisher);

        }


        List<Job> jobList= jobService.getJobList();
        return new ResponseObj<>(jobList);


    }

    //유저가 제출할 지원서 정보 확인 API
    @ResponseBody
    @GetMapping("/{jobIdx}/application")
    public ResponseObj<GetApplicationRes> getApplication(HttpServletRequest request,
                                                         @PathVariable("jobIdx")int jobIdx){
        int userIdx=(int)request.getAttribute("userIdx");
        GetApplicationRes getApplicationRes = jobService.getResume(userIdx);

        return new ResponseObj<>(getApplicationRes);

    }

    //지원서 제출 API
    @ResponseBody
    @PostMapping("/{jobIdx}/application")
    public ResponseObj<String> apply(HttpServletRequest request,
                                         @PathVariable("jobIdx")int jobIdx){
        int userIdx=(int)request.getAttribute("userIdx");
        int applyStatus=jobService.checkApplyStatus(userIdx,jobIdx);
        if(applyStatus==0) { //이미 지원된 공고인지 확인
            jobService.apply(jobIdx, userIdx);
            return new ResponseObj<>("");
        }
        else return new ResponseObj<>(ResponseStatusCode.ALREADY_APPLIED);
    }

    //공고에 지원한 사람들 확인
    @ResponseBody
    @GetMapping("/{jobIdx}/applicants")
    public ResponseObj<List<GetApplicationRes>> getApplications(HttpServletRequest request,
                                                                @PathVariable("jobIdx")int jobIdx){
        int userIdx=(int)request.getAttribute("userIdx");
        int checkCompanyStatus=jobService.checkCompany(userIdx);
        int checkPublisherStatus=jobService.checkPublisherStatus(jobIdx);
        System.out.println("checkPublisherStatus = " + checkPublisherStatus);
        if(checkCompanyStatus==1) {//기업회원일 경우
            if(checkPublisherStatus==userIdx){
           List<GetApplicationRes> applicantList=jobService.getApplicants(jobIdx);
           return new ResponseObj<>(applicantList);
            }
            return new ResponseObj<>(ResponseStatusCode.INVALID_USER_JWT);
        }
        else return new ResponseObj<>(ResponseStatusCode.NOT_FOR_PERSONAL_USERS);
    }



//job_title, age,job_gender, job_wage, job_working_time, job_personnel, job_detail, job_offer_status, job_address

    @ResponseBody
    @PostMapping("/{jobIdx}/modify-auth")
    public ResponseObj<String> modifyAuth(@PathVariable("jobIdx") int jobIdx,HttpServletRequest request){
        int userIdx=(int)request.getAttribute("userIdx");
        if(userIdx==jobService.checkPublisherStatus(jobIdx)) return new ResponseObj<>(ResponseStatusCode.ABLE_TO_MODIFY);
        else return new ResponseObj(ResponseStatusCode.INVALID_USER_JWT);

    }

    @ResponseBody
    @PatchMapping("/{jobIdx}/title")
    public ResponseObj<String> modifyTitle(@PathVariable("jobIdx") int jobIdx,
                                           @RequestBody PatchTitleReq patchTitleReq){
        int check= jobService.modifyTitle(patchTitleReq);
       if(check==1) return new ResponseObj<>("");
       else return new ResponseObj(ResponseStatusCode.MODIFY_TITLE_FAIL);
    }

    @ResponseBody
    @PatchMapping("/{jobIdx}/age")
    public ResponseObj<String> modifyAge(@PathVariable("jobIdx") int jobIdx,
                                         @RequestBody PatchAgeReq patchAgeReq){
        int check= jobService.modifyAge(patchAgeReq);
        if(check==1) return new ResponseObj<>("");
        else return new ResponseObj(ResponseStatusCode.MODIFY_AGE_FAIL);


    }

    @ResponseBody
    @PatchMapping("/{jobIdx}/gender")
    public ResponseObj<String> modifyGender(@PathVariable("jobIdx") int jobIdx,
                                            @RequestBody PatchGenderReq patchGenderReq){
        int check= jobService.modifyGender(patchGenderReq);
        if(check==1) return new ResponseObj<>("");
        else return new ResponseObj(ResponseStatusCode.MODIFY_GENDER_FAIL);
    }


    @ResponseBody
    @PatchMapping("/{jobIdx}/wage")
    public ResponseObj<String> modifyWage(@PathVariable("jobIdx") int jobIdx,
                                          @RequestBody PatchWageReq patchWageReq){
        int check= jobService.modifyWage(patchWageReq);
        if(check==1) return new ResponseObj<>("");
        else return new ResponseObj(ResponseStatusCode.MODIFY_WAGE_FAIL);
    }

    @ResponseBody
    @PatchMapping("/{jobIdx}/time")
    public ResponseObj<String> modifyTime(@PathVariable("jobIdx") int jobIdx,
                                          @RequestBody PatchTimeReq patchTimeReq){
        int check= jobService.modifyTime(patchTimeReq);
        if(check==1) return new ResponseObj<>("");
        else return new ResponseObj(ResponseStatusCode.MODIFY_TIME_FAIL);}


    @ResponseBody
    @PatchMapping("/{jobIdx}/personnel")
    public ResponseObj<String> modifyPersonnel(@PathVariable("jobIdx") int jobIdx,
                                               @RequestBody PatchPersonnelReq patchPersonnelReq){
        int check= jobService.modifyPersonnel(patchPersonnelReq);
        if(check==1) return new ResponseObj<>("");
        else return new ResponseObj(ResponseStatusCode.MODIFY_PERSONNEL_FAIL);}

    @ResponseBody
    @PatchMapping("/{jobIdx}/detail")
    public ResponseObj<String> modifyDetail(@PathVariable("jobIdx") int jobIdx,
                                            @RequestBody PatchDetailReq patchDetailReq){
        int check= jobService.modifyDetail(patchDetailReq);
        if(check==1) return new ResponseObj<>("");
        else return new ResponseObj(ResponseStatusCode.MODIFY_DETAIL_FAIL);}

    @ResponseBody
    @PatchMapping("/{jobIdx}/offer")
    public ResponseObj<String> modifyOffer(@PathVariable("jobIdx") int jobIdx,
                                           @RequestBody PatchOfferReq patchOfferReq){
        int check= jobService.modifyOffer(patchOfferReq);
        if(check==1) return new ResponseObj<>("");
        else return new ResponseObj(ResponseStatusCode.MODIFY_OFFER_STATUS_FAIL);}


    @ResponseBody
    @PatchMapping("/{jobIdx}/address")
    public ResponseObj<String> modifyAddress(@PathVariable("jobIdx") int jobIdx,
                                             @RequestBody PatchAddressReq patchAddressReq){
        int check= jobService.modifyyAddress(patchAddressReq);
        if(check==1) return new ResponseObj<>("");
        else return new ResponseObj(ResponseStatusCode.MODIFY_ADDRESS_FAIL);}
    }





