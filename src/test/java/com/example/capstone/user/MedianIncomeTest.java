package com.example.capstone.user;

import org.junit.jupiter.api.Test;

public class MedianIncomeTest {
    static final double median_income=1757194;
    @Test
    public void IncomeTest(){
        double income=36000000;
        int percent= (int) ((income/median_income)*100);

        System.out.println("median_percentage = " + percent);

    }
}
