package com.yangchenle.electricityconsumptionsystem.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ReaderAccountDTO implements Serializable {

    private static final long serialVersionUID = 8748160631407075232L;
    private Integer readerId;

    private String readerName;

    private String readerPhone;

    private Date addTime;

    private Date updateTime;
}
