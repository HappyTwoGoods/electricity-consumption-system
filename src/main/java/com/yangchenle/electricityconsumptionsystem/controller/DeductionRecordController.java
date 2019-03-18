package com.yangchenle.electricityconsumptionsystem.controller;

import com.yangchenle.electricityconsumptionsystem.common.CommonResult;
import com.yangchenle.electricityconsumptionsystem.dto.DeductionRecordDTO;
import com.yangchenle.electricityconsumptionsystem.dto.ElectricDTO;
import com.yangchenle.electricityconsumptionsystem.dto.MoneyAndConsumptionSumDTO;
import com.yangchenle.electricityconsumptionsystem.dto.UserDTO;
import com.yangchenle.electricityconsumptionsystem.emun.HttpStatus;
import com.yangchenle.electricityconsumptionsystem.service.DeductionService;
import com.yangchenle.electricityconsumptionsystem.service.ElectricService;
import com.yangchenle.electricityconsumptionsystem.service.UserService;
import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class DeductionRecordController {
    @Resource
    private DeductionService deductionService;
    @Resource
    private ElectricService electricService;
    @Resource
    private UserService userService;

    @GetMapping("/manager/select/deductionAll")
    public CommonResult selectAll() {
        List<DeductionRecordDTO> recordDTOS = deductionService.selectDeductionRecordAll();
        if (CollectionUtils.isEmpty(recordDTOS)) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        List<ResultDeduction> result = createRecordResult(recordDTOS);
        return CommonResult.success(result);
    }

    @GetMapping("/manager/select/deduction")
    public CommonResult selectDeduction(@RequestParam(required = false) Integer electricNum,
                                        @RequestParam(required = false) String start,
                                        @RequestParam(required = false) String end) {
        Integer electricId = null;
        Date startTime = null;
        Date endTime = null;
        if (electricNum != null) {
            ElectricDTO electricDTO = electricService.selectElectricByNum(electricNum);
            if (electricDTO != null) {
                electricId = electricDTO.getElectricId();
            } else {
                return CommonResult.fail(404, "电表编号不存在");
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (!StringUtils.isEmpty(start)) {
                startTime = sdf.parse(start);
            }
            if (!StringUtils.isEmpty(end)) {
                endTime = sdf.parse(end);
                endTime = new DateTime(endTime).plusDays(1).toDate();
            }
            List<DeductionRecordDTO> deductionRecordDTOS = deductionService.selectDeductionRecord(electricId, startTime, endTime);
            if (CollectionUtils.isEmpty(deductionRecordDTOS)) {
                return CommonResult.fail(HttpStatus.NOT_FOUND);
            }
            List<ResultDeduction> result = createRecordResult(deductionRecordDTOS);
            return CommonResult.success(result);
        } catch (ParseException e) {
            System.out.println("时间格式错误");
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
    }

    @GetMapping("/manager/select/sum")
    public CommonResult selectSumPriceAndConsumption(@RequestParam(required = false) Integer electricNum,
                                                     @RequestParam(required = false) String start,
                                                     @RequestParam(required = false) String end) {
        Integer electricId = null;
        Date startTime = null;
        Date endTime = null;
        if (electricNum != null) {
            ElectricDTO electricDTO = electricService.selectElectricByNum(electricNum);
            if (electricDTO == null) {
                return CommonResult.fail(403, "电表编号不存在");
            } else {
                electricId = electricDTO.getElectricId();
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (!StringUtils.isEmpty(start)) {
                startTime = sdf.parse(start);
            }
            if (!StringUtils.isEmpty(end)) {
                endTime = sdf.parse(end);
                endTime = new DateTime(endTime).plusDays(1).toDate();
            }
            List<MoneyAndConsumptionSumDTO> moneyAndConsumptionSumDTOS = deductionService.selectSum(electricId, startTime, endTime);
            if (CollectionUtils.isEmpty(moneyAndConsumptionSumDTOS)) {
                return CommonResult.fail(HttpStatus.NOT_FOUND);
            }
            List<ResultSum> sum = createSum(moneyAndConsumptionSumDTOS);
            return CommonResult.success(sum);
        } catch (ParseException e) {
            System.out.println("时间格式错误");
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
    }

    private List<ResultDeduction> createRecordResult(List<DeductionRecordDTO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        ArrayList<ResultDeduction> resultDeductions = new ArrayList<>();
        for (DeductionRecordDTO deductionRecordDTO : list) {
            if (deductionRecordDTO != null) {
                ResultDeduction resultDeduction = new ResultDeduction();
                BeanUtils.copyProperties(deductionRecordDTO, resultDeduction);
                ElectricDTO electricDTO = electricService.selectElectricById(deductionRecordDTO.getElectricId());
                if (electricDTO != null) {
                    resultDeduction.setElectricNum(electricDTO.getNum());
                    if (electricDTO.getUserId() != null) {
                        resultDeduction.setUserId(electricDTO.getUserId());
                        UserDTO userDTO = userService.queryById(electricDTO.getUserId());
                        resultDeduction.setUsername(userDTO.getUserName());
                    } else {
                        resultDeduction.setUsername("暂无绑定");
                    }
                    resultDeductions.add(resultDeduction);
                }
            }
        }
        return resultDeductions;
    }

    private List<ResultSum> createSum(List<MoneyAndConsumptionSumDTO> list) {
        ArrayList<ResultSum> resultSums = new ArrayList<>();
        if (CollectionUtils.isEmpty(list)) {
            return resultSums;
        }
        for (MoneyAndConsumptionSumDTO moneyAndConsumptionSumDTO : list) {
            if (moneyAndConsumptionSumDTO != null) {
                ResultSum resultSum = new ResultSum();
                BeanUtils.copyProperties(moneyAndConsumptionSumDTO, resultSum);
                ElectricDTO electricDTO = electricService.selectElectricById(moneyAndConsumptionSumDTO.getElectricId());
                if (electricDTO != null) {
                    resultSum.setElectricNum(electricDTO.getNum());
                    if (electricDTO.getUserId() != null) {
                        resultSum.setUserId(electricDTO.getUserId());
                        UserDTO userDTO = userService.queryById(electricDTO.getUserId());
                        resultSum.setUsername(userDTO.getUserName());
                    } else {
                        resultSum.setUsername("暂未绑定");
                    }
                    resultSums.add(resultSum);
                }
            }
        }
        return resultSums;
    }

    @Data
    private class ResultSum {
        private Integer electricId;
        private Integer electricNum;
        private Integer userId;
        private String username;
        private BigDecimal money;
        private BigDecimal consumption;
    }

    @Data
    private class ResultDeduction {
        private Integer id;
        private Integer electricId;
        private Integer electricNum;
        private Integer userId;
        private String username;
        private BigDecimal electricConsumption;
        private BigDecimal money;
        private Date addTime;
        private Date updateTime;
    }
}
