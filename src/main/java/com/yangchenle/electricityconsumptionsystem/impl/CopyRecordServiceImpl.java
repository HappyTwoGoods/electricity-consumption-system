package com.yangchenle.electricityconsumptionsystem.impl;

import com.yangchenle.electricityconsumptionsystem.dao.CopyRecordDao;
import com.yangchenle.electricityconsumptionsystem.dto.CopyRecordDTO;
import com.yangchenle.electricityconsumptionsystem.entity.CopyRecordEntity;
import com.yangchenle.electricityconsumptionsystem.service.CopyRecordService;
import com.yangchenle.electricityconsumptionsystem.util.BeansListUtils;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
public class CopyRecordServiceImpl implements CopyRecordService {
    @Resource
    private CopyRecordDao copyRecordDao;

    @Override
    public int addCopyRecord(CopyRecordDTO copyRecordDTO) {
        if (copyRecordDTO == null || copyRecordDTO.getReaderId() == null || copyRecordDTO.getReaderId() < 1 ||
                copyRecordDTO.getElectricId() == null || copyRecordDTO.getElectricId() < 1
                || copyRecordDTO.getCopyData() == null) {
            return 0;
        }
        CopyRecordEntity copyRecordEntity = new CopyRecordEntity();
        BeanUtils.copyProperties(copyRecordDTO, copyRecordEntity);
        return copyRecordDao.addCopyRecord(copyRecordEntity);
    }

    @Override
    public int updateCopyRecord(BigDecimal data, Integer id) {
        if (data == null || id < 1) {
            return 0;
        }
        return copyRecordDao.updateCopyRecord(data, id);
    }

    @Override
    public CopyRecordDTO selectCopyRecordById(Integer id) {
        if (id == null || id < 1) {
            return null;
        }
        CopyRecordEntity copyRecordEntity = copyRecordDao.selectCopyRecordById(id);
        if (copyRecordEntity == null) {
            return null;
        }
        CopyRecordDTO copyRecordDTO = new CopyRecordDTO();
        BeanUtils.copyProperties(copyRecordEntity, copyRecordDTO);
        return copyRecordDTO;
    }

    @Override
    public List<CopyRecordDTO> selectByReader(Integer readerId) {
        if (readerId == null){
            return null;
        }
        List<CopyRecordEntity> copyRecordEntityList = copyRecordDao.selectByReader(readerId);
        if (CollectionUtils.isEmpty(copyRecordEntityList)){
            return null;
        }
        return BeansListUtils.copyListProperties(copyRecordEntityList,CopyRecordDTO.class);
    }

    @Override
    public List<CopyRecordDTO> selectByElectrocId(Integer id) {
        if (id == null){
            return null;
        }
        List<CopyRecordEntity> copyRecordEntityList = copyRecordDao.selectByElectrocId(id);
        return BeansListUtils.copyListProperties(copyRecordEntityList,CopyRecordDTO.class);
    }

    @Override
    public List<CopyRecordDTO> getEcharsInfo(Integer id) {
        if (id == null) {
            return null;
        }
        List<CopyRecordEntity> copyRecordEntityList = copyRecordDao.getEcharsInfo(id);
        return null;
    }
}
