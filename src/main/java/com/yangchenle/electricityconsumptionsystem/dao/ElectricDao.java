package com.yangchenle.electricityconsumptionsystem.dao;

import com.yangchenle.electricityconsumptionsystem.entity.ElectricEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ElectricDao {

    /**
     * 根据用户id查询用户电量信息
     *
     * @param userId
     * @return
     */
    List<ElectricEntity> queryEleById(@Param("userId") Integer userId);
}
