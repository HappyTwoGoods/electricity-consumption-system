package com.yangchenle.electricityconsumptionsystem.controller;

import com.sun.xml.internal.ws.spi.db.DatabindingException;
import com.yangchenle.electricityconsumptionsystem.common.CommonResult;
import com.yangchenle.electricityconsumptionsystem.constant.SessionParameters;
import com.yangchenle.electricityconsumptionsystem.dto.CopyRecordDTO;
import com.yangchenle.electricityconsumptionsystem.dto.ElectricDTO;
import com.yangchenle.electricityconsumptionsystem.dto.ReaderAccountDTO;
import com.yangchenle.electricityconsumptionsystem.dto.UserDTO;
import com.yangchenle.electricityconsumptionsystem.service.CopyRecordService;
import com.yangchenle.electricityconsumptionsystem.service.ElectricService;
import com.yangchenle.electricityconsumptionsystem.service.UserService;
import lombok.Data;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class CopyRecordController {

    @Resource
    private CopyRecordService copyRecordService;

    @Resource
    private ElectricService electricService;

    @Resource
    private UserService userService;

    /**
     * 抄表员添加抄表记录
     *
     * @param electricId
     * @param copyData
     * @return
     */
    @GetMapping("/reader/insert/record")
    public CommonResult addRecord(Integer electricId, BigDecimal copyData, HttpServletRequest request){
        if (electricId == null){
            return CommonResult.fail(403,"参数错误！");
        }
        HttpSession session = request.getSession();
        Integer readerId = (Integer) session.getAttribute(SessionParameters.READERID);
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

    /**
     * 查询该抄表员抄表记录
     *
     * @param request
     * @return
     */
    @GetMapping("/reader/queryAll")
    public CommonResult queryCopyRecord(HttpServletRequest request){
        HttpSession session = request.getSession();
        Integer readerId = (Integer) session.getAttribute(SessionParameters.READERID);
        List<CopyRecordDTO> copyRecordDTOList = copyRecordService.selectByReader(readerId);
        if (CollectionUtils.isEmpty(copyRecordDTOList)){
            return CommonResult.fail(404,"没有相关信息！");
        }
        List<ReaderEleDTO> readerEleDTOList = new ArrayList<>();
        for (CopyRecordDTO copyRecordDTO: copyRecordDTOList){
            ReaderEleDTO readerEleDTO = new ReaderEleDTO();
            ElectricDTO electricDTO = electricService.selectElectricById(copyRecordDTO.getElectricId());
            UserDTO userDTO = userService.queryById(electricDTO.getUserId());
            readerEleDTO.setElectricNum(electricDTO.getNum());
            readerEleDTO.setUserName(userDTO.getUserName());
            readerEleDTO.setUserPhone(userDTO.getUserPhone());
            readerEleDTO.setCopyData(copyRecordDTO.getCopyData());
            readerEleDTO.setAddTime(copyRecordDTO.getAddTime());
            readerEleDTOList.add(readerEleDTO);
        }
        return CommonResult.success(readerEleDTOList);
    }

    @Data
    private class ReaderEleDTO{

        private Integer electricNum;

        private String userName;

        private String userPhone;

        private BigDecimal copyData;

        private Date addTime;
    }

    @Data
    private class UserCopyDTO{

        private Integer electricNum;

        private String readerName;

        private BigDecimal copyData;

        private Date addTime;

    }
}
