package com.yangchenle.electricityconsumptionsystem.impl;

import com.yangchenle.electricityconsumptionsystem.dao.AdminDao;
import com.yangchenle.electricityconsumptionsystem.dto.AdminDTO;
import com.yangchenle.electricityconsumptionsystem.entity.AdminEntity;
import com.yangchenle.electricityconsumptionsystem.service.AdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminDao adminDao;

    @Override
    public AdminDTO selectById(Integer id) {
        AdminEntity adminEntity = adminDao.selectById(id);
        if (adminEntity == null) {
            return null;
        }
        AdminDTO adminDTO = new AdminDTO();
        BeanUtils.copyProperties(adminEntity, adminDTO);
        return adminDTO;
    }

    @Override
    public AdminDTO selectByPhone(String phone) {
        AdminEntity adminEntity = adminDao.selectByPhone(phone);
        if (adminEntity == null) {
            return null;
        }
        AdminDTO adminDTO = new AdminDTO();
        BeanUtils.copyProperties(adminEntity, adminDTO);
        return adminDTO;
    }
}
