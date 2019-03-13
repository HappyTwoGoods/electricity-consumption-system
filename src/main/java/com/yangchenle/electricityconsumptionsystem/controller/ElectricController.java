package com.yangchenle.electricityconsumptionsystem.controller;

import com.yangchenle.electricityconsumptionsystem.common.CommonResult;
import com.yangchenle.electricityconsumptionsystem.constant.ElectricType;
import com.yangchenle.electricityconsumptionsystem.dto.ElectricDTO;
import com.yangchenle.electricityconsumptionsystem.emun.HttpStatus;
import com.yangchenle.electricityconsumptionsystem.service.ElectricService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@RestController
public class ElectricController {

    @Resource
    private ElectricService electricService;

    /**
     * 查看用电信息
     *
     * @return
     */
    @GetMapping("/user/queryElectric/userId")
    public CommonResult queryByUserId(){
        Integer userId = 1;
        List<ElectricDTO> electricDTOList = electricService.queryEleByUserId(userId);
        if (CollectionUtils.isEmpty(electricDTOList)){
            return CommonResult.fail(404,"没有该用户相关记录！");
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
        return CommonResult.success(electricDTO);
    }

    @GetMapping("/manager/select/electricAll")
    public CommonResult selectElectricAll() {
        List<ElectricDTO> electricDTOS = electricService.selectElectricAll();
        if (CollectionUtils.isEmpty(electricDTOS)) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        return CommonResult.success(electricDTOS);
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
}
