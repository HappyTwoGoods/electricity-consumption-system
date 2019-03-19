package com.yangchenle.electricityconsumptionsystem.util;

import com.yangchenle.electricityconsumptionsystem.dto.ElectricDTO;
import com.yangchenle.electricityconsumptionsystem.service.ElectricService;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ElectricUtil {
    @Resource
    private ElectricService electricService;

    public Integer isExist(Integer electricNum) {
        if (electricNum != null) {
            List<ElectricDTO> electricDTOS = electricService.selectElectricByCondition(electricNum, null, null);
            if (!CollectionUtils.isEmpty(electricDTOS)) {
                return electricDTOS.get(0).getElectricId();
            }
            return -1;
        }
        return null;
    }
}
