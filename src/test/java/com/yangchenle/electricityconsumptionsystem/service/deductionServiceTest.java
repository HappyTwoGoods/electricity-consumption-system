package com.yangchenle.electricityconsumptionsystem.service;

import com.yangchenle.electricityconsumptionsystem.dto.DeductionRecordDTO;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class deductionServiceTest {
    @Resource
    private DeductionService deductionService;
    @Test
    public void selectAllTest(){
        List<DeductionRecordDTO> deductionRecordDTOS = deductionService.selectDeductionRecordAll();
        System.out.println(deductionRecordDTOS);
    }
    @Test
    public void selectByIdTest(){
        DeductionRecordDTO deductionRecordDTO = deductionService.selectDeductionRecordById(1);
        System.out.println(deductionRecordDTO);
    }
    @Test
    public void selectTest(){
        List<DeductionRecordDTO> deductionRecordDTOS = deductionService.selectDeductionRecord(1, null, null);
        System.out.println(deductionRecordDTOS);
        List<DeductionRecordDTO> deductionRecordDTOS1 = deductionService.selectDeductionRecord(null, new DateTime().minusMonths(1).toDate(), new Date());
        System.out.println(deductionRecordDTOS1.size());
    }
}
