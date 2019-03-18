package com.yangchenle.electricityconsumptionsystem.impl;

import com.yangchenle.electricityconsumptionsystem.constant.PaymentState;
import com.yangchenle.electricityconsumptionsystem.dao.PaymentRecordDao;
import com.yangchenle.electricityconsumptionsystem.dto.PaymentRecordDTO;
import com.yangchenle.electricityconsumptionsystem.entity.PaymentRecordEntity;
import com.yangchenle.electricityconsumptionsystem.service.PaymentRecordService;
import com.yangchenle.electricityconsumptionsystem.util.BeansListUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PaymentRecordServiceImpl implements PaymentRecordService {

    @Resource
    private PaymentRecordDao paymentRecordDao;

    @Override
    public List<PaymentRecordDTO> queryPayment(Integer electricId, Integer paymentState) {
        if (electricId == null || paymentState == null) {
            return null;
        }
        List<PaymentRecordEntity> paymentRecordEntities = paymentRecordDao.queryPayment(electricId, paymentState);
        if (CollectionUtils.isEmpty(paymentRecordEntities)) {
            return null;
        }
        return BeansListUtils.copyListProperties(paymentRecordEntities, PaymentRecordDTO.class);
    }

    @Override
    public int updatePayment(Integer paymentMethd, BigDecimal money, Integer paymentState, Integer electricId) {
        if (paymentMethd == null || money == null || paymentState == null || electricId == null) {
            return 0;
        }
        return paymentRecordDao.updatePayment(paymentMethd, money, paymentState, electricId);
    }

    @Override
    public List<PaymentRecordDTO> selectPayRecordAll(Integer electricId, Date start, Date end) {
        List<PaymentRecordEntity> recordEntities = paymentRecordDao.selectPayRecordAll(electricId, PaymentState.PAID, start, end);
        if (CollectionUtils.isEmpty(recordEntities)) {
            return new ArrayList<>();
        }
        return BeansListUtils.copyListProperties(recordEntities, PaymentRecordDTO.class);
    }
}
