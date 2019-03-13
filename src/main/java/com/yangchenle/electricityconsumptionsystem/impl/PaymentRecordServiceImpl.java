package com.yangchenle.electricityconsumptionsystem.impl;

import com.yangchenle.electricityconsumptionsystem.dto.PaymentRecordDTO;
import com.yangchenle.electricityconsumptionsystem.entity.PaymentRecordEntity;
import com.yangchenle.electricityconsumptionsystem.service.PaymentRecordService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PaymentRecordServiceImpl implements PaymentRecordService {
    @Override
    public List<PaymentRecordDTO> queryPayment(Integer electricId, Integer paymentState) {
        if (electricId == null || paymentState == null){
            return new ArrayList<>();
        }
        List<PaymentRecordEntity> paymentRecordEntities ;
        return null;
    }

    @Override
    public int updatePayment(Integer paymentMethd, BigDecimal money, Integer paymentState, Integer electricId) {
        return 0;
    }
}
