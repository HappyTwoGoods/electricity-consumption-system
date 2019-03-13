package com.yangchenle.electricityconsumptionsystem.controller;

import com.yangchenle.electricityconsumptionsystem.common.CommonResult;
import com.yangchenle.electricityconsumptionsystem.constant.PhoneNum;
import com.yangchenle.electricityconsumptionsystem.dto.ReaderAccountDTO;
import com.yangchenle.electricityconsumptionsystem.emun.HttpStatus;
import com.yangchenle.electricityconsumptionsystem.service.ReaderTableService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class ReaderTableController {
    @Resource
    private ReaderTableService readerTableService;

    @PostMapping("/manager/addReader")
    public CommonResult addReader(String name, String phone) {
        if (name == null || name.length() < 1 || phone == null || phone.length() != PhoneNum.LENGTH) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        ReaderAccountDTO readerAccountDTO = new ReaderAccountDTO();
        readerAccountDTO.setReaderName(name);
        readerAccountDTO.setReaderPhone(phone);
        int addNum = readerTableService.insertReader(readerAccountDTO);
        if (addNum < 1) {
            return CommonResult.fail(HttpStatus.ERROR);
        }
        return CommonResult.success("新增成功");
    }

    @GetMapping("/manager/select/readerAll")
    public CommonResult selectAllReader() {
        List<ReaderAccountDTO> readerAccountDTOS = readerTableService.selectReaderAll();
        if (CollectionUtils.isEmpty(readerAccountDTOS)) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        return CommonResult.success(readerAccountDTOS);
    }

    @GetMapping("/manager/select/readerOne")
    public CommonResult selectReaderById(Integer id) {
        if (id == null || id < 1) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        ReaderAccountDTO readerAccountDTO = readerTableService.queryById(id);
        if (readerAccountDTO == null) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        return CommonResult.success(readerAccountDTO);
    }

    @GetMapping("/manager/delete/reader")
    public CommonResult deleteReader(Integer id) {
        if (id == null || id < 1) {
            return CommonResult.fail(HttpStatus.PARAMETER_ERROR);
        }
        ReaderAccountDTO readerAccountDTO = readerTableService.queryById(id);
        if (readerAccountDTO == null) {
            return CommonResult.fail(HttpStatus.NOT_FOUND);
        }
        int deleteNum = readerTableService.deleteById(id);
        if (deleteNum < 1) {
            return CommonResult.fail(HttpStatus.ERROR);
        }
        return CommonResult.success();
    }

}
