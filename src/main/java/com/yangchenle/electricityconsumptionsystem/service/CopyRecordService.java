package com.yangchenle.electricityconsumptionsystem.service;

import com.yangchenle.electricityconsumptionsystem.dto.CopyRecordDTO;

import java.math.BigDecimal;
import java.util.List;

public interface CopyRecordService {
    /**
     * 新增抄表记录
     *
     * @param copyRecordDTO
     * @return
     */
    int addCopyRecord(CopyRecordDTO copyRecordDTO);

    /**
     * 修改抄表数据
     *
     * @param data
     * @return
     */
    int updateCopyRecord(BigDecimal data, Integer id);

    /**
     * 根据id查询用抄表记录
     *
     * @param id
     * @return
     */
    CopyRecordDTO selectCopyRecordById(Integer id);

    /**
     * 根据抄表员id查询抄表记录
     *
     * @param readerId
     * @return
     */
    List<CopyRecordDTO> selectByReader(Integer readerId);

    List<CopyRecordDTO> selectByElectrocId(Integer id);
}
