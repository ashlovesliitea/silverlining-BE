package com.example.capstone.job;


import com.example.capstone.config.ResponseException;
import com.example.capstone.config.ResponseObj;
import com.example.capstone.config.ResponseStatusCode;
import com.example.capstone.config.annotation.NoAuth;
import com.example.capstone.job.model.entity.Job;
import com.example.capstone.job.model.entity.Application;
import com.example.capstone.job.model.request.PostJobReq;
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
                postJobReq.getJobPublisherIdx());

        int jobCreatedStatus=jobService.createJob(newJob);

        if(jobCreatedStatus!=0) return new ResponseObj<>("");
        else return new ResponseObj(ResponseStatusCode.FAIL_TO_CREATE_JOB);}
        else return new ResponseObj<>(ResponseStatusCode.NOT_FOR_PERSONAL_USERS);

    }

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
            System.out.println(" user= " +userNum);
            List<Job> jobListFilteredByUser= jobService.getJobByUser(userNum);
            return new ResponseObj<>(jobListFilteredByUser);
        }


        if(publisherNum!=null){
            //기업에서 올린 직업공고 조회
        int userIdx=(int)request.getAttribute("userIdx");
        int checkCompanyStatus=jobService.checkCompany(userIdx);

            if(checkCompanyStatus==1){//기업회원일 경우

                List<Job> jobListByPublisher= jobService.getJobByPublisher(publisherNum);
                return new ResponseObj<>(jobListByPublisher);
            }
            else return new ResponseObj<>(ResponseStatusCode.NOT_FOR_PERSONAL_USERS);
        }


        List<Job> jobList= jobService.getJobList();
        return new ResponseObj<>(jobList);


    }

    //유저가 제출할 지원서 정보 확인 API
    @ResponseBody
    @GetMapping("/{jobIdx}/application")
    public ResponseObj<Application> getApplication(HttpServletRequest request,
                                              @PathVariable("jobIdx")int jobIdx){
        int userIdx=(int)request.getAttribute("userIdx");
        Application application = jobService.getResume(userIdx);

        return new ResponseObj<>(application);

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

    //공고에 지원한 사람들인지 확인
    @ResponseBody
    @GetMapping("/{jobIdx}/applicants")
    public ResponseObj<List<Application>> getApplications(HttpServletRequest request,
            @PathVariable("jobIdx")int jobIdx){
        int userIdx=(int)request.getAttribute("userIdx");
        int checkCompanyStatus=jobService.checkCompany(userIdx);

        if(checkCompanyStatus==1) {//기업회원일 경우
           List<Application> applicantList=jobService.getApplicants(jobIdx);
           return new ResponseObj<>(applicantList);
        }
        else return new ResponseObj<>(ResponseStatusCode.NOT_FOR_PERSONAL_USERS);
    }



}
