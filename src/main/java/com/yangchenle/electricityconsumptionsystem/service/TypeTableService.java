package com.yangchenle.electricityconsumptionsystem.service;

import com.yangchenle.electricityconsumptionsystem.dao.TypeTableDao;
import com.yangchenle.electricityconsumptionsystem.dto.TypeTableDTO;

import java.math.BigDecimal;
import java.util.List;

public interface TypeTableService {
    /**
     * 根据类型编号查找类型
     * @param num
     * @return
     */
    TypeTableDTO selectByNum(Integer num);

    /**
     * 修改价格
     * @param price
     * @param num
     * @return
     */
    int updateTypeTableByNum(BigDecimal price, Integer num);

    /**
     * 查询所有类别
     * @return
     */
    List<TypeTableDTO> selectTypeAll();
}
