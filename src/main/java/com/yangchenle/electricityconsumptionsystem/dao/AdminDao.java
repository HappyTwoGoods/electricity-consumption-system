package com.yangchenle.electricityconsumptionsystem.dao;

import com.yangchenle.electricityconsumptionsystem.entity.AdminEntity;
import org.apache.ibatis.annotations.Param;

public interface AdminDao {
    /**
     * 通过id查询管理员
     * @param id
     * @return
     */
    AdminEntity selectById(@Param("id")Integer id);

    /**
     * 通过手机号查询
     * @param phone
     * @return
     */
    AdminEntity selectByPhone(@Param("phone") String phone);
}
