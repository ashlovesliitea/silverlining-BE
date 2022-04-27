package com.example.capstone.facility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class FacilityDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate=new JdbcTemplate(dataSource);
    }

    public List<String> findKeywordList(int userIdx) {
        String findKeywordQuery="select distinct department_name\n" +
                "from hospital_department hd\n" +
                "inner join\n" +
                "\t(select ud.user_idx,dd.department_idx\n" +
                "\tfrom user_disease ud\n" +
                "\tinner join disease_department dd on ud.disease_idx=dd.disease_idx\n" +
                "\t) udd on hd.department_idx=udd.department_idx\n" +
                "where user_idx=?";

        return this.jdbcTemplate.query(findKeywordQuery,
                (rs,rowNum)->rs.getString(1),
                userIdx);

    }
}
