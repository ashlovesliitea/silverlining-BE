package com.example.capstone.policy;

import com.example.capstone.policy.model.entity.Policy;
import com.example.capstone.policy.model.response.GetLikedRes;
import com.example.capstone.user.UserDao;
import com.example.capstone.user.model.entity.User;
import com.example.capstone.utils.AgeCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static com.example.capstone.utils.AgeCalculator.calculateAge;

@Repository
public class PolicyDao {
    private JdbcTemplate jdbcTemplate;
    private UserDao userDao;

    @Autowired
    public PolicyDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate=new JdbcTemplate(dataSource);
    }

    public List<Policy> getPolicies(int userIdx) {
        User user=userDao.findUser(userIdx);
        Integer user_income_pct=0;
        Integer user_age= calculateAge(user.getUser_birth());
        System.out.println("user_age = " + user_age);
        if(user.getUser_median_income()!=null){
            user_income_pct=user.getUser_median_income();
        }

        String policyQuery="select policy_idx,applicant_age,applicant_income from policy";
        List<Policy> policyList=this.jdbcTemplate.query(policyQuery,
                (rs,rowNum)-> new Policy(rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3)));

        List<Policy> filteredPolicyList=new ArrayList<>();
        for(Policy policy:policyList){
            if(user_income_pct==0 //소득 수준 입력이 안됐을 때
                    ||policy.getApplicantIncome()==0 // 정책이 소득 수준과 무관할때
                    ||(user_income_pct!=0 && policy.getApplicantIncome()>user_income_pct)){
                if(user_age>=policy.getApplicantAge()){
                    filteredPolicyList.add(getPolicy(policy.getPolicyIdx()));
                }
            }


        }
        return filteredPolicyList;
    }

    public Policy getPolicy(int policyIdx){
        String policyQuery="select policy_idx,policy_name,applicant_age,applicant_income,applicant_subject,policy_operation,policy_support_detail,policy_apply_method,policy_phone from policy where policy_idx=?";
        return this.jdbcTemplate.queryForObject(policyQuery,
                (rs,rowNum)->new Policy(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9)

                ),policyIdx);
    }

    public int likedPolicy(int userIdx, int policyIdx) {
        String likedQuery="select exists(select policy_liked_idx from policy_liked where policy_idx=? and user_idx=?)";
        Object[] likedParams={policyIdx,userIdx};

        return this.jdbcTemplate.queryForObject(likedQuery,int.class,likedParams);
    }

    public void addPolicyLike(int userIdx, int policyIdx) {
        String lastInsertedIdQuery="select max(policy_liked_idx) from policy_liked";
        int lastInsertedId=this.jdbcTemplate.queryForObject(lastInsertedIdQuery,int.class);

        String addLikeQuery="insert into policy_liked(policy_liked_idx,user_idx,policy_idx) values(?,?,?)";
        Object[] addParams={lastInsertedId+1,userIdx,policyIdx};

        this.jdbcTemplate.update(addLikeQuery,addParams);

    }

    public List<GetLikedRes> getLikes(int userIdx) {
        String getLikedQuery="select policy_liked_idx,pl.policy_idx,p.policy_name,p.policy_phone from policy_liked pl inner join policy p on p.policy_idx=pl.policy_idx where pl.user_idx=?";
        return this.jdbcTemplate.query(getLikedQuery,
                (rs,rowNum)->new GetLikedRes(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4)
                ),userIdx);
    }

    public void deleteLike(int likedIdx) {
        String deleteLikedQuery="delete from policy_liked where policy_liked_idx=?";
        this.jdbcTemplate.update(deleteLikedQuery,likedIdx);
    }
}
