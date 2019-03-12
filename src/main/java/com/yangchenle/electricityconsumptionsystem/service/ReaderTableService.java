package com.yangchenle.electricityconsumptionsystem.service;

import com.sun.org.apache.regexp.internal.RE;
import com.yangchenle.electricityconsumptionsystem.dto.ReaderAccountDTO;

public interface ReaderTableService {

    /**
     * 抄表员登录
     *
     * @param readerPhone
     * @return
     */
    ReaderAccountDTO readerLogin(String readerPhone);

    /**
     * 抄表员注册
     *
     * @param readerInfo
     * @return
     */
    int insertReader(ReaderAccountDTO readerInfo);

    /**
     * 根据id查询抄表员信息
     *
     * @param readerId
     * @return
     */
    ReaderAccountDTO queryById(Integer readerId);

    /**
     * 修改抄表员信息
     *
     * @param readerName
     * @param readerId
     * @return
     */
    int updateReaderInfo(String readerName, Integer readerId);
}
