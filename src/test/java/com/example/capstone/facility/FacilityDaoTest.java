package com.example.capstone.facility;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"spring.config.location=classpath:application-test.yml"})
public class FacilityDaoTest {

    @Autowired
    FacilityDao facilityDao;

    @Test
    void keywordTest(){
        List<String> diseaseList=facilityDao.findKeywordList(1);
        Assertions.assertThat(diseaseList.size()).isEqualTo(4);
    }
}
