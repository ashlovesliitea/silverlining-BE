package com.example.capstone.policy;

import com.example.capstone.config.ResponseObj;
import com.example.capstone.config.ResponseStatusCode;
import com.example.capstone.policy.model.entity.Policy;
import com.example.capstone.policy.model.response.GetLikedRes;
import com.example.capstone.user.UserService;
import com.example.capstone.user.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/app/policies")
public class PolicyController {
    private PolicyService policyService;
    private UserService userService;

    @Autowired
    public PolicyController(PolicyService policyService, UserService userService) {
        this.policyService = policyService;
        this.userService = userService;
    }

    @ResponseBody
    @GetMapping("")
    public ResponseObj<List<Policy>> searchPolicies(HttpServletRequest request,
            @RequestParam(value="user-idx",required = false) int userIdx){
        int userLoginIdx=(int)request.getAttribute("userIdx");
        if(userLoginIdx==userIdx){

        User user=userService.findUserInfo(userIdx);
        List<Policy> policyList= policyService.getPolicies(userIdx);

        return new ResponseObj<>(policyList);}
        else return new ResponseObj<>(ResponseStatusCode.INVALID_USER_JWT);

    }

    @ResponseBody
    @GetMapping("/{policyIdx}")
    public ResponseObj<Policy> searchPolicy(@PathVariable("policyIdx")int policyIdx){

        Policy policy= policyService.getPolicy(policyIdx);

        return new ResponseObj<>(policy);

    }

    @ResponseBody
    @PostMapping("/{policyIdx}")
    public ResponseObj<String> addPolicyLike(HttpServletRequest request,
                                        @RequestParam(value="user-idx",required=false)int userIdx,
                                             @PathVariable("policyIdx")int policyIdx){
        int userLoginIdx=(int)request.getAttribute("userIdx");
        if(userLoginIdx==userIdx){
        if(policyService.likedPolicy(userIdx,policyIdx)==0){
            policyService.addPolicyLike(userIdx,policyIdx);
            return new ResponseObj<>("");
        }
        else return new ResponseObj(ResponseStatusCode.ALREADY_LIKED_POLICY);}
        else return new ResponseObj<>(ResponseStatusCode.INVALID_USER_JWT);
    }


    @ResponseBody
    @GetMapping("/liked-list")
    public ResponseObj<List<GetLikedRes>> addPolicyLike(HttpServletRequest request,
                                                        @RequestParam(value="user-idx",required=false)int userIdx){
       int userLoginIdx=(int)request.getAttribute("userIdx");
        if(userLoginIdx==userIdx){
        List<GetLikedRes> likedList=policyService.getLikes(userIdx);
        return new ResponseObj<>(likedList);}
        else return new ResponseObj<>(ResponseStatusCode.INVALID_USER_JWT);
    }

    @ResponseBody
    @DeleteMapping("/liked-list")
    public ResponseObj<String> deletePolicyLike(@RequestParam(value="liked-idx", required=false)int likedIdx){
        policyService.deleteLike(likedIdx);
        return new ResponseObj<>("");
    }

}
