package com.yangchenle.electricityconsumptionsystem.controller;

import com.yangchenle.electricityconsumptionsystem.common.CommonResult;
import com.yangchenle.electricityconsumptionsystem.constant.BossAccount;
import com.yangchenle.electricityconsumptionsystem.constant.PaymentState;
import com.yangchenle.electricityconsumptionsystem.dto.ElectricDTO;
import com.yangchenle.electricityconsumptionsystem.dto.PaymentRecordDTO;
import com.yangchenle.electricityconsumptionsystem.service.ElectricService;
import com.yangchenle.electricityconsumptionsystem.service.PaymentRecordService;
import com.yangchenle.electricityconsumptionsystem.service.UserService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
    public CommonResult queryPaymentRecord(){
        Integer userId = 1;
        List<ElectricDTO> electricDTOList = electricService.queryEleByUserId(userId);
        if (CollectionUtils.isEmpty(electricDTOList)){
            return CommonResult.fail(404,"没有该用户电表记录");
        }
        Map<Integer,List<PaymentRecordDTO>> paymentMap = new HashMap<>();
        for (ElectricDTO electric: electricDTOList) {
            List<PaymentRecordDTO> paymentRecordDTOS = paymentRecordService.queryPayment(electric.getElectricId(), PaymentState.UNPAID);
            if (CollectionUtils.isEmpty(paymentRecordDTOS)){
                return CommonResult.success("用户没有未缴费记录！");
            }
            paymentMap.put(electric.getElectricId(),paymentRecordDTOS);
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
                                 Integer electricId){
        if (paymentMethod == null || electricId == null){
            return CommonResult.fail(403,"参数错误！");
        }
        int result = paymentRecordService.updatePayment(paymentMethod,new BigDecimal(0),PaymentState.PAID,electricId);
        int payResult = userService.payById(money, 1);
        if (result <= 0 && payResult <= 0){
            return CommonResult.fail("缴费失败！");
        }
        return CommonResult.success();
    }
}
