package com.yangchenle.electricityconsumptionsystem.service;

import com.yangchenle.electricityconsumptionsystem.dto.DeductionRecordDTO;

import java.util.Date;
import java.util.List;

public interface DeductionService {
    /**
     * 根据id查看扣费记录
     *
     * @param id
     * @return
     */
    DeductionRecordDTO selectDeductionRecordById(Integer id);

    /**
     * 根据电表id||时间动态查询扣费记录
     *
     * @param electricId
     * @param start
     * @param end
     * @return
     */
    List<DeductionRecordDTO> selectDeductionRecord(Integer electricId, Date start, Date end);

    /**
     * 查询所有扣费记录
     *
     * @return
     */
    List<DeductionRecordDTO> selectDeductionRecordAll();
}
