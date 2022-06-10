package com.example.capstone.user;

import com.example.capstone.config.ResponseException;
import com.example.capstone.job.JobDao;
import com.example.capstone.user.model.entity.User;
import com.example.capstone.user.model.request.*;
import com.example.capstone.user.model.response.GetApplyRes;
import com.example.capstone.chat.model.GetPhoneRes;
import com.example.capstone.user.model.response.PostLoginRes;
import com.example.capstone.user.model.response.PostUserRes;
import com.example.capstone.utils.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.capstone.config.ResponseStatusCode.*;

import java.util.List;

@Service
@Slf4j
public class UserService {

    private final UserDao userDao;
    private final JwtService jwtService;
    private final JobDao jobDao;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserDao userDao, JwtService jwtService, JobDao jobDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.jwtService = jwtService;
        this.jobDao = jobDao;
        this.passwordEncoder = passwordEncoder;
    }

    public int getLastId(){
        return userDao.getLastId();
    }

    public int checkUserId(String user_id){
        return userDao.checkUserId(user_id);
    }

    @Transactional
    public PostUserRes createUser(User user) throws ResponseException {
        if(checkUserId(user.getUser_id())==1){
            throw new ResponseException(POST_USERS_EXISTS_EMAIL);
        }

        String pwd;
        pwd= passwordEncoder.encode(user.getUser_pw());
        user.setUser_pw(pwd);
        System.out.println("pwd = " + pwd);

        int user_idx= userDao.createUser(user);
        String jwt=jwtService.createJwt(user_idx);
        System.out.println("jwt = " + jwt);
        return new PostUserRes(user_idx,jwt);
    }

    public String findPwd(String user_id){
        return userDao.findPwd(user_id);
    }

    public int findUserIdx(String user_id){
        return userDao.findUserIdx(user_id);
    }

    public PostLoginRes logIn(User user) throws ResponseException {
        if(checkUserId(user.getUser_id())==0){
            throw new ResponseException(POST_USERS_EMAIL_DOESNT_EXIST);
        }

        String pwdFoundById=findPwd(user.getUser_id());

        if(passwordEncoder.matches(user.getUser_pw(),pwdFoundById)){
            //평문과 암호화된 비번이 서로 같음을 비교하는 메소드
            int user_idx=findUserIdx(user.getUser_id());
            String jwt=jwtService.createJwt(user_idx);
            return new PostLoginRes(user_idx,jwt);
        }
        else{
            throw new ResponseException(FAILED_TO_LOGIN);
        }
    }

    public User findUserInfo(int user_idx) {
        return userDao.findUser(user_idx);
    }

    public List<Integer> findUserList(){
        return userDao.findUserIdxList();
    }

    public List<GetApplyRes> findMyApplications(int userIdx) {
        return userDao.findMyApplications(userIdx);
    }

    public int checkUserPhone(String recipientNum) {
        return userDao.checkUserPhone(recipientNum);
    }

    public int modifyPassword(int userIdx, String pw) {
        String pwd= passwordEncoder.encode(pw);
       return userDao.modifyPassword(userIdx,pwd);

    }

    public boolean checkPw(int userIdx, String old_pw) {
        log.info(old_pw);
        String old_pw_db=userDao.findPwdByIdx(userIdx);
        return passwordEncoder.matches(old_pw,old_pw_db);
    }

    public void modifyDisease(int userIdx, List<Integer> diseaseList) {
        userDao.modifyDisease(userIdx,diseaseList);
    }

    public int modifyAddress(int userIdx, PatchAddressReq patchAddressReq) {
        return userDao.modifyAddress(userIdx,patchAddressReq);
    }

    public GetPhoneRes findByUserPhone(String user_phone) {
        return userDao.findByUserPhone(user_phone);
    }

    public Integer modifyInsurance(PatchInsuranceReq patchInsuranceReq) {
        return userDao.modifyInsurance(patchInsuranceReq);
    }

    public int getUserByIdx(int user_idx){
        return userDao.checkUserByIdx(user_idx);
    }

    public int modifyDrive(PatchDriveReq patchDriveReq) {
        return userDao.modifyDrive(patchDriveReq);
    }

    public int modifyExperience(PatchExpReq patchExpReq) {
        return userDao.modifyExperience(patchExpReq);
    }

    public int modifyDetailAddress(PatchDetailAddressReq patchDetailAddressReq) {
        return userDao.modifyDetailAddress(patchDetailAddressReq);
    }

    public int modifyGuardian(PatchGuardianReq patchGuardianReq) {
        return userDao.modifyGuardian(patchGuardianReq);
    }

    public int modifyIncome(PatchIncomeReq patchIncomeReq) {
        return userDao.modifyIncome(patchIncomeReq);
    }
}
