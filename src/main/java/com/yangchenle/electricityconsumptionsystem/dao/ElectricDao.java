package com.yangchenle.electricityconsumptionsystem.dao;

import com.yangchenle.electricityconsumptionsystem.entity.ElectricEntity;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ElectricDao {

    /**
     * 根据用户id查询用户表信息
     *
     * @param userId
     * @return
     */
    List<ElectricEntity> queryEleById(@Param("userId") Integer userId);

    /**
     * 新增电表
     *
     * @param electricEntity
     * @return
     */
    int addElectric(@Param("data") ElectricEntity electricEntity);

    /**
     * 动态修改电表的余额||最后一次抄表数据||电表状态
     *
     * @param lastData
     * @param money
     * @return
     */
    int updateElectric(@Param("data") BigDecimal lastData,
                       @Param("money") BigDecimal money,
                       @Param("state") Integer state,
                       @Param("id") Integer id);

    /**
     * 删除电表
     *
     * @param id
     * @return
     */
    int deleteElectricById(@Param("id") Integer id);

    /**
     * 根据id查电表
     *
     * @param id
     * @return
     */
    ElectricEntity selectElectricById(@Param("id") Integer id);
}
