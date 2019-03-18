package com.yangchenle.electricityconsumptionsystem.dao;

import com.yangchenle.electricityconsumptionsystem.entity.DeductionRecordEntity;
import com.yangchenle.electricityconsumptionsystem.entity.MoneyAndConsumptionSumEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface DeductionRecordDao {
    /**
     * 新增扣费记录
     *
     * @param deductionRecordEntity
     * @return
     */
    int addDeductionRecord(@Param("data") DeductionRecordEntity deductionRecordEntity);

    /**
     * 根据id查看扣费记录
     *
     * @param id
     * @return
     */
    DeductionRecordEntity selectDeductionRecordById(@Param("id") Integer id);

    /**
     * 根据电表id||时间动态查询扣费记录
     *
     * @param electricId
     * @param start
     * @param end
     * @return
     */
    List<DeductionRecordEntity> selectDeductionRecord(@Param("electricId") Integer electricId,
                                                      @Param("start") Date start,
                                                      @Param("end") Date end);

    /**
     * 查询所有扣费记录
     *
     * @return
     */
    List<DeductionRecordEntity> selectDeductionRecordAll();

    /**
     * 根据时间||电表id统计扣费和用电
     * @param electricId
     * @param start
     * @param end
     * @return
     */
    List<MoneyAndConsumptionSumEntity> selectSum(@Param("electricId") Integer electricId,
                                           @Param("start") Date start,
                                           @Param("end") Date end);
}
