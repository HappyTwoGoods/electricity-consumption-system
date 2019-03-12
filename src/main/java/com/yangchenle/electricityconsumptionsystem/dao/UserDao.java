package com.yangchenle.electricityconsumptionsystem.dao;

import com.yangchenle.electricityconsumptionsystem.entity.UserEntity;

public interface UserDao {

    /**
     * 根据手机号登录
     *
     * @param readerPhone
     * @return
     */
    UserEntity userLogin(String readerPhone);
}
