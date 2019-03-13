package com.yangchenle.electricityconsumptionsystem.impl;

import com.yangchenle.electricityconsumptionsystem.dao.ReaderTableDao;
import com.yangchenle.electricityconsumptionsystem.dto.ReaderAccountDTO;
import com.yangchenle.electricityconsumptionsystem.entity.ReaderAccountEntity;
import com.yangchenle.electricityconsumptionsystem.service.ReaderTableService;
import com.yangchenle.electricityconsumptionsystem.util.BeansListUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReaderTableServiceImpl implements ReaderTableService {

    @Resource
    private ReaderTableDao readerTableDao;

    @Override
    public ReaderAccountDTO readerLogin(String readerPhone) {
        if (readerPhone == null) {
            return null;
        }
        ReaderAccountEntity readerAccountEntity = readerTableDao.ReaderLogin(readerPhone);
        ReaderAccountDTO readerAccountDTO = new ReaderAccountDTO();
        BeanUtils.copyProperties(readerAccountEntity, readerAccountDTO);
        return readerAccountDTO;
    }

    @Override
    public int insertReader(ReaderAccountDTO readerInfo) {
        if (readerInfo == null) {
            return 0;
        }
        ReaderAccountEntity readerAccountEntity = new ReaderAccountEntity();
        BeanUtils.copyProperties(readerInfo, readerAccountEntity);
        return readerTableDao.insertReader(readerAccountEntity);
    }

    @Override
    public ReaderAccountDTO queryById(Integer readerId) {
        if (readerId == null) {
            return null;
        }
        ReaderAccountEntity readerAccountEntity = readerTableDao.queryById(readerId);
        ReaderAccountDTO readerAccountDTO = new ReaderAccountDTO();
        BeanUtils.copyProperties(readerAccountEntity, readerAccountDTO);
        return readerAccountDTO;
    }

    @Override
    public int updateReaderInfo(String readerName, Integer readerId) {
        if (readerName == null || readerId == null) {
            return 0;
        }
        return readerTableDao.updateReaderInfo(readerName, readerId);
    }

    @Override
    public List<ReaderAccountDTO> selectReaderAll() {
        List<ReaderAccountEntity> readerAccountEntities = readerTableDao.selectReaderAll();
        if (CollectionUtils.isEmpty(readerAccountEntities)) {
            return new ArrayList<>();
        }
        return BeansListUtils.copyListProperties(readerAccountEntities, ReaderAccountDTO.class);
    }

    @Override
    public int deleteById(Integer id) {
        if (id == null || id < 1) {
            return 0;
        }
        return readerTableDao.deleteById(id);
    }
}
