package com.yangchenle.electricityconsumptionsystem.service;

import com.yangchenle.electricityconsumptionsystem.dto.AdminDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminTest {
    @Resource
    private AdminService adminService;

    @Test
    public void selectAdminById() {
        AdminDTO adminDTO = adminService.selectById(1);
        System.out.println(adminDTO);
    }

    @Test
    public void selectAdminByPhone() {
        AdminDTO adminDTO = adminService.selectByPhone("13456567878");
        System.out.println(adminDTO);
    }
}
