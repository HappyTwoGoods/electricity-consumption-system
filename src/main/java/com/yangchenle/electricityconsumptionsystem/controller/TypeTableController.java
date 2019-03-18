package com.yangchenle.electricityconsumptionsystem.controller;

import com.yangchenle.electricityconsumptionsystem.common.CommonResult;
import com.yangchenle.electricityconsumptionsystem.constant.ElectricType;
import com.yangchenle.electricityconsumptionsystem.dto.TypeTableDTO;
import com.yangchenle.electricityconsumptionsystem.emun.HttpStatus;
import com.yangchenle.electricityconsumptionsystem.service.TypeTableService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@RestController
public class TypeTableController {
    @Resource
    private TypeTableService typeTableService;

    @GetMapping("/manager/query/typeAll")
    public CommonResult queryAll() {
        List<TypeTableDTO> typeTableDTOS = typeTableService.selectTypeAll();
        if (CollectionUtils.isEmpty(typeTableDTOS)) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        return CommonResult.success(typeTableDTOS);
    }

    /**
     * 用户查看用电类型信息
     *
     * @return
     */
    @GetMapping("/user/queryType")
    public CommonResult queryType() {
        List<TypeTableDTO> typeTableDTOS = typeTableService.selectTypeAll();
        if (CollectionUtils.isEmpty(typeTableDTOS)) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        return CommonResult.success(typeTableDTOS);
    }

    @GetMapping("/manager/query/typeNum")
    public CommonResult queryTypeByNum(Integer num) {
        if (num == null || num < ElectricType.HOEM || num > ElectricType.FACTORY) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        TypeTableDTO typeTableDTO = typeTableService.selectByNum(num);
        if (typeTableDTO == null) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        return CommonResult.success(typeTableDTO);
    }

    @PostMapping("/manager/update/type")
    public CommonResult updateType(Integer num, BigDecimal price) {
        if (num == null || num < 1 || price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        TypeTableDTO typeTableDTO = typeTableService.selectByNum(num);
        if (typeTableDTO == null) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        int updateNum = typeTableService.updateTypeTableByNum(price, num);
        if (updateNum == 0) {
            return CommonResult.fail(HttpStatus.ERROR);
        }
        return CommonResult.success("修改成功");
    }
}
