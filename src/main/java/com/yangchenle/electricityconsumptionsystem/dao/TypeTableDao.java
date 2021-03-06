package com.yangchenle.electricityconsumptionsystem.dao;

import com.yangchenle.electricityconsumptionsystem.dto.TypeTableDTO;
import com.yangchenle.electricityconsumptionsystem.entity.TypeTableEntity;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 电类型操作
 */
public interface TypeTableDao {
    /**
     * 根据类型编号查找类型
     * @param num
     * @return
     */
    TypeTableEntity selectTypeByNum(@Param("num") Integer num);

    /**
     * 修改价格
     * @param price
     * @param num
     * @return
     */
    int updateTypeTableByNum(@Param("price") BigDecimal price,
                             @Param("num") Integer num);

    /**
     * 查询所有类型
     * @return
     */
    List<TypeTableEntity> selectTypeAll();

    /**
     *根据id查询
     *
     * @param typeId
     * @return
     */
    TypeTableEntity selectById(@Param("typeId")Integer typeId);

}
