package com.example.capstone.job;

import com.example.capstone.job.model.entity.Job;
import com.example.capstone.job.model.request.*;
import com.example.capstone.job.model.response.GetApplicationRes;
import com.example.capstone.user.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.capstone.utils.AgeCalculator.calculateAge;
import static com.example.capstone.utils.calculateDistanceByKilometer.distance;

@Repository
public class JobDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate=new JdbcTemplate(dataSource);
    }
    public int findLastIdx() {
        String lastJobQuery="select max(job_idx) from job";
        return this.jdbcTemplate.queryForObject(lastJobQuery,int.class);
    }

    public int createJob(Job newJob) {
       String createJobQuery=" INSERT INTO job (job_idx,job_title, company_name, job_category_id, job_min_age, job_max_age, job_gender, job_wage, job_working_time, job_personnel, job_detail, job_offer_status, job_siNm, job_sggNm, job_emdNm, job_streetNm, job_detailNm, job_lat, job_lng, job_publisher_idx) " +
               "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

       Object[] jobParams={newJob.getJobIdx(),newJob.getJobTitle(),newJob.getCompanyName(),newJob.getJobCategoryId(),newJob.getJobMinAge(),newJob.getJobMaxAge(),
       newJob.getJobGender(),newJob.getJobWage(),newJob.getJobWorkingTime(),newJob.getJobPersonnel(),newJob.getJobDetail(),newJob.getJobOfferStatus(),newJob.getJobSinm(),newJob.getJobSggnm(),
       newJob.getJobEmdnm(),newJob.getJobStreetnm(),newJob.getJobDetailnm(),newJob.getJobLat(),newJob.getJobLng(),newJob.getJobPublisherIdx()};

       return this.jdbcTemplate.update(createJobQuery,jobParams);
    }

    public int checkCompany(int userIdx) {
        String checkCompanyStatus="select user_company_status from user where user_idx=?";
        return this.jdbcTemplate.queryForObject(checkCompanyStatus,int.class,userIdx);
    }

    public List<Job> getJobByUser(Integer userNum) {
        String findAgeQuery="select user_birth,user_gender,user_lat,user_lng from user where user_idx=?";
         User user= this.jdbcTemplate.queryForObject(findAgeQuery,
                (rs,rowNum)-> new User(
                        rs.getDate(1),
                        rs.getInt(2),
                        rs.getDouble(3),
                        rs.getDouble(4)
                ),userNum);
        //나이로 필터링
        Date userBirth=user.getUser_birth();
        int age= calculateAge(userBirth);

        String filterAgeQuery="select job_idx,job_gender,job_lat,job_lng from job" +
                " where job_min_age <= ? and job_max_age >= ? ";

        Object[] ageParams={age,age};
        List<Job> jobListFilteredByAge=this.jdbcTemplate.query(filterAgeQuery,
                (rs,rowNum)->new Job(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getDouble(3),
                        rs.getDouble(4)
                ),ageParams);

        int userGender=user.getUser_gender();


        //거리&성별로 필터링
        List<Job> jobList=new ArrayList<>();
        for(Job job:jobListFilteredByAge){
            int jobGender=job.getJobGender();
            double dist= distance(job.getJobLat(),job.getJobLng(),user.getUser_lat(),user.getUser_lng());
            if(userGender==jobGender||jobGender==2){
                //user의 성별과 공고의 성별이 같거나, 공고가 성별 무관인 경우에 필터링 ok
            if(dist<=30){
                jobList.add(getJob(job.getJobIdx()));
            }}
        }
        return jobList;
    }

    public Job getJob(int jobIdx){
        String findJobQuery="select job_idx,job_title, company_name, job_category_id, job_min_age, job_max_age, job_gender, job_wage, job_working_time, job_personnel, job_detail, job_offer_status, job_siNm, job_sggNm, job_emdNm, job_streetNm, job_detailNm, job_lat, job_lng, job_publisher_idx from job where job_idx=?";
        return this.jdbcTemplate.queryForObject(findJobQuery,
                (rs,rowNum)->new Job(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getInt(10),
                        rs.getString(11),
                        rs.getInt(12),
                        rs.getString(13),
                        rs.getString(14),
                        rs.getString(15),
                        rs.getString(16),
                        rs.getString(17),
                        rs.getDouble(18),
                        rs.getDouble(19),
                        rs.getInt(20)
                ),jobIdx);

    }

    public List<Job> getJobByPublisher(Integer publisherNum) {
        String publisherQuery="select job_idx from job where job_publisher_idx=?";
        List<Integer> idxList=this.jdbcTemplate.query(publisherQuery,
                (rs,rowNum)-> rs.getInt(1),publisherNum);

        List<Job> jobList=new ArrayList<>();
        for(Integer idx:idxList){
            Job newJob=getJob(idx);
            jobList.add(newJob);
        }

        return jobList;
    }

    public List<Job> getJobList() {
        String jobListQuery="select job_idx from job";
        List<Integer> idxList=this.jdbcTemplate.query(jobListQuery,
                (rs,rowNum)-> rs.getInt(1));

        List<Job> jobList=new ArrayList<>();
        for(Integer idx:idxList){
            Job newJob=getJob(idx);
            jobList.add(newJob);
        }

        return jobList;
    }



    public GetApplicationRes getApplication(int userIdx) {
        List<String> jobCateList = getListByQuery(userIdx,
                "select job_category_name from user_job_category ujc inner join job_category jc on ujc.job_category_idx=jc.job_category_idx where user_idx=?");

        String findUserQuery="select user_name,user_birth,user_gender,user_phone,user_drive_status, user_experience,user_profile_img from user where user_idx=?";
        User user=this.jdbcTemplate.queryForObject(findUserQuery,
                (rs,rowNum)->new User(
                        rs.getString(1),
                        rs.getDate(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getString(7)
                ),userIdx);

        int user_age=calculateAge(user.getUser_birth());

        String user_gender="";
        switch (user.getUser_gender()){
            case 0: user_gender="남성";
            case 1: user_gender="여성";
        }

        String user_drive_status="";
        switch(user.getUser_drive_status()){
            case 0: user_drive_status="불가능";
            case 1: user_drive_status="가능";
        }

        return new GetApplicationRes(user.getUser_name(),user.getUser_birth(),user_age,user_gender,user.getUser_phone(),
                user_drive_status,user.getUser_experience(),user.getUser_profile_img(),jobCateList);
    }

    private List<String> getListByQuery(int user_idx, String s) {
        String findJobCateQuery = s;
        return this.jdbcTemplate.query(findJobCateQuery,
                (rs, rowNum) -> rs.getString(1), user_idx);
    }

    public int apply(int jobIdx, int userIdx) {
        String findLastId="select max(user_job_idx) from user_job_apply";
        int lastId=this.jdbcTemplate.queryForObject(findLastId,int.class);

        String applyQuery="insert into user_job_apply(user_job_idx,user_idx,job_idx) values(?,?,?)";
        Object[] applyParams={lastId+1,userIdx,jobIdx};
        return this.jdbcTemplate.update(applyQuery,applyParams);
    }

    public int checkApplyStatus(int userIdx, int jobIdx) {
        String checkApplyQuery="select exists(select user_job_idx from user_job_apply" +
                " where user_idx=? and job_idx=?)";
        Object[] checkApplyParams={userIdx,jobIdx};
        return this.jdbcTemplate.queryForObject(checkApplyQuery,int.class,checkApplyParams);
    }
    


    public List<GetApplicationRes> getApplicants(int jobIdx) {
        String applicantIdxListQuery="select user_idx from user_job_apply where job_idx=?";
        List<Integer> applicantIdxList=this.jdbcTemplate.query(applicantIdxListQuery,
                (rs,rowNum)->rs.getInt(1),jobIdx);

        List<GetApplicationRes> getApplicationResList =new ArrayList<>();
        for(Integer idx:applicantIdxList){
            GetApplicationRes app=getApplication(idx);
            getApplicationResList.add(app);
        }
        return getApplicationResList;
    }


    public int checkPublisherStatus(int jobIdx) {
        String checkQuery="select job_publisher_idx from job where job_idx=?";
        return this.jdbcTemplate.queryForObject(checkQuery,int.class,jobIdx);
    }

    public int modifyTitle( PatchTitleReq patchTitleReq) {
        String modifyTitle="update job j set j.job_title=? where j.job_idx=?";
        Object[] modifyParams={patchTitleReq.getJob_title(),patchTitleReq.getJob_idx()};
        return this.jdbcTemplate.update(modifyTitle,modifyParams);
    }

    public int modifyAge(PatchAgeReq patchAgeReq) {
        String modifyAge="update job j set j.job_min_age=?, j.job_max_age=? where j.job_idx=?";

        Object[] ageParams={patchAgeReq.getJob_min_age(),patchAgeReq.getJob_max_age(),patchAgeReq.getJob_idx()};
        return this.jdbcTemplate.update(modifyAge,ageParams);

    }

    public int modifyGender(PatchGenderReq patchGenderReq) {
        String modifyGender="update job j set j.job_gender=? where j.job_idx=?";
        Object[] modifyParams={patchGenderReq.getJob_gender(),patchGenderReq.getJob_idx()};
        return this.jdbcTemplate.update(modifyGender,modifyParams);
    }

    public int modifyWage(PatchWageReq patchWageReq) {
        String modifyWage="update job j set j.job_wage=? where j.job_idx=?";
        Object[] modifyParams={patchWageReq.getJob_wage(),patchWageReq.getJob_idx()};
        return this.jdbcTemplate.update(modifyWage,modifyParams);
    }

    public int modifyTime(PatchTimeReq patchTimeReq) {
        String modifyTime="update job j set j.job_working_time=? where j.job_idx=?";
        Object[] modifyParams={patchTimeReq.getJob_working_time(),patchTimeReq.getJob_idx()};
        return this.jdbcTemplate.update(modifyTime,modifyParams);
    }

    public int modifyPersonnel(PatchPersonnelReq patchPersonnelReq) {
        String modifyPersonnel="update job j set j.job_personnel=? where j.job_idx=?";
        Object[] modifyParams={patchPersonnelReq.getJob_personnel(),patchPersonnelReq.getJob_idx()};
        return this.jdbcTemplate.update(modifyPersonnel,modifyParams);
    }

    public int modifyDetail(PatchDetailReq patchDetailReq) {
        String modifyDetail="update job j set j.job_detail=? where j.job_idx=?";
        Object[] modifyParams={patchDetailReq.getJob_detail(),patchDetailReq.getJob_idx()};
        return this.jdbcTemplate.update(modifyDetail,modifyParams);
    }

    public int modifyOffer(PatchOfferReq patchOfferReq) {
        String modifyOffer="update job j set j.job_offer_status=? where j.job_idx=?";
        Object[] modifyParams={patchOfferReq.getJob_offer_status(),patchOfferReq.getJob_idx()};
        return this.jdbcTemplate.update(modifyOffer,modifyParams);
    }

    public int modifyAddress(PatchAddressReq patchAddressReq) {
        String modifyAddress="update job j set j.job_siNm=?,j.job_lat=?,j.job_lng=? where j.job_idx=?";
        Object[] modifyParams={patchAddressReq.getJob_siNm(),patchAddressReq.getJob_lat(),patchAddressReq.getJob_lng(),patchAddressReq.getJob_idx()};
        return this.jdbcTemplate.update(modifyAddress,modifyParams);
    }
}
