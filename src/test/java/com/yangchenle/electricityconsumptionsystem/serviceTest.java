package com.yangchenle.electricityconsumptionsystem;

import com.yangchenle.electricityconsumptionsystem.dao.UserDao;
import com.yangchenle.electricityconsumptionsystem.entity.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class serviceTest {

    @Resource
    private UserDao userDao;

    @Test
    public void queryById(){
        UserEntity userEntity = userDao.queryById(1);
        System.out.println(userEntity);
    }


}
