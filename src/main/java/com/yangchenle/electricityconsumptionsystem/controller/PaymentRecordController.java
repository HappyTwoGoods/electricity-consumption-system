package com.yangchenle.electricityconsumptionsystem.controller;

import com.yangchenle.electricityconsumptionsystem.common.CommonResult;
import com.yangchenle.electricityconsumptionsystem.constant.BossAccount;
import com.yangchenle.electricityconsumptionsystem.constant.ElectricState;
import com.yangchenle.electricityconsumptionsystem.constant.PaymentState;
import com.yangchenle.electricityconsumptionsystem.constant.SessionParameters;
import com.yangchenle.electricityconsumptionsystem.dto.ElectricDTO;
import com.yangchenle.electricityconsumptionsystem.dto.PaymentRecordDTO;
import com.yangchenle.electricityconsumptionsystem.dto.UserDTO;
import com.yangchenle.electricityconsumptionsystem.service.ElectricService;
import com.yangchenle.electricityconsumptionsystem.service.PaymentRecordService;
import com.yangchenle.electricityconsumptionsystem.service.UserService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PaymentRecordController {

    @Resource
    private PaymentRecordService paymentRecordService;

    @Resource
    private ElectricService electricService;

    @Resource
    private UserService userService;

    /**
     * 用户查看自己未缴费记录
     *
     * @return
     */
    @GetMapping("/user/query/payment")
    public CommonResult queryPaymentRecord(HttpServletRequest request){
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute(SessionParameters.USERID);
        List<ElectricDTO> electricDTOList = electricService.queryEleByUserId(userId);
        if (CollectionUtils.isEmpty(electricDTOList)){
            return CommonResult.fail(404,"没有该用户电表记录");
        }
        Map<String,List<PaymentRecordDTO>> paymentMap = new HashMap<>();
        for (ElectricDTO electric: electricDTOList) {
            List<PaymentRecordDTO> paymentRecordDTOS = paymentRecordService.queryPayment(electric.getElectricId(), PaymentState.UNPAID);
            if (CollectionUtils.isEmpty(paymentRecordDTOS)){
                continue;
            }
            paymentMap.put("paymentInfo",paymentRecordDTOS);
        }
        return CommonResult.success(paymentMap);
    }

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
                                 Integer electricId, HttpServletRequest request){
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute(SessionParameters.USERID);
        if (paymentMethod == null || electricId == null){
            return CommonResult.fail(403,"参数错误！");
        }
        UserDTO userDTO = userService.queryById(userId);
        BigDecimal price = userDTO.getPrice();
        if (price.compareTo(money) < 0 ){
            return CommonResult.fail(500,"余额不足！");
        }
        int userPayResult = userService.payById(price.subtract(money),userId);
        if (userPayResult <= 0){
            return CommonResult.fail(500,"支付失败！");
        }
        UserDTO userDTO1 = userService.queryById(1);
        BigDecimal bossMoney = userDTO1.getPrice();
        BigDecimal bossMoneyNum = bossMoney.add(money);
        int payResult = userService.payById(bossMoneyNum, 1);
        if (payResult <= 0){
            return CommonResult.fail(500,"转账失败！");
        }
        int result = paymentRecordService.updatePayment(paymentMethod,new BigDecimal(0),PaymentState.PAID,electricId);
        if (result <= 0){
            return CommonResult.fail(500,"缴费失败！");
        }
        ElectricDTO electricDTO = electricService.selectElectricById(electricId);
        BigDecimal elePrice = electricDTO.getMoney();
        BigDecimal priceNum = elePrice.add(money);
        if (priceNum.compareTo(money) < 0){
            int payResults = electricService.updateElectric(null,priceNum, ElectricState.STOP,electricId);
            if (payResults <= 0){
                return CommonResult.fail(500,"更改电表信息失败！");
            }
        }
        int payResulted = electricService.updateElectric(null,priceNum,ElectricState.NORMAL,electricId);
        if (payResulted <= 0){
            return CommonResult.fail(500,"更改电表信息失败！");
        }
        return CommonResult.success();
    }
}
