package com.yangchenle.electricityconsumptionsystem.controller;

import com.yangchenle.electricityconsumptionsystem.common.CommonResult;
import com.yangchenle.electricityconsumptionsystem.constant.PhoneNum;
import com.yangchenle.electricityconsumptionsystem.constant.SessionParameters;
import com.yangchenle.electricityconsumptionsystem.dto.AdminDTO;
import com.yangchenle.electricityconsumptionsystem.service.AdminService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class AdminController {
    @Resource
    private AdminService adminService;
    @PostMapping("/manager/login")
    public CommonResult selectAdminByPhone(String phone, HttpServletRequest request){
        HttpSession session = request.getSession();
        if(phone==null||phone.length()!= PhoneNum.LENGTH){
            return CommonResult.fail(403,"参数错误");
        }
        AdminDTO adminDTO = adminService.selectByPhone(phone);
        if(adminDTO==null){
            return CommonResult.fail(404,"用户不存在");
        }
        session.setAttribute(SessionParameters.PHONE,phone);
        session.setAttribute(SessionParameters.MANAGERID,adminDTO.getAdminId());
        return CommonResult.success("登录成功");
    }
}
