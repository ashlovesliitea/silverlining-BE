package com.example.capstone.user;

import com.example.capstone.user.model.entity.User;
import com.example.capstone.user.model.request.*;
import com.example.capstone.user.model.response.GetApplyRes;
import com.example.capstone.chat.model.GetPhoneRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate=new JdbcTemplate(dataSource);
    }

    public int getLastId() {
        String getUsersLastId="Select max(user_idx) from user";
        return this.jdbcTemplate.queryForObject(getUsersLastId,
                int.class);
    }

    public int checkUserId(String user_id) {

            String checkEmailQuery = "select exists(select user_id from user where user_id = ?)";
            String checkEmailParams = user_id;
            return this.jdbcTemplate.queryForObject(checkEmailQuery,
                    int.class,
                    checkEmailParams);

    }
    public int checkUserByIdx(int user_idx){
        String checkUserIdxQuery="select exists(select * from user where user_idx=?)";

        return this.jdbcTemplate.queryForObject(checkUserIdxQuery,int.class,user_idx);
    }
    public int createUser(User user) {
        String createUserQuery="insert into user(user_idx,user_id,user_pw,user_name,user_birth,user_gender,user_phone,user_company_status,user_experience,user_drive_status," +
                "user_siNm,user_sggNm,user_emdNm,user_streetNm,user_detailNm,user_lat,user_lng,user_insurance_status,user_guardian_phone,user_median_income,user_profile_img) " +
                "Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        Object[] createUserParams={user.getUser_idx(),user.getUser_id(),user.getUser_pw(),user.getUser_name(),user.getUser_birth(),user.getUser_gender(),user.getUser_phone(),user.getUser_company_status(),
        user.getUser_experience(),user.getUser_drive_status(),user.getUser_siNm(),user.getUser_sggNm(),user.getUser_emdNm(),user.getUser_streetNm(),user.getUser_detailNm(),
        user.getUser_lat(),user.getUser_lng(),user.getUser_insurance_status(),user.getUser_guardian_phone(),user.getUser_median_income(),user.getUser_profile_img()};

        this.jdbcTemplate.update(createUserQuery,createUserParams);

        if(user.getUser_disease_list()!=null) {

            List<Integer> user_disease_list = user.getUser_disease_list();
            for (int dis : user_disease_list) {
                String lastDisId = "select max(user_disease_idx) from user_disease";
                int lastId = this.jdbcTemplate.queryForObject(lastDisId, int.class);

                String createUserDisease = "insert into user_disease(user_disease_idx,disease_idx,user_idx) values(?,?,?)";
                Object[] createUserDiseaseParams = {lastId + 1, dis, user.getUser_idx()};
                this.jdbcTemplate.update(createUserDisease, createUserDiseaseParams);
            }
        }
        if(user.getUser_job_cate_list()!=null){
        List<Integer> user_job_cate_list=user.getUser_job_cate_list();
        for(int job:user_job_cate_list){
            String lastJobId="select max(user_job_category_idx) from user_job_category";
            int lastId=this.jdbcTemplate.queryForObject(lastJobId,int.class);

            String createUserJobList="insert into user_job_category(user_job_category_idx,user_idx,job_category_idx) values(?,?,?)";
            Object[] createUserJobParams={lastId+1,user.getUser_idx(),job};
            this.jdbcTemplate.update(createUserJobList,createUserJobParams);
        }


        }
        return getLastId();

    }

    public String findPwd(String user_id) {
        String findPwdQuery="select user_pw from user where user_id=?";
        return this.jdbcTemplate.queryForObject(findPwdQuery,String.class,user_id);
    }

    public String findPwdByIdx(int user_idx) {
        String findPwdQuery="select user_pw from user where user_idx=?";
        return this.jdbcTemplate.queryForObject(findPwdQuery,String.class,user_idx);
    }

    public int findUserIdx(String user_id) {
        String findIdxQuery="select user_idx from user where user_id=?";
        return this.jdbcTemplate.queryForObject(findIdxQuery,int.class,user_id);
    }

    public User findUser(int user_idx){
        List<Integer> jobCateList = getListByQuery(user_idx, "select job_category_idx from user_job_category where user_idx=?");

        List<Integer> userDiseaseList = getListByQuery(user_idx, "select disease_idx from user_disease where user_idx=?");

        String findUserQuery="select * from user where user_idx=?";

       return this.jdbcTemplate.queryForObject(findUserQuery,
                (rs,rowNum)->new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDate(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getInt(8),
                        rs.getString(9),
                        rs.getInt(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getString(13),
                        rs.getString(14),
                        rs.getString(15),
                        rs.getDouble(16),
                        rs.getDouble(17),
                        rs.getInt(18),
                        rs.getInt(19),
                        rs.getString(20),
                        rs.getString(21),
                        jobCateList,userDiseaseList
                ),user_idx);


    }

    private List<Integer> getListByQuery(int user_idx, String s) {
        String findJobCateQuery = s;
        return this.jdbcTemplate.query(findJobCateQuery,
                (rs, rowNum) -> rs.getInt(1), user_idx);
    }

    public List<Integer> findUserIdxList() {
        String findUserIdxQuery="select user_idx from user";
        return this.jdbcTemplate.query(findUserIdxQuery,
                (rs,rowNum)->rs.getInt(1));
    }

    public List<GetApplyRes> findMyApplications(int userIdx) {
        String jobIdxListQuery="select user_job_idx,uja.job_idx,job_title,company_name from user_job_apply uja " +
                "inner join job on job.job_idx=uja.job_idx where user_idx=?";
        return this.jdbcTemplate.query(jobIdxListQuery,
                (rs,rowNum)->new GetApplyRes(
                        rs.getInt(1),
                        userIdx,
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4)
                ),userIdx);


    }

    public int checkUserPhone(String recipientNum) {
        String checkPhoneQuery="select exists(select * from user where user_phone=?)";
        return this.jdbcTemplate.queryForObject(checkPhoneQuery,int.class,recipientNum);
    }

    public int modifyPassword(int userIdx, String pwd) {
        String modifyPwQuery="update user u set u.user_pw=? where user_idx=?";
        Object[] modifyParams={pwd,userIdx};
        return this.jdbcTemplate.update(modifyPwQuery,modifyParams);
    }

    public void modifyDisease(int userIdx, List<Integer> diseaseList) {
        String deleteDisease="delete from user_disease where user_idx=?";
        this.jdbcTemplate.update(deleteDisease,userIdx);
        if(diseaseList!=null) {
            System.out.println("userIdx = " + userIdx);

            for (int dis : diseaseList) {
                String lastDisId = "select max(user_disease_idx) from user_disease";
                int lastId = this.jdbcTemplate.queryForObject(lastDisId, int.class);

                String createUserDisease = "insert into user_disease(user_disease_idx,disease_idx,user_idx) values(?,?,?)";
                Object[] createUserDiseaseParams = {lastId + 1, dis, userIdx};
                this.jdbcTemplate.update(createUserDisease, createUserDiseaseParams);
            }
        }


    }

    public int modifyAddress(int userIdx, PatchAddressReq patchAddressReq) {
        String updateAddress="update user u set u.user_siNm=?,u.user_lat=?,u.user_lng=? where u.user_idx=?";
        Object[] addressParams={patchAddressReq.getSiNm(),patchAddressReq.getUser_lat(),patchAddressReq.getUser_lng(),userIdx};
        return this.jdbcTemplate.update(updateAddress,addressParams);
    }

    public GetPhoneRes findByUserPhone(String user_phone) {
        String findUserInfoQuery="select user_idx,user_name from user where user_phone=?";


        return this.jdbcTemplate.queryForObject(findUserInfoQuery,
                (rs,rowNum)->new GetPhoneRes(rs.getInt(1),
                        rs.getString(2)),
                user_phone);
    }

    public Integer modifyInsurance(PatchInsuranceReq patchInsuranceReq) {
        String updateInsuranceQuery="update user u set u.user_insurance_status=? where u.user_idx=?";
        Object[] insuranceParams={patchInsuranceReq.getUser_insurance_status(),patchInsuranceReq.getUser_idx()};
        return this.jdbcTemplate.update(updateInsuranceQuery,insuranceParams);
    }

    public int modifyDrive(PatchDriveReq patchDriveReq) {
        String updateDriveStatusQuery="update user u set u.user_drive_status=?  where u.user_idx=?";
        Object[] driveParams={patchDriveReq.getUser_drive_status(),patchDriveReq.getUser_idx()};
        return this.jdbcTemplate.update(updateDriveStatusQuery,driveParams);
    }

    public int modifyExperience(PatchExpReq patchExpReq) {
        String updateExpQuery="update user u set u.user_experience=?  where u.user_idx=?";
        Object[] ExpParams={patchExpReq.getUser_experience(),patchExpReq.getUser_idx()};
        return this.jdbcTemplate.update(updateExpQuery,ExpParams);
    }

    public int modifyDetailAddress(PatchDetailAddressReq patchDetailAddressReq) {
        String updateDetailAddressQuery="update user u set u.user_detailNm=?  where u.user_idx=?";
        Object[] detailAddressParams={patchDetailAddressReq.getUser_detailNm(),patchDetailAddressReq.getUser_idx()};
        return this.jdbcTemplate.update(updateDetailAddressQuery,detailAddressParams);
    }

    public int modifyGuardian(PatchGuardianReq patchGuardianReq) {
        String updateGuardianQuery="update user u set u.user_guardian_phone=?  where u.user_idx=?";
        Object[] modifyGuardianParams={patchGuardianReq.getUser_guardian_phone(),patchGuardianReq.getUser_idx()};
        return this.jdbcTemplate.update(updateGuardianQuery,modifyGuardianParams);
    }

    public int modifyIncome(PatchIncomeReq patchIncomeReq) {
        String updateIncomeQuery="update user u set u.user_median_income=? where u.user_idx=?";
        Object[] modifyIncomeParams={patchIncomeReq.getUser_income(),patchIncomeReq.getUser_idx()};
        return this.jdbcTemplate.update(updateIncomeQuery,modifyIncomeParams);
    }
}
