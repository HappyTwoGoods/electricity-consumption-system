package com.yangchenle.electricityconsumptionsystem.controller;

import com.yangchenle.electricityconsumptionsystem.common.CommonResult;
import com.yangchenle.electricityconsumptionsystem.constant.ElectricState;
import com.yangchenle.electricityconsumptionsystem.constant.PaymentState;
import com.yangchenle.electricityconsumptionsystem.constant.SessionParameters;
import com.yangchenle.electricityconsumptionsystem.dto.*;
import com.yangchenle.electricityconsumptionsystem.emun.HttpStatus;
import com.yangchenle.electricityconsumptionsystem.service.*;
import com.yangchenle.electricityconsumptionsystem.util.ElectricUtil;
import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class PaymentRecordController {

    @Resource
    private PaymentRecordService paymentRecordService;

    @Resource
    private ElectricService electricService;

    @Resource
    private UserService userService;
    @Resource
    private ElectricUtil electricUtil;

    /**
     * 用户查看自己未缴费记录
     *
     * @return
     */
    @GetMapping("/user/query/payment")
    public CommonResult queryPaymentRecord(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute(SessionParameters.USERID);
        List<ElectricDTO> electricDTOList = electricService.queryEleByUserId(userId);
        if (CollectionUtils.isEmpty(electricDTOList)) {
            return CommonResult.fail(404, "没有该用户电表记录");
        }
        Map<String, List<PaymentRecordDTO>> paymentMap = new HashMap<>();
        for (ElectricDTO electric : electricDTOList) {
            List<PaymentRecordDTO> paymentRecordDTOS = paymentRecordService.queryPayment(electric.getElectricId(), PaymentState.UNPAID);
            if (CollectionUtils.isEmpty(paymentRecordDTOS)) {
                continue;
            }
            paymentMap.put("paymentInfo", paymentRecordDTOS);
        }
        return CommonResult.success(paymentMap);
    }

    @Resource
    private TypeTableService typeTableService;

    @Resource
    private DeductionService deductionService;

//    /**
//     * 用户查看自己未缴费记录
//     *
//     * @return
//     */
////    @GetMapping("/user/query/payment")
//    public CommonResult queryPaymentRecord(HttpServletRequest request){
//        HttpSession session = request.getSession();
//        Integer userId = (Integer) session.getAttribute(SessionParameters.USERID);
//        List<ElectricDTO> electricDTOList = electricService.queryEleByUserId(userId);
//        if (CollectionUtils.isEmpty(electricDTOList)){
//            return CommonResult.fail(404,"没有该用户电表记录");
//        }
//        Map<String,List<PaymentRecordDTO>> paymentMap = new HashMap<>();
//        for (ElectricDTO electric: electricDTOList) {
//            List<PaymentRecordDTO> paymentRecordDTOS = paymentRecordService.queryPayment(electric.getElectricId(), PaymentState.UNPAID);
//            if (CollectionUtils.isEmpty(paymentRecordDTOS)){
//                continue;
//            }
//            paymentMap.put("paymentInfo",paymentRecordDTOS);
//        }
//        return CommonResult.success(paymentMap);
//    }

    /**
     * 缴费
     *
     * @param paymentMethod
     * @param money
     * @param electricId
     * @return
     */
    @GetMapping("/user/payMoney")
    public CommonResult payMoney(Integer paymentMethod, BigDecimal money,
                                 Integer electricId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute(SessionParameters.USERID);
        if (paymentMethod == null || electricId == null) {
            return CommonResult.fail(403, "参数错误！");
        }
        UserDTO userDTO = userService.queryById(userId);
        BigDecimal price = userDTO.getPrice();
        if (price.compareTo(money) < 0) {
            return CommonResult.fail(500, "余额不足！");
        }
        int userPayResult = userService.payById(price.subtract(money), userId);
        if (userPayResult <= 0) {
            return CommonResult.fail(500, "支付失败！");
        }
        UserDTO userDTO1 = userService.queryById(1);
        BigDecimal bossMoney = userDTO1.getPrice();
        BigDecimal bossMoneyNum = bossMoney.add(money);
        int payResult = userService.payById(bossMoneyNum, 1);
        if (payResult <= 0) {
            return CommonResult.fail(500, "转账失败！");
        }
        int result = paymentRecordService.updatePayment(paymentMethod, new BigDecimal(0), PaymentState.PAID, electricId);
        if (result <= 0) {
            return CommonResult.fail(500, "缴费失败！");
        }
        ElectricDTO electricDTO = electricService.selectElectricById(electricId);
        BigDecimal elePrice = electricDTO.getMoney();
        BigDecimal priceNum = elePrice.add(money);
        int r = priceNum.compareTo(BigDecimal.ZERO);
        if (r < 0) {
            int payResults = electricService.updateElectric(null, priceNum, ElectricState.STOP, electricId);
            if (payResults <= 0) {
                return CommonResult.fail(500, "更改电表信息失败！");
            }
            return CommonResult.success();
        }
        int payResulted = electricService.updateElectric(null, priceNum, ElectricState.NORMAL, electricId);
        if (payResulted <= 0) {
            return CommonResult.fail(500, "更改电表信息失败！");
        }
        Integer type = electricDTO.getType();
        TypeTableDTO typeTableDTO = typeTableService.selectById(type);
        BigDecimal typePrice = typeTableDTO.getPrice();
        BigDecimal consumption = money.subtract(typePrice);
//        int data = deductionService.
        return CommonResult.success();
    }

    @GetMapping("/manager/select/payRecord")
    public CommonResult selectPayRecord(@RequestParam(required = false) Integer electricNum,
                                        @RequestParam(required = false) String start,
                                        @RequestParam(required = false) String end) {
        Integer electricId = electricUtil.isExist(electricNum);
        if (electricId != null && electricId < 0) {
            return CommonResult.fail(403, "电表编号不存在");
        }
        Date startTime = null;
        Date endTime = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (!StringUtils.isEmpty(start)) {
                startTime = sdf.parse(start);
            }
            if (!StringUtils.isEmpty(end)) {
                endTime = sdf.parse(end);
                endTime = new DateTime(endTime).plusDays(1).toDate();
            }
            List<PaymentRecordDTO> recordDTOS = paymentRecordService.selectPayRecordAll(electricId, startTime, endTime);
            if (CollectionUtils.isEmpty(recordDTOS)) {
                return CommonResult.fail(HttpStatus.NOT_FOUND);
            }
            return CommonResult.success(createRePayRecord(recordDTOS));
        } catch (ParseException e) {
            System.out.println("时间格式错误");
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
    }

    @GetMapping("/manager/select/paySumMoney")
    public CommonResult selectPaySumMoney(@RequestParam(required = false) Integer electricNum,
                                          @RequestParam(required = false) String start,
                                          @RequestParam(required = false) String end) {
        Integer electricId = electricUtil.isExist(electricNum);
        if (electricId != null && electricId < 0) {
            return CommonResult.fail(403, "电表编号不存在");
        }
        Date startTime = null;
        Date endTime = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (!StringUtils.isEmpty(start)) {
                startTime = sdf.parse(start);
            }
            if (!StringUtils.isEmpty(end)) {
                endTime = sdf.parse(end);
                endTime = new DateTime(endTime).plusDays(1).toDate();
            }
            List<PaySumMoneyDTO> sumMoneyDTOS = paymentRecordService.selectSum(electricId, startTime, endTime);
            if (CollectionUtils.isEmpty(sumMoneyDTOS)) {
                return CommonResult.fail(HttpStatus.NOT_FOUND);
            }
            return CommonResult.success(createRePaySum(sumMoneyDTOS));
        } catch (ParseException e) {
            System.out.println("时间格式错误");
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
    }

    private List<rePayRecord> createRePayRecord(List<PaymentRecordDTO> list) {
        List<rePayRecord> rePayRecords = new ArrayList<>();
        if (CollectionUtils.isEmpty(list)) {
            return rePayRecords;
        }
        for (PaymentRecordDTO pay : list) {
            rePayRecord rePayRecord = new rePayRecord();
            BeanUtils.copyProperties(pay, rePayRecord);
            ElectricDTO electricDTO = electricService.selectElectricById(pay.getElectricId());
            if (electricDTO != null) {
                rePayRecord.setElectricNum(electricDTO.getNum());
                if (electricDTO.getUserId() != null) {
                    UserDTO userDTO = userService.queryById(electricDTO.getUserId());
                    if (userDTO != null) {
                        rePayRecord.setUsername(userDTO.getUserName());
                    } else {
                        rePayRecord.setUsername("信息有误");
                    }
                } else {
                    rePayRecord.setUsername("暂未绑定");
                }
                rePayRecords.add(rePayRecord);
            }
        }
        return rePayRecords;
    }

    private List<rePaySum> createRePaySum(List<PaySumMoneyDTO> list) {
        List<rePaySum> reList = new ArrayList<>();
        if (CollectionUtils.isEmpty(list)) {
            return reList;
        }
        for (PaySumMoneyDTO paySumMoneyDTO : list) {
            rePaySum rePaySum = new rePaySum();
            BeanUtils.copyProperties(paySumMoneyDTO, rePaySum);
            ElectricDTO electricDTO = electricService.selectElectricById(paySumMoneyDTO.getElectricId());
            if (electricDTO != null) {
                rePaySum.setElectricNum(electricDTO.getNum());
                if (electricDTO.getUserId() != null) {
                    UserDTO userDTO = userService.queryById(electricDTO.getUserId());
                    if (userDTO == null) {
                        rePaySum.setUsername("数据有误");
                    } else {
                        rePaySum.setUserId(electricDTO.getUserId());
                        rePaySum.setUsername(userDTO.getUserName());
                    }
                } else {
                    rePaySum.setUsername("暂未绑定");
                }
                reList.add(rePaySum);
            }
        }
        return reList;
    }

    @Data
    private class rePaySum {
        Integer electricId;
        private Integer electricNum;
        private Integer userId;
        private String username;
        BigDecimal money;
    }

    @Data
    private class rePayRecord {
        private Integer paymentId;
        private Integer electricId;
        private Integer electricNum;
        private String username;
        private Integer paymentMethod;
        private Integer paymentState;
        private BigDecimal money;
        private Date addTime;
    }
}
