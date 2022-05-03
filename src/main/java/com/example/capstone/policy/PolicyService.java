package com.example.capstone.policy;

import com.example.capstone.policy.model.entity.Policy;
import com.example.capstone.policy.model.response.GetLikedRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyService {

    private PolicyDao policyDao;

    @Autowired
    public PolicyService(PolicyDao policyDao) {
        this.policyDao = policyDao;
    }

    public List<Policy> getPolicies(int userIdx) {
        return policyDao.getPolicies(userIdx);
    }

    public int likedPolicy(int userIdx, int policyIdx) {
        return policyDao.likedPolicy(userIdx,policyIdx);
    }

    public void addPolicyLike(int userIdx, int policyIdx) {
        policyDao.addPolicyLike(userIdx,policyIdx);
    }

    public List<GetLikedRes> getLikes(int userIdx) {
        return policyDao.getLikes(userIdx);
    }

    public void deleteLike(int likedIdx) {
        policyDao.deleteLike(likedIdx);
    }

    public Policy getPolicy(int policyIdx) {
        return policyDao.getPolicy(policyIdx);
    }
}
