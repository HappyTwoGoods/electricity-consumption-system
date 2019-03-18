package com.yangchenle.electricityconsumptionsystem.dao;

import com.yangchenle.electricityconsumptionsystem.entity.CopyRecordEntity;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 抄表员信息
 */
public interface CopyRecordDao {
    /**
     * 新增抄表记录
     *
     * @param copyRecordEntity
     * @return
     */
    int addCopyRecord(@Param("data") CopyRecordEntity copyRecordEntity);

    /**
     * 修改抄表数据
     *
     * @param data
     * @param id
     * @return
     */
    int updateCopyRecord(@Param("data") BigDecimal data,
                         @Param("id") Integer id);

    /**
     * 根据id查询用抄表记录
     *
     * @param id
     * @return
     */
    CopyRecordEntity selectCopyRecordById(@Param("id") Integer id);

    /**
     * 查询最新的抄表数据
     *
     * @return
     */
    List<CopyRecordEntity> selectNewRecord();

    List<CopyRecordEntity> selectByReader(@Param("readerId")Integer readerId);
}
