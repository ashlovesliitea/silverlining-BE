package com.example.capstone.policy;

import com.example.capstone.config.ResponseObj;
import com.example.capstone.config.ResponseStatusCode;
import com.example.capstone.policy.model.entity.Policy;
import com.example.capstone.policy.model.response.GetLikedRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/policies")
public class PolicyController {
    private PolicyService policyService;

    @Autowired
    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @ResponseBody
    @GetMapping("")
    public ResponseObj<List<Policy>> searchPolicies(@RequestParam(value="user-idx",required = false) int userIdx){

        List<Policy> policyList= policyService.getPolicies(userIdx);

        return new ResponseObj<>(policyList);

    }

    @ResponseBody
    @GetMapping("/{policyIdx}")
    public ResponseObj<Policy> searchPolicy(@PathVariable("policyIdx")int policyIdx){

        Policy policy= policyService.getPolicy(policyIdx);

        return new ResponseObj<>(policy);

    }

    @ResponseBody
    @PostMapping("/{policyIdx}")
    public ResponseObj<String> addPolicyLike(@RequestParam(value="user-idx",required=false)int userIdx,
                                             @PathVariable("policyIdx")int policyIdx){
        if(policyService.likedPolicy(userIdx,policyIdx)==0){
            policyService.addPolicyLike(userIdx,policyIdx);
            return new ResponseObj<>("");
        }
        else return new ResponseObj(ResponseStatusCode.ALREADY_LIKED_POLICY);
    }


    @ResponseBody
    @GetMapping("/liked-list")
    public ResponseObj<List<GetLikedRes>> addPolicyLike(@RequestParam(value="user-idx",required=false)int userIdx){
        List<GetLikedRes> likedList=policyService.getLikes(userIdx);
        return new ResponseObj<>(likedList);
    }

    @ResponseBody
    @DeleteMapping("/liked-list")
    public ResponseObj<String> deletePolicyLike(@RequestParam(value="liked-idx", required=false)int likedIdx){
        policyService.deleteLike(likedIdx);
        return new ResponseObj<>("");
    }

}
