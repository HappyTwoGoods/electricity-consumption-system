package com.yangchenle.electricityconsumptionsystem.service;

import com.yangchenle.electricityconsumptionsystem.dto.PaymentRecordDTO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentRecordService {

    /**
     * 根据electricId查询未缴费记录
     *
     * @param electricId
     * @param paymentState
     * @return
     */
    List<PaymentRecordDTO> queryPayment(Integer electricId, Integer paymentState);

    /**
     * 缴费操作
     *
     * @param paymentMethd
     * @param money
     * @param paymentState
     * @param electricId
     * @return
     */
    int updatePayment(@Param("paymentMethod") Integer paymentMethd,
                      @Param("money")BigDecimal money,
                      @Param("PaymentState") Integer paymentState,
                      @Param("electricId") Integer electricId);
}
