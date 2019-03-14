package com.yangchenle.electricityconsumptionsystem.dao;

import com.yangchenle.electricityconsumptionsystem.entity.UserEntity;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * 用户信息
 */
public interface UserDao {

    /**
     * 根据手机号登录
     *
     * @param userPhone
     * @return
     */
    UserEntity userLogin(@Param("userPhone") String userPhone);

    /**
     * 用户注册
     *
     * @param userInfo
     * @return
     */
    int insertUser(@Param("userInfo") UserEntity userInfo);

    /**
     * 根据用户id查询用户
     *
     * @param userId
     * @return
     */
    UserEntity queryById(@Param("userId") Integer userId);

    /**
     * 动态修改用户信息
     *
     * @param userName
     * @param userAccount
     * @param userAddress
     * @param userId
     * @return
     */
    int updateUserInfo(@Param("userName") String userName,
                  @Param("userAccount") Integer userAccount,
                  @Param("userAddress") String userAddress,
                  @Param("userId") Integer userId
                  );

    /**
     * 支付
     *
     * @param money
     * @param userId
     * @return
     */
    int payById(@Param("money") BigDecimal money,@Param("userId") Integer userId);

}
