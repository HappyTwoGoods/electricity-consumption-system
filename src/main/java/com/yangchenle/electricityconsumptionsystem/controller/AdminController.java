package com.yangchenle.electricityconsumptionsystem.controller;

import com.yangchenle.electricityconsumptionsystem.common.CommonResult;
import com.yangchenle.electricityconsumptionsystem.constant.PhoneNum;
import com.yangchenle.electricityconsumptionsystem.constant.SessionParameters;
import com.yangchenle.electricityconsumptionsystem.dto.AdminDTO;
import com.yangchenle.electricityconsumptionsystem.emun.HttpStatus;
import com.yangchenle.electricityconsumptionsystem.service.AdminService;
import com.yangchenle.electricityconsumptionsystem.service.RandomCodeService;
import org.apache.commons.lang3.StringUtils;
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
        if (phone == null || phone.length() != PhoneNum.LENGTH || code == null || code.length() != 6) {
            return CommonResult.fail(403, "参数错误");
        }
        String newCode = (String) session.getAttribute(phone);
        if (StringUtils.isEmpty(newCode) || !newCode.equals(code)) {
            return CommonResult.fail(403, "验证码不正确！");
        }
        try {
            AdminDTO adminDTO = adminService.selectByPhone(phone);
            session.setAttribute(SessionParameters.PHONE, phone);
            session.setAttribute(SessionParameters.READERID, adminDTO.getAdminId());
            session.setAttribute(phone, "");
            return CommonResult.success("登录成功！");
        } catch (Exception e) {
            return CommonResult.fail(500, "登录异常！");
        }
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
        try {
            session.setAttribute(phone, result);
            return CommonResult.success(result);
        } catch (Exception e) {
            return CommonResult.fail(HttpStatus.ERROR);
        }
    }
}
