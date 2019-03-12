package com.yangchenle.electricityconsumptionsystem.impl;

import com.yangchenle.electricityconsumptionsystem.dao.ElectricDao;
import com.yangchenle.electricityconsumptionsystem.dto.ElectricDTO;
import com.yangchenle.electricityconsumptionsystem.entity.ElectricEntity;
import com.yangchenle.electricityconsumptionsystem.service.ElectricService;
import com.yangchenle.electricityconsumptionsystem.util.BeansListUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class ElectricServiceImpl implements ElectricService {

    @Resource
    private ElectricDao electricDao;

    @Override
    public List<ElectricDTO> queryEleById(Integer userId) {
        if (userId == null){
            return null;
        }
        List<ElectricEntity> electricEntityList = electricDao.queryEleById(userId);
        return BeansListUtils.copyListProperties(electricEntityList,ElectricDTO.class);
    }
}
