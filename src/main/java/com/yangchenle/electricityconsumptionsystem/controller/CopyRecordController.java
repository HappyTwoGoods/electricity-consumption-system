package com.yangchenle.electricityconsumptionsystem.controller;

import com.yangchenle.electricityconsumptionsystem.common.CommonResult;
import com.yangchenle.electricityconsumptionsystem.dto.CopyRecordDTO;
import com.yangchenle.electricityconsumptionsystem.service.CopyRecordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RestController
public class CopyRecordController {

    @Resource
    private CopyRecordService copyRecordService;

    /**
     * 抄表员添加抄表记录
     *
     * @param electricId
     * @param copyData
     * @return
     */
    @GetMapping("/reader/insert/record")
    public CommonResult addRecord(Integer electricId, BigDecimal copyData){
        if (electricId == null){
            return CommonResult.fail(403,"参数错误！");
        }
        Integer readerId = 1;
        CopyRecordDTO copyRecordDTO = new CopyRecordDTO();
        copyRecordDTO.setElectricId(electricId);
        copyRecordDTO.setCopyData(copyData);
        copyRecordDTO.setReaderId(readerId);
        int result = copyRecordService.addCopyRecord(copyRecordDTO);
        if (result <= 0){
            return CommonResult.fail(500,"增加抄表记录失败！");
        }
        return CommonResult.success();
    }

    /**
     * 抄表员修改抄表记录
     *
     * @param copyData
     * @param copyId
     * @return
     */
    @GetMapping("/update/copyInfo/reader")
    public CommonResult updateByReader(BigDecimal copyData, Integer copyId){
        if (copyId == null){
            return CommonResult.fail(403,"参数错误！");
        }
        int result = copyRecordService.updateCopyRecord(copyData,copyId);
        if (result <= 0){
            return CommonResult.fail(500,"记录修改失败！");
        }
        return CommonResult.success();
    }
}
