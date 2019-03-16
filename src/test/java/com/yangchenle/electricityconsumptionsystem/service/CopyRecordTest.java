package com.yangchenle.electricityconsumptionsystem.service;

import com.yangchenle.electricityconsumptionsystem.dto.CopyRecordDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CopyRecordTest {
    @Resource
    private CopyRecordService copyRecordService;

    @Test
    public void addCopyTest() {
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            CopyRecordDTO copyRecordDTO = new CopyRecordDTO();
            copyRecordDTO.setCopyData(BigDecimal.valueOf(random.nextFloat()*100+100));
            copyRecordDTO.setReaderId(1);
            copyRecordDTO.setElectricId(i+1);
            copyRecordService.addCopyRecord(copyRecordDTO);
        }
    }

    @Test
    public void updateCopyTest() {
        int i = copyRecordService.updateCopyRecord(BigDecimal.valueOf(2.3), 1);
        System.out.println(i);
    }

    @Test
    public void selectCopyTest() {
        CopyRecordDTO copyRecordDTO = copyRecordService.selectCopyRecordById(1);
        System.out.println(copyRecordDTO);
    }
}
