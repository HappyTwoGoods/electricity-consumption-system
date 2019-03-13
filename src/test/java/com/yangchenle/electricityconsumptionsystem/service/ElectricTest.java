package com.yangchenle.electricityconsumptionsystem.service;

import com.yangchenle.electricityconsumptionsystem.constant.ElectricState;
import com.yangchenle.electricityconsumptionsystem.constant.ElectricType;
import com.yangchenle.electricityconsumptionsystem.dto.ElectricDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElectricTest {
    @Resource
    private ElectricService electricService;
    @Test
    public void selectElectric(){
        List<ElectricDTO> electricDTOS = electricService.queryEleByUserId(1);
        System.out.println(electricDTOS);
    }
    @Test
    public void addElectricTest(){
        int i = electricService.addElectric(ElectricType.HOEM, BigDecimal.valueOf(44.5));
        System.out.println(i);
    }
    @Test
    public void updateElectricTest(){
        int i = electricService.updateElectric(BigDecimal.valueOf(12.5), BigDecimal.valueOf(5.44), ElectricState.STOP, 1);
        System.out.println(i);
        int i1 = electricService.updateElectric(null, null, ElectricState.NORMAL, 1);
        int i2 = electricService.updateElectric(BigDecimal.valueOf(11.2), null, null, 1);
        int i3 = electricService.updateElectric(null, BigDecimal.valueOf(100), null, 1);
        System.out.println(i1+""+i2+""+i3);
    }
    @Test
    public void deleteElectricByIdTest(){
        int i = electricService.deleteElectricById(1);
        System.out.println(i);
    }
    @Test
    public void selectElectricById(){
        ElectricDTO electricDTO = electricService.selectElectricById(1);
        System.out.println(electricDTO);
    }
}
