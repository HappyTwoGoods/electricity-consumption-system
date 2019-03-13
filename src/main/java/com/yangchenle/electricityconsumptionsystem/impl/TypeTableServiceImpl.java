package com.yangchenle.electricityconsumptionsystem.impl;

import com.yangchenle.electricityconsumptionsystem.constant.ElectricType;
import com.yangchenle.electricityconsumptionsystem.dao.TypeTableDao;
import com.yangchenle.electricityconsumptionsystem.dto.TypeTableDTO;
import com.yangchenle.electricityconsumptionsystem.entity.TypeTableEntity;
import com.yangchenle.electricityconsumptionsystem.service.TypeTableService;
import com.yangchenle.electricityconsumptionsystem.util.BeansListUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class TypeTableServiceImpl implements TypeTableService {
    @Resource
    private TypeTableDao typeTableDao;

    @Override
    public TypeTableDTO selectByNum(Integer num) {
        if (num == null || num < ElectricType.HOEM || num > ElectricType.FACTORY) {
            return null;
        }
        TypeTableEntity typeTableEntity = typeTableDao.selectTypeByNum(num);
        if (typeTableEntity == null) {
            return null;
        }
        TypeTableDTO typeTableDTO = new TypeTableDTO();
        BeanUtils.copyProperties(typeTableEntity, typeTableDTO);
        return typeTableDTO;
    }

    @Override
    public int updateTypeTableByNum(BigDecimal price, Integer num) {
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0 ||
                num == null || num < ElectricType.HOEM || num > ElectricType.FACTORY) {
            return 0;
        }
        return typeTableDao.updateTypeTableByNum(price, num);
    }

    @Override
    public List<TypeTableDTO> selectTypeAll() {
        List<TypeTableEntity> typeTableEntities = typeTableDao.selectTypeAll();
        if (CollectionUtils.isEmpty(typeTableEntities)) {
            return new ArrayList<>();
        }
        return BeansListUtils.copyListProperties(typeTableEntities, TypeTableDTO.class);
    }
}
