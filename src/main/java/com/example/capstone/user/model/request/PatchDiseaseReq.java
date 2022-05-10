package com.example.capstone.user.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class PatchDiseaseReq {
    private int user_idx;
    private List<Integer> user_disease_list;
}
