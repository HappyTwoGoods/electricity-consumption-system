package com.yangchenle.electricityconsumptionsystem.service;

import com.yangchenle.electricityconsumptionsystem.dto.PaymentRecordDTO;

import java.math.BigDecimal;
import java.util.Date;
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
     * @param paymentMethod
     * @param money
     * @param paymentState
     * @param electricId
     * @return
     */
    int updatePayment(Integer paymentMethod,
                      BigDecimal money,
                      Integer paymentState,
                      Integer electricId);

    /**
     * 根据电表id||支付状态||时间查缴费记录
     * @param electricId
     * @param start
     * @param end
     * @return
     */
    List<PaymentRecordDTO> selectPayRecordAll(Integer electricId, Date start, Date end);

    /**
     * 添加缴费记录
     *
     * @param paymentRecordDTO
     * @return
     */
    int insertRecord(PaymentRecordDTO paymentRecordDTO);

    /**
     * 删除记录
     *
     * @param electricId
     * @return
     */
    int deleteById(Integer electricId);
}
