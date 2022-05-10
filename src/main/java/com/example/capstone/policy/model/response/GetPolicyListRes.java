package com.example.capstone.policy.model.response;

import com.example.capstone.policy.model.entity.Policy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class GetPolicyListRes {
    private int user_age;
    private int user_income_percentage;
    private List<Policy> policyList;
}
