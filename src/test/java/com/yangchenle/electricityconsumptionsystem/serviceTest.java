package com.yangchenle.electricityconsumptionsystem;

import com.yangchenle.electricityconsumptionsystem.dao.UserDao;
import com.yangchenle.electricityconsumptionsystem.dto.*;
import com.yangchenle.electricityconsumptionsystem.entity.UserEntity;
import com.yangchenle.electricityconsumptionsystem.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class serviceTest {

    @Resource
    private UserService userService;

    @Resource
    private ElectricService electricService;

    @Resource
    private ReaderTableService readerTableService;

    @Resource
    private CopyRecordService copyRecordService;

    @Resource
    private PaymentRecordService paymentRecordService;

    @Test
    public void queryById(){
        UserDTO userDTO = userService.queryById(1);
        System.out.println(userDTO);
    }

    @Test
    public void queryByPhone(){
        UserDTO userDTO = userService.userLogin("15991183771");
        System.out.println(userDTO);
    }

    @Test
    public void insertUserInfo(){
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("Alice");
        userDTO.setUserPhone("18220822497");
        userDTO.setUserAccount(110);
        userDTO.setUserAddress("陕西省安康市");
        userDTO.setIdCard("610110199510021123");
        userDTO.setPrice(new BigDecimal(0));
        int data = userService.insertUser(userDTO);
        System.out.println(data);
    }

    @Test
    public void updateUserInfo(){
        int data = userService.updateUserInfo("Bob",107,null,1);
        System.out.println(data);
    }

    @Test
    public void selectElectric(){
        List<ElectricDTO> electricDTO = electricService.queryEleByUserId(1);
        System.out.println(electricDTO);
    }

    @Test
    public void readerLogin(){
        ReaderAccountDTO readerAccountDTO = readerTableService.readerLogin("18220822010");
        ReaderAccountDTO readerAccountDTO1 = readerTableService.queryById(1);
        System.out.println(readerAccountDTO);
        System.out.println("1"+readerAccountDTO1);
    }

    @Test
    public void updateReader(){
        int data = readerTableService.updateReaderInfo("李四",1);
        System.out.println(data);
    }

    @Test
    public void selectPay(){
        List<PaymentRecordDTO> paymentRecordDTOS = paymentRecordService.queryPayment(1,1);
        System.out.println(paymentRecordDTOS);
    }

    @Test
    public void updatePay(){
        int data = paymentRecordService.updatePayment(1,new BigDecimal(0),1,1);
        System.out.println(data);
    }








}
