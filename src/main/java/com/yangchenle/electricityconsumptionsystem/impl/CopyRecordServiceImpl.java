package com.yangchenle.electricityconsumptionsystem.impl;

import com.yangchenle.electricityconsumptionsystem.dao.CopyRecordDao;
import com.yangchenle.electricityconsumptionsystem.dto.CopyRecordDTO;
import com.yangchenle.electricityconsumptionsystem.entity.CopyRecordEntity;
import com.yangchenle.electricityconsumptionsystem.service.CopyRecordService;
import com.yangchenle.electricityconsumptionsystem.util.BeansListUtils;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CopyRecordServiceImpl implements CopyRecordService {
    @Reference
    private CopyRecordDao copyRecordDao;

    @Override
    public int addCopyRecord(CopyRecordDTO copyRecordDTO) {
        if (copyRecordDTO == null || copyRecordDTO.getElectricId() == null ||
                copyRecordDTO.getElectricId() < 1 || copyRecordDTO.getCopyData() == null) {
            return 0;
        }
        CopyRecordEntity copyRecordEntity = new CopyRecordEntity();
        BeanUtils.copyProperties(copyRecordDTO,copyRecordEntity);
        return copyRecordDao.addCopyRecord(copyRecordEntity);
    }

    @Override
    public int updateCopyRecord(BigDecimal data, Integer id) {
        if(data==null||id<1){
            return 0;
        }
        return copyRecordDao.updateCopyRecord(data,id);
    }

    @Override
    public CopyRecordDTO selectCopyRecordById(Integer id) {
        if(id==null||id<1){
            return null;
        }
        CopyRecordEntity copyRecordEntity = copyRecordDao.selectCopyRecordById(id);
        if(copyRecordEntity==null){
            return null;
        }
        CopyRecordDTO copyRecordDTO = new CopyRecordDTO();
        BeanUtils.copyProperties(copyRecordEntity,copyRecordDTO);
        return copyRecordDTO;
    }
}
