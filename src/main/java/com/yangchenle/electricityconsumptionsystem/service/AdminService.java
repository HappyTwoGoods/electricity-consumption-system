package com.yangchenle.electricityconsumptionsystem.service;

import com.yangchenle.electricityconsumptionsystem.dto.AdminDTO;

public interface AdminService {
    /**
     * 通过id查询管理员
     *
     * @param id
     * @return
     */
    AdminDTO selectById(Integer id);

    /**
     * 通过手机号查询
     *
     * @param phone
     * @return
     */
    AdminDTO selectByPhone(String phone);
}
