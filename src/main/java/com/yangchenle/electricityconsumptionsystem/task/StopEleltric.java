package com.yangchenle.electricityconsumptionsystem.task;

import com.yangchenle.electricityconsumptionsystem.constant.ElectricState;
import com.yangchenle.electricityconsumptionsystem.dao.ElectricDao;
import com.yangchenle.electricityconsumptionsystem.entity.ElectricEntity;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class StopEleltric {

    @Resource
    private ElectricDao electricDao;
    @Scheduled(cron = "")
    public void stopElectric() {
        List<ElectricEntity> electricEntities = electricDao.selectElectricAll();
        List<Integer> ids = electricEntities.stream().filter(electricEntity -> electricEntity.getMoney().compareTo(BigDecimal.valueOf(0)) < 0).map(ElectricEntity::getElectricId).collect(Collectors.toList());
        for (Integer id : ids) {
            electricDao.updateElectric(null, null, ElectricState.STOP, id);
        }

    }
}
