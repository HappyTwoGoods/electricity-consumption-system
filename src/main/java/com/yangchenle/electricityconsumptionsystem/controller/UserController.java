package com.yangchenle.electricityconsumptionsystem.controller;

import com.yangchenle.electricityconsumptionsystem.common.CommonResult;
import com.yangchenle.electricityconsumptionsystem.constant.SessionParameters;
import com.yangchenle.electricityconsumptionsystem.dto.UserDTO;
import com.yangchenle.electricityconsumptionsystem.service.RandomCodeService;
import com.yangchenle.electricityconsumptionsystem.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.regex.Pattern;

@RestController
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private RandomCodeService randomCodeService;

    /**
     * 获取验证码
     *
     * @param userPhone
     * @return
     */
    @GetMapping("/user/verifyCode")
    public CommonResult userVerifyCode(String userPhone, HttpServletRequest request) {
        String regex = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";
        if (StringUtils.isBlank(userPhone) || !Pattern.matches(regex, userPhone)) {
            return CommonResult.fail(403, "参数错误！");
        }
        UserDTO userDTO = userService.userLogin(userPhone);
        if (userDTO.getUserId() == null) {
            return CommonResult.fail(404, "该用户未注册！");
        }
        String result = randomCodeService.buildCode();
        if (StringUtils.isBlank(regex) || result.length() != 6) {
            return CommonResult.fail(500, "获取验证码失败！");
        }
        HttpSession session = request.getSession();
        session.setAttribute(SessionParameters.PHONE, userPhone);
        session.setAttribute(SessionParameters.PASSNUM, result);
        session.setAttribute(SessionParameters.USERID, userDTO.getUserId());
        return CommonResult.success(result);
    }

    /**
     * 用户登录
     *
     * @param userPhone
     * @param code
     * @return
     */
    @GetMapping("/user/login")
    public CommonResult userLogin(String userPhone, String code, HttpServletRequest request) {
        if (userPhone == null || code == null) {
            return CommonResult.fail(403, "参数错误！");
        }
        UserDTO userDTO = userService.userLogin(userPhone);
        if (userDTO.getUserId() == null){
            return CommonResult.fail(404,"没有该用户信息！");
        }
        HttpSession session = request.getSession();
        String phone = (String) session.getAttribute(SessionParameters.PHONE);
        String passNum = (String) session.getAttribute(SessionParameters.PASSNUM);
        if (userPhone.equals(phone) && passNum.equals(code)) {
            session.setAttribute(SessionParameters.PASSNUM,"");
            return CommonResult.success();
        }
        return CommonResult.fail(404, "没有该用户信息！");
    }

    /**
     * 用户注册
     *
     * @param userName
     * @param userPhone
     * @param userAccount
     * @param userAddress
     * @param idCard
     * @param price
     * @return
     */
    @PostMapping("/user/register")
    public CommonResult userRegister(String userName, String userPhone, Integer userAccount,
                                     String userAddress, String idCard, BigDecimal price) {
        if (userName == null || userAccount == null || userAddress == null || userPhone == null || idCard == null) {
            return CommonResult.fail(403, "参数错误！");
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(userName);
        userDTO.setUserAccount(userAccount);
        userDTO.setUserPhone(userPhone);
        userDTO.setUserAddress(userAddress);
        userDTO.setIdCard(idCard);
        userDTO.setPrice(price);
        int data = userService.insertUser(userDTO);
        if (data <= 0) {
            return CommonResult.fail(500, "注册失败！");
        }
        return CommonResult.success();
    }

    /**
     * 用户查看个人信息
     *
     * @return
     */
    @GetMapping("/user/query/userId")
    public CommonResult queryById(HttpServletRequest request) {
//        Integer userId = 1;
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute(SessionParameters.USERID);
        UserDTO userDTO = userService.queryById(userId);
        if (userDTO.getUserId() == null) {
            return CommonResult.fail(404, "没有查到该用户！");
        }
        return CommonResult.success(userDTO);
    }

    /**
     * 用户修改个人信息
     *
     * @param userName
     * @param userAccount
     * @param userAddress
     * @return
     */
    @GetMapping("/user/update/userInfo")
    public CommonResult updateUserInfo(HttpServletRequest request,
                                       @RequestParam(required = false, defaultValue = "") String userName,
                                       @RequestParam(required = false, defaultValue = "") Integer userAccount,
                                       @RequestParam(required = false, defaultValue = "") String userAddress) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute(SessionParameters.USERID);
        //        Integer userId = 1;
        int data = userService.updateUserInfo(userName, userAccount, userAddress, userId);
        if (data <= 0) {
            return CommonResult.fail(500, "用户信息修改错误！");
        }
        return CommonResult.success();
    }

}
