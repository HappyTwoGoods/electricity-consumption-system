package com.yangchenle.electricityconsumptionsystem.dao;

import com.yangchenle.electricityconsumptionsystem.entity.ElectricEntity;
import com.yangchenle.electricityconsumptionsystem.entity.PaySumMoneyEntity;
import com.yangchenle.electricityconsumptionsystem.entity.PaymentRecordEntity;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 缴费记录
 */
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
                      @Param("money") BigDecimal money,
                      @Param("paymentState") Integer paymentState,
                      @Param("electricId") Integer electricId);

    /**
     * 根据电表id||支付状态||时间查缴费记录
     *
     * @param electricId
     * @param state
     * @param start
     * @param end
     * @return
     */
    List<PaymentRecordEntity> selectPayRecordAll(@Param("electricId") Integer electricId,
                                                 @Param("state") Integer state,
                                                 @Param("start") Date start,
                                                 @Param("end") Date end);

    /**
     * 根据电表id||支付状态||时间统计缴费金额
     * @param electricId
     * @param state
     * @param start
     * @param end
     * @return
     */
    List<PaySumMoneyEntity> selectSum(@Param("electricId") Integer electricId,
                                      @Param("state") Integer state,
                                      @Param("start") Date start,
                                      @Param("end") Date end);
}
