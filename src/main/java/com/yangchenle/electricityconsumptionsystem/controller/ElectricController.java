package com.yangchenle.electricityconsumptionsystem.controller;

import com.yangchenle.electricityconsumptionsystem.common.CommonResult;
import com.yangchenle.electricityconsumptionsystem.dto.ElectricDTO;
import com.yangchenle.electricityconsumptionsystem.service.ElectricService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class ElectricController {

    @Resource
    private ElectricService electricService;

    @GetMapping("/user/queryElectric/userId")
    public CommonResult queryByUserId(){
        Integer userId = 1;
        List<ElectricDTO> electricDTOList = electricService.queryEleById(userId);
        if (CollectionUtils.isEmpty(electricDTOList)){
            return CommonResult.fail(404,"没有该用户相关记录！");
        }
        return CommonResult.success(electricDTOList);
    }
}
