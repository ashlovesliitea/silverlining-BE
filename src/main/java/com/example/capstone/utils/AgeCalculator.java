package com.example.capstone.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class AgeCalculator {
    public static int calculateAge(Date birthDate){
        LocalDate birthLocalDate=modifyDate(birthDate);
        LocalDate curLocalDate=LocalDate.now();
        System.out.println("curLocalDate = " + curLocalDate);
        return Period.between(birthLocalDate,curLocalDate).getYears();
    }

    public static LocalDate modifyDate(Date date){
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
