package com.example.capstone.user;

import com.example.capstone.user.model.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"spring.config.location=classpath:application-test.yml"})
public class UserDaoTest {
    @Autowired
    private UserDao userDao;

    @Test
    public void findUserTest(){
        User user= userDao.findUser(1);
        Assertions.assertThat(user.getUser_id()).isEqualTo("sky123@gmail.com");
    }
}
