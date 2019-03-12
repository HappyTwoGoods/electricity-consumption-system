package com.yangchenle.electricityconsumptionsystem.dao;

import com.yangchenle.electricityconsumptionsystem.entity.ReaderAccountEntity;
import lombok.Data;
import org.apache.ibatis.annotations.Param;

public interface ReaderTableDao {

    /**
     * 抄表员登录
     *
     * @param readerPhone
     * @return
     */
    ReaderAccountEntity ReaderLogin(@Param("readerPhone")String readerPhone);

    /**
     * 抄表员注册
     *
     * @param readerInfo
     * @return
     */
    int insertReader(@Param("readerInfo") ReaderAccountEntity readerInfo);

    /**
     * 根据Id查询抄表员信息
     *
     * @param readerId
     * @return
     */
    ReaderAccountEntity queryById(@Param("readerId") Integer readerId);

    /**
     * 修改抄表员信息
     *
     * @param readerName
     * @param readerId
     * @return
     */
    int updateReaderInfo(@Param("readerName")String readerName,
                         @Param("readerId")Integer readerId);


}
