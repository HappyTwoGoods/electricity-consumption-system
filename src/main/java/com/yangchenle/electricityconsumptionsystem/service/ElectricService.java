package com.yangchenle.electricityconsumptionsystem.service;

import com.yangchenle.electricityconsumptionsystem.dto.ElectricDTO;

import java.util.List;

public interface ElectricService {

    /**
     * 根据用户id查询用户用电信息
     *
     * @param userId
     * @return
     */
    List<ElectricDTO> queryEleById(Integer userId);
}
