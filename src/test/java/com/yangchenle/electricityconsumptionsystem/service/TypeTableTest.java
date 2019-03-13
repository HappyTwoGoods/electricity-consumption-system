package com.yangchenle.electricityconsumptionsystem.service;

import com.yangchenle.electricityconsumptionsystem.dto.TypeTableDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TypeTableTest {
    @Resource
    private TypeTableService typeTableService;

    @Test
    public void selectTypeByNumTest(){
        TypeTableDTO typeTableDTO = typeTableService.selectByNum(1);
        System.out.println(typeTableDTO);
    }
    @Test
    public void selectUpdateByNumTest(){
        int i = typeTableService.updateTypeTableByNum(BigDecimal.valueOf(0.47), 1);
        System.out.println(i);
    }
}
