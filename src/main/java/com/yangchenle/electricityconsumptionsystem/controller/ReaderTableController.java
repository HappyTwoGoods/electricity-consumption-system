package com.yangchenle.electricityconsumptionsystem.controller;

import com.yangchenle.electricityconsumptionsystem.common.CommonResult;
import com.yangchenle.electricityconsumptionsystem.dto.ReaderAccountDTO;
import com.yangchenle.electricityconsumptionsystem.service.RandomCodeService;
import com.yangchenle.electricityconsumptionsystem.service.ReaderTableService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.regex.Pattern;

@RestController
public class ReaderTableController {

    @Resource
    private ReaderTableService readerTableService;

    @Resource
    private RandomCodeService randomCodeService;

    /**
     * 获取验证码
     *
     * @param readerPhone
     * @return
     */
    @GetMapping("/reader/verifyCode")
    public CommonResult readerVerifyCode(String readerPhone){
        String regex = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";
        if (StringUtils.isBlank(readerPhone) || !Pattern.matches(regex, readerPhone)) {
            return CommonResult.fail(403,"参数错误！");
        }
        ReaderAccountDTO readerAccountDTO = readerTableService.readerLogin(readerPhone);
        if (readerAccountDTO.getReaderId() == null){
            return CommonResult.fail(404,"该抄表员未注册！");
        }
        String result = randomCodeService.buildCode();
        if (StringUtils.isBlank(regex) || result.length() != 6){
            return CommonResult.fail(500,"获取验证码失败！");
        }
        return CommonResult.success(result);
    }

    /**
     * 抄表员登录
     *
     * @param readerPhone
     * @param code
     * @return
     */
    @GetMapping("/reader/login")
    public CommonResult readerlogin(String readerPhone, String code){
        if (readerPhone == null || code == null){
            return CommonResult.fail(403,"参数错误！");
        }
        ReaderAccountDTO readerAccountDTO = readerTableService.readerLogin(readerPhone);
        if (readerAccountDTO.getReaderId() == null){
            return CommonResult.fail(404,"该用户未注册！");
        }
        return CommonResult.success();
    }

    /**
     * 抄表员查看个人信息
     *
     * @return
     */
    @GetMapping("/raeder/query/readerId")
    public CommonResult queryById(){
        Integer reaedrId = 1;
        ReaderAccountDTO readerAccountDTO = readerTableService.queryById(reaedrId);
        if (readerAccountDTO.getReaderId() == null){
            return CommonResult.fail(404,"没有该用户信息！");
        }
        return CommonResult.success(readerAccountDTO);
    }

    /**
     * 抄表员修改个人信息
     *
     * @param readerName
     * @return
     */
    @GetMapping("/update/readerInfo")
    public CommonResult updateById(String readerName){
        if (readerName == null){
            return CommonResult.fail(403,"参数错误！");
        }
        Integer readerId = 1;
        int result = readerTableService.updateReaderInfo(readerName,readerId);
        if (result <= 0){
            return CommonResult.fail(500,"信息修改失败！");
        }
        return CommonResult.success();
    }
}
