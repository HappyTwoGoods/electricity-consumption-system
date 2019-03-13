package com.yangchenle.electricityconsumptionsystem.service;

import com.sun.org.apache.regexp.internal.RE;
import com.yangchenle.electricityconsumptionsystem.dto.ReaderAccountDTO;
import com.yangchenle.electricityconsumptionsystem.entity.ReaderAccountEntity;

import java.util.List;

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

    /**
     * 查询所有的抄表员
     * @return
     */
    List<ReaderAccountDTO> selectReaderAll();

    /**
     * 根据id删除抄表员
     * @param id
     * @return
     */
    int deleteById(Integer id);
}
