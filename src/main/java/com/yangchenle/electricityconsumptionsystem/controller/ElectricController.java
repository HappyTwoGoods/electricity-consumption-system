package com.yangchenle.electricityconsumptionsystem.controller;

import com.yangchenle.electricityconsumptionsystem.common.CommonResult;
import com.yangchenle.electricityconsumptionsystem.constant.ElectricType;
import com.yangchenle.electricityconsumptionsystem.constant.SessionParameters;
import com.yangchenle.electricityconsumptionsystem.dto.ElectricDTO;
import com.yangchenle.electricityconsumptionsystem.dto.UserDTO;
import com.yangchenle.electricityconsumptionsystem.emun.HttpStatus;
import com.yangchenle.electricityconsumptionsystem.service.ElectricService;
import com.yangchenle.electricityconsumptionsystem.service.UserService;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class ElectricController {

    @Resource
    private ElectricService electricService;
    @Resource
    private UserService userService;

    /**
     * 查看用电信息
     *
     * @return
     */
    @GetMapping("/user/queryElectric/userId")
    public CommonResult queryByUserId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute(SessionParameters.USERID);
        List<ElectricDTO> electricDTOList = electricService.queryEleByUserId(userId);
        if (CollectionUtils.isEmpty(electricDTOList)) {
            return CommonResult.fail(404, "没有该用户相关记录！");
        }
        return CommonResult.success(electricDTOList);
    }

    @PostMapping("manager/add/electric")
    public CommonResult addElectric(Integer num, Integer type, BigDecimal data) {
        if (num == null || num < 1 || type == null || type < ElectricType.HOEM || type > ElectricType.FACTORY) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        ElectricDTO electricDTO = electricService.selectElectricByNum(num);
        if (electricDTO != null) {
            return CommonResult.fail(403, "电表编号已存在");
        }
        int addNum = electricService.addElectric(num, type, data);
        if (addNum < 1) {
            return CommonResult.fail(HttpStatus.ERROR);
        }
        return CommonResult.success("新增成功");
    }

    @GetMapping("/manager/select/electric")
    public CommonResult selectElectricById(Integer id) {
        if (id == null || id < 1) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        ElectricDTO electricDTO = electricService.selectElectricById(id);
        if (electricDTO == null) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        return CommonResult.success(reElectricData(electricDTO));
    }

    @GetMapping("/manager/select/electricAll")
    public CommonResult selectElectricAll() {
        List<ElectricDTO> electricDTOS = electricService.selectElectricAll();
        if (CollectionUtils.isEmpty(electricDTOS)) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        ArrayList<reElectric> reElectrics = new ArrayList<>();
        for (ElectricDTO electricDTO : electricDTOS) {
            reElectrics.add(reElectricData(electricDTO));
        }
        return CommonResult.success(reElectrics);
    }

    @GetMapping("/manager/delete/electric")
    public CommonResult deleteElectric(Integer id) {
        if (id == null || id < 1) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        ElectricDTO electricDTO = electricService.selectElectricById(id);
        if (electricDTO == null) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        int deleteNum = electricService.deleteElectricById(id);
        if (deleteNum < 1) {
            return CommonResult.fail(HttpStatus.ERROR);
        }
        return CommonResult.success("删除成功");
    }

    @GetMapping("/manager/select/electricByNum")
    public CommonResult selectByNum(Integer num) {
        if (num == null || num < 1) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        ElectricDTO electricDTO = electricService.selectElectricByNum(num);
        if (electricDTO == null) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        return CommonResult.success(reElectricData(electricDTO));
    }

    /**
     * 动态查询用户电表信息
     *
     * @param electricNum
     * @param type
     * @param state
     * @param request
     * @return
     */
    @GetMapping("/user/query/byCondition")
    public CommonResult queryByCondition(@RequestParam(required = false, defaultValue = "")Integer electricNum,
                                         @RequestParam(required = false, defaultValue = "")Integer type,
                                         @RequestParam(required = false, defaultValue = "")Integer state,
                                         HttpServletRequest request){
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute(SessionParameters.USERID);
        List<ElectricDTO> electricDTOList = electricService.queryByCondition(userId,electricNum,type,state);
        if (CollectionUtils.isEmpty(electricDTOList)){
            return CommonResult.fail(404,"没有相关信息!");
        }
        return CommonResult.success(electricDTOList);
    }

    private reElectric reElectricData(ElectricDTO electricDTO) {
        if (electricDTO == null) {
            return null;
        }
        reElectric reElectric = new reElectric();
        BeanUtils.copyProperties(electricDTO, reElectric);
        if (electricDTO.getUserId() != null) {
            UserDTO userDTO = userService.queryById(electricDTO.getUserId());
            reElectric.setUsername(userDTO.getUserName());
        } else {
            reElectric.setUsername("暂无绑定");
        }
        return reElectric;
    }

    @Data
    private class reElectric {

        private Integer electricId;

        private Integer num;

        private Integer type;

        private BigDecimal lastData;

        private Integer userId;

        private String username;

        private BigDecimal money;

        private Integer state;

        private Date addTime;

        private Date updateTime;
    }
}
