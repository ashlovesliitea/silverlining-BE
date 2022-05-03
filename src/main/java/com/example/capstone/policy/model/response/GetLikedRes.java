package com.example.capstone.policy.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class GetLikedRes {
    private int policy_liked_idx;
    private int policy_idx;
    private String policy_name;
}
