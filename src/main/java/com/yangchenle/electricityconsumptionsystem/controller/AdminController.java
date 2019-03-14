package com.yangchenle.electricityconsumptionsystem.controller;

import com.yangchenle.electricityconsumptionsystem.common.CommonResult;
import com.yangchenle.electricityconsumptionsystem.constant.PhoneNum;
import com.yangchenle.electricityconsumptionsystem.constant.SessionParameters;
import com.yangchenle.electricityconsumptionsystem.dto.AdminDTO;
import com.yangchenle.electricityconsumptionsystem.emun.HttpStatus;
import com.yangchenle.electricityconsumptionsystem.service.AdminService;
import com.yangchenle.electricityconsumptionsystem.service.RandomCodeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class AdminController {
    @Resource
    private AdminService adminService;
    @Resource
    private RandomCodeService randomCodeService;

    @PostMapping("/manager/login")
    public CommonResult selectAdminByPhone(String phone, String code, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (phone == null || phone.length() != PhoneNum.LENGTH || code == null || code.length() < 6) {
            return CommonResult.fail(403, "参数错误");
        }
        String newPhone = (String) session.getAttribute(SessionParameters.PHONE);
        String newCode = (String) session.getAttribute(SessionParameters.PASSNUM);
        if(newCode.equals(code)&&newPhone.equals(phone)){
            session.setAttribute(SessionParameters.PASSNUM,"");
            return CommonResult.success("登录成功");
        }
        return CommonResult.fail(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/manager/verifyCode")
    public CommonResult readerVerifyCode(String phone, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (phone == null || phone.length() < PhoneNum.LENGTH) {
            return CommonResult.fail(403, "参数错误！");
        }
        AdminDTO adminDTO = adminService.selectByPhone(phone);
        if (adminDTO == null) {
            return CommonResult.fail(404, "管理员不存在！");
        }
        String result = randomCodeService.buildCode();
        if (result == null || result.length() != 6) {
            return CommonResult.fail(500, "获取验证码失败！");
        }
        session.setAttribute(SessionParameters.PHONE, phone);
        session.setAttribute(SessionParameters.PASSNUM, result);
        session.setAttribute(SessionParameters.READERID, adminDTO.getAdminId());
        return CommonResult.success(result);
    }
}
