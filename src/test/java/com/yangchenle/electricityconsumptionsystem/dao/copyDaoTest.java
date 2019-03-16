package com.yangchenle.electricityconsumptionsystem.dao;

import com.yangchenle.electricityconsumptionsystem.entity.CopyRecordEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class copyDaoTest {
    @Resource
    private CopyRecordDao copyRecordDao;

    @Test
    public void selectNewRecordTest() {
        List<CopyRecordEntity> copyRecordEntities = copyRecordDao.selectNewRecord();
        System.out.println(copyRecordEntities);
    }
}
