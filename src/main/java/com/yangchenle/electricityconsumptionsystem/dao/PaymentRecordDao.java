package com.yangchenle.electricityconsumptionsystem.dao;

import com.yangchenle.electricityconsumptionsystem.entity.ElectricEntity;
import com.yangchenle.electricityconsumptionsystem.entity.PaymentRecordEntity;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentRecordDao {

    /**
     * 根据electricId查询未缴费记录
     *
     * @param electricId
     * @param paymentState
     * @return
     */
    List<PaymentRecordEntity> queryPayment(@Param("electricId") Integer electricId,
                                           @Param("paymentState") Integer paymentState);

    /**
     * 缴费操作
     *
     * @param paymentMethod
     * @param money
     * @param paymentState
     * @param electricId
     * @return
     */
    int updatePayment(@Param("paymentMethod") Integer paymentMethod,
                      @Param("money")BigDecimal money,
                      @Param("paymentState") Integer paymentState,
                      @Param("electricId") Integer electricId);
}
