package com.yangchenle.electricityconsumptionsystem.service;

import com.yangchenle.electricityconsumptionsystem.dto.UserDTO;

public interface UserService {

    /**
     * 根据手机号登录
     *
     * @param readerPhone
     * @return
     */
    UserDTO userLogin(String readerPhone);
}
