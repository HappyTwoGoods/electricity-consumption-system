package com.yangchenle.electricityconsumptionsystem.service;

import com.yangchenle.electricityconsumptionsystem.dto.ElectricDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ElectricService {

    /**
     * 根据用户id查询用户用电信息
     *
     * @param userId
     * @return
     */
    List<ElectricDTO> queryEleByUserId(Integer userId);

    /**
     * 新增电表
     *
     * @param type
     * @param data
     * @return
     */
    int addElectric(Integer type, BigDecimal data);

    /**
     * 动态修改电表的余额||最后一次抄表数据||电表状态
     *
     * @param lastData
     * @param money
     * @param state
     * @param id
     * @return
     */
    int updateElectric(BigDecimal lastData,
                       BigDecimal money,
                       Integer state, Integer id);

    /**
     * 删除电表
     *
     * @param id
     * @return
     */
    int deleteElectricById(Integer id);

    /**
     * 根据id查电表
     *
     * @param id
     * @return
     */
    ElectricDTO selectElectricById(Integer id);
}
