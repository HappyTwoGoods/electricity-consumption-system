package com.yangchenle.electricityconsumptionsystem.controller;

import com.yangchenle.electricityconsumptionsystem.common.CommonResult;
import com.yangchenle.electricityconsumptionsystem.dto.DeductionRecordDTO;
import com.yangchenle.electricityconsumptionsystem.dto.MoneyAndConsumptionSumDTO;
import com.yangchenle.electricityconsumptionsystem.emun.HttpStatus;
import com.yangchenle.electricityconsumptionsystem.service.DeductionService;
import lombok.Data;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
public class DeductionRecordController {
    @Resource
    private DeductionService deductionService;

    @GetMapping("/select/deductionAll")
    public CommonResult selectAll() {
        List<DeductionRecordDTO> recordDTOS = deductionService.selectDeductionRecordAll();
        if (CollectionUtils.isEmpty(recordDTOS)) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        return CommonResult.success(recordDTOS);
    }

    @GetMapping("/select/deduction")
    public CommonResult selectDeduction(@RequestParam(required = false) Integer electricId,
                                        @RequestParam(required = false) Date start,
                                        @RequestParam(required = false) Date end) {
        List<DeductionRecordDTO> deductionRecordDTOS = deductionService.selectDeductionRecord(electricId, start, end);
        if (CollectionUtils.isEmpty(deductionRecordDTOS)) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        return CommonResult.success(deductionRecordDTOS);
    }

    @GetMapping("/select/sum")
    public CommonResult selectSumPriceAndConsumption(@RequestParam(required = false) Integer electricId,
                                                     @RequestParam(required = false) Date start,
                                                     @RequestParam(required = false) Date end) {
        List<MoneyAndConsumptionSumDTO> moneyAndConsumptionSumDTOS = deductionService.selectSum(electricId, start, end);
        if (CollectionUtils.isEmpty(moneyAndConsumptionSumDTOS)) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        return CommonResult.success(moneyAndConsumptionSumDTOS);
    }

    @Data
    private class Resultdeduction {
        private Integer id;

        private Integer electricNum;

        private String username;

        private BigDecimal electricConsumption;

        private BigDecimal money;

        private Date addTime;

        private Date updateTime;
    }
}
