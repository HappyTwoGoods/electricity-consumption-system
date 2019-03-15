package com.yangchenle.electricityconsumptionsystem.controller;

import com.yangchenle.electricityconsumptionsystem.common.CommonResult;
import com.yangchenle.electricityconsumptionsystem.constant.PhoneNum;
import com.yangchenle.electricityconsumptionsystem.constant.SessionParameters;
import com.yangchenle.electricityconsumptionsystem.dto.ReaderAccountDTO;
import com.yangchenle.electricityconsumptionsystem.emun.HttpStatus;
import com.yangchenle.electricityconsumptionsystem.service.RandomCodeService;
import com.yangchenle.electricityconsumptionsystem.service.ReaderTableService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
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
    public CommonResult readerVerifyCode(String readerPhone, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String regex = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";
        if (StringUtils.isBlank(readerPhone) || !Pattern.matches(regex, readerPhone)) {
            return CommonResult.fail(403, "参数错误！");
        }
        ReaderAccountDTO readerAccountDTO = readerTableService.readerLogin(readerPhone);
        if (readerAccountDTO.getReaderId() == null) {
            return CommonResult.fail(404, "该抄表员未注册！");
        }
        String result = randomCodeService.buildCode();
        if (StringUtils.isBlank(regex) || result.length() != 6) {
            return CommonResult.fail(500, "获取验证码失败！");
        }
        try {
            session.setAttribute(readerPhone, result);
            return CommonResult.success(result);
        } catch (Exception e) {
            return CommonResult.fail(HttpStatus.ERROR);
        }
    }

    /**
     * 抄表员登录
     *
     * @param readerPhone
     * @param code
     * @return
     */
    @GetMapping("/reader/login")
    public CommonResult readerLogin(String readerPhone, String code, HttpServletRequest request) {
        if (readerPhone == null || code == null) {
            return CommonResult.fail(403, "参数错误！");
        }
        HttpSession session = request.getSession();
        String newCode = (String) session.getAttribute(readerPhone);
        if (StringUtils.isEmpty(newCode) || !code.equals(newCode)) {
            return CommonResult.fail(403, "验证码不正确");
        }
        try {
            ReaderAccountDTO readerAccountDTO = readerTableService.readerLogin(readerPhone);
            session.setAttribute(SessionParameters.PHONE, readerPhone);
            session.setAttribute(SessionParameters.READERID, readerAccountDTO.getReaderId());
            session.setAttribute(readerPhone,"");
            return CommonResult.success("登录成功！");
        } catch (Exception e) {
            return CommonResult.fail(HttpStatus.ERROR);
        }
    }

    /**
     * 抄表员查看个人信息
     *
     * @return
     */
    @GetMapping("/raeder/query/readerId")
    public CommonResult queryById(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer reaedrId = (Integer) session.getAttribute(SessionParameters.READERID);
//        Integer reaedrId = 1;
        ReaderAccountDTO readerAccountDTO = readerTableService.queryById(reaedrId);
        if (readerAccountDTO.getReaderId() == null) {
            return CommonResult.fail(404, "没有该用户信息！");
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
    public CommonResult updateById(String readerName, HttpServletRequest request) {
        if (readerName == null) {
            return CommonResult.fail(403, "参数错误！");
        }
        HttpSession session = request.getSession();
        Integer readerId = (Integer) session.getAttribute(SessionParameters.READERID);
//        Integer readerId = 1;
        int result = readerTableService.updateReaderInfo(readerName, readerId);
        if (result <= 0) {
            return CommonResult.fail(500, "信息修改失败！");
        }
        return CommonResult.success();
    }

    @PostMapping("/manager/addReader")
    public CommonResult addReader(String name, String phone) {
        if (name == null || name.length() < 1 || phone == null || phone.length() != PhoneNum.LENGTH) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        ReaderAccountDTO readerAccountDTO = new ReaderAccountDTO();
        readerAccountDTO.setReaderName(name);
        readerAccountDTO.setReaderPhone(phone);
        int addNum = readerTableService.insertReader(readerAccountDTO);
        if (addNum < 1) {
            return CommonResult.fail(HttpStatus.ERROR);
        }
        return CommonResult.success("新增成功");
    }

    @GetMapping("/manager/select/readerAll")
    public CommonResult selectAllReader() {
        List<ReaderAccountDTO> readerAccountDTOS = readerTableService.selectReaderAll();
        if (CollectionUtils.isEmpty(readerAccountDTOS)) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        return CommonResult.success(readerAccountDTOS);
    }

    @GetMapping("/manager/select/readerOne")
    public CommonResult selectReaderById(Integer id) {
        if (id == null || id < 1) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        ReaderAccountDTO readerAccountDTO = readerTableService.queryById(id);
        if (readerAccountDTO == null) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        return CommonResult.success(readerAccountDTO);
    }

    @GetMapping("/manager/delete/reader")
    public CommonResult deleteReader(Integer id) {
        if (id == null || id < 1) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        ReaderAccountDTO readerAccountDTO = readerTableService.queryById(id);
        if (readerAccountDTO == null) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        int deleteNum = readerTableService.deleteById(id);
        if (deleteNum < 1) {
            return CommonResult.fail(HttpStatus.ERROR);
        }
        return CommonResult.success();
    }

}
