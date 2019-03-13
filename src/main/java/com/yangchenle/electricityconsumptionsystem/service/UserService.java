package com.yangchenle.electricityconsumptionsystem.service;

import com.yangchenle.electricityconsumptionsystem.dto.UserDTO;

public interface UserService {

    /**
     * 根据手机号登录
     *
     * @param userPhone
     * @return
     */
    UserDTO userLogin(String userPhone);

    /**
     * 用户注册
     *
     * @param userInfo
     * @return
     */
    Integer insertUser(UserDTO userInfo);

    /**
     * 根据用户Id查询用户
     *
     * @param userId
     * @return
     */
    UserDTO queryById(Integer userId);

    /**
     * 动态修改用户信息
     *
     * @param userName
     * @param userAccount
     * @param userAddress
     * @param userId
     * @return
     */
    int updateUserInfo(String userName, Integer userAccount, String userAddress, Integer userId);
}
