package com.yangchenle.electricityconsumptionsystem.impl;

import com.yangchenle.electricityconsumptionsystem.dao.UserDao;
import com.yangchenle.electricityconsumptionsystem.dto.UserDTO;
import com.yangchenle.electricityconsumptionsystem.entity.UserEntity;
import com.yangchenle.electricityconsumptionsystem.service.UserService;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;

public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public UserDTO userLogin(String userPhone) {
        if (userPhone == null){
            return null;
        }
        UserEntity userEntity = userDao.userLogin(userPhone);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userEntity,userDTO);
        return userDTO;
    }

    @Override
    public Integer insertUser(UserDTO userInfo) {
        if (userInfo == null){
            return 0;
        }
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userInfo,userEntity);
        return userDao.insertUser(userEntity);
    }

    @Override
    public UserDTO queryById(Integer userId) {
        if (userId == null){
            return null;
        }
        UserEntity userEntity = userDao.queryById(userId);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userEntity,userDTO);
        return userDTO;
    }

    @Override
    public int updateUserInfo(String userName, String userAccount, String userAddress, Integer userId) {
        if (userId == null){
            return 0;
        }
        return userDao.updateUserInfo(userName,userAccount,userAddress,userId);
    }
}
