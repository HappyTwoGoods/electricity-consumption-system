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
    public UserDTO userLogin(String readerPhone) {
        if (readerPhone == null){
            return null;
        }
        UserEntity userEntity = userDao.userLogin(readerPhone);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userEntity,userDTO);
        return userDTO;
    }
}
