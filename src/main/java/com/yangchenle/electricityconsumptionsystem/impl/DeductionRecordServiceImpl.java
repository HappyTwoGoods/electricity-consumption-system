package com.yangchenle.electricityconsumptionsystem.impl;

import com.yangchenle.electricityconsumptionsystem.dao.DeductionRecordDao;
import com.yangchenle.electricityconsumptionsystem.dto.DeductionRecordDTO;
import com.yangchenle.electricityconsumptionsystem.dto.MoneyAndConsumptionSumDTO;
import com.yangchenle.electricityconsumptionsystem.entity.DeductionRecordEntity;
import com.yangchenle.electricityconsumptionsystem.entity.MoneyAndConsumptionSumEntity;
import com.yangchenle.electricityconsumptionsystem.service.DeductionService;
import com.yangchenle.electricityconsumptionsystem.util.BeansListUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DeductionRecordServiceImpl implements DeductionService {
    @Resource
    private DeductionRecordDao deductionRecordDao;

    @Override
    public DeductionRecordDTO selectDeductionRecordById(Integer id) {
        if (id == null) {
            return null;
        }
        DeductionRecordEntity deductionRecordEntity = deductionRecordDao.selectDeductionRecordById(id);
        if (deductionRecordEntity == null) {
            return null;
        }
        DeductionRecordDTO deductionRecordDTO = new DeductionRecordDTO();
        BeanUtils.copyProperties(deductionRecordEntity, deductionRecordDTO);
        return deductionRecordDTO;
    }

    @Override
    public List<DeductionRecordDTO> selectDeductionRecord(Integer electricId, Date start, Date end) {
        List<DeductionRecordEntity> deductionRecordEntities = deductionRecordDao.selectDeductionRecord(electricId, start, end);
        if (CollectionUtils.isEmpty(deductionRecordEntities)) {
            return new ArrayList<>();
        }
        return BeansListUtils.copyListProperties(deductionRecordEntities, DeductionRecordDTO.class);
    }

    @Override
    public List<DeductionRecordDTO> selectDeductionRecordAll() {
        List<DeductionRecordEntity> deductionRecordEntities = deductionRecordDao.selectDeductionRecordAll();
        if (CollectionUtils.isEmpty(deductionRecordEntities)) {
            return new ArrayList<>();
        }
        return BeansListUtils.copyListProperties(deductionRecordEntities, DeductionRecordDTO.class);
    }

    @Override
    public List<MoneyAndConsumptionSumDTO> selectSum(Integer electricId, Date start, Date end) {
        List<MoneyAndConsumptionSumEntity> sumEntities = deductionRecordDao.selectSum(electricId, start, end);
        if (CollectionUtils.isEmpty(sumEntities)) {
            return new ArrayList<>();
        }
        return BeansListUtils.copyListProperties(sumEntities, MoneyAndConsumptionSumDTO.class);
    }

}
