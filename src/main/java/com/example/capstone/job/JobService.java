package com.example.capstone.job;

import com.example.capstone.job.model.entity.Job;
import com.example.capstone.job.model.request.*;
import com.example.capstone.job.model.response.GetApplicationRes;
import com.example.capstone.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {
    private final JobDao jobDao;
    private final JwtService jwtService;
    private PasswordEncoder passwordEncoder;


    @Autowired
    public JobService(JobDao jobDao, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.jobDao = jobDao;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }


    public int findLastIdx() {
        return jobDao.findLastIdx();
    }

    public int createJob(Job newJob) {
        return jobDao.createJob(newJob);
    }

    public int checkCompany(int userIdx) {
        return jobDao.checkCompany(userIdx);
    }

    public List<Job> getJobByUser(Integer userNum) {
        return jobDao.getJobByUser(userNum);
    }

    public Job findJobByIdx(int job_idx) {
        return jobDao.getJob(job_idx);
    }

    public List<Job> getJobByPublisher(Integer publisherNum) {
        return jobDao.getJobByPublisher(publisherNum);
    }

    public List<Job> getJobList() {
        return jobDao.getJobList();
    }

    public GetApplicationRes getResume(int userIdx) {
        return jobDao.getApplication(userIdx);
    }

    public int apply(int jobIdx, int userIdx) {
        return jobDao.apply(jobIdx,userIdx);
    }

    public int checkApplyStatus(int userIdx, int jobIdx) {
        return jobDao.checkApplyStatus(userIdx,jobIdx);
    }

    public List<GetApplicationRes> getApplicants(int jobIdx) {
        return jobDao.getApplicants(jobIdx);
    }

    public int checkPublisherStatus(int jobIdx) {
        return jobDao.checkPublisherStatus(jobIdx);
    }

    public int modifyTitle( PatchTitleReq patchTitleReq) {
        return jobDao.modifyTitle(patchTitleReq);
    }

    public int modifyAge(PatchAgeReq patchAgeReq) {
        return jobDao.modifyAge(patchAgeReq);
    }

    public int modifyGender(PatchGenderReq patchGenderReq) {
        return jobDao.modifyGender(patchGenderReq);}

    public int modifyWage(PatchWageReq patchWageReq) {
        return jobDao.modifyWage(patchWageReq);
    }

    public int modifyTime(PatchTimeReq patchTimeReq) {
        return jobDao.modifyTime(patchTimeReq);
    }

    public int modifyPersonnel(PatchPersonnelReq patchPersonnelReq) {
        return jobDao.modifyPersonnel(patchPersonnelReq);
    }

    public int modifyDetail(PatchDetailReq patchDetailReq) {
        return jobDao.modifyDetail(patchDetailReq);
    }

    public int modifyOffer(PatchOfferReq patchOfferReq) {
        return jobDao.modifyOffer(patchOfferReq);
    }

    public int modifyyAddress(PatchAddressReq patchAddressReq) {
        return jobDao.modifyAddress(patchAddressReq);
    }
}
