package com.yangchenle.electricityconsumptionsystem.task;

import com.yangchenle.electricityconsumptionsystem.constant.ElectricState;
import com.yangchenle.electricityconsumptionsystem.constant.ElectricType;
import com.yangchenle.electricityconsumptionsystem.dao.CopyRecordDao;
import com.yangchenle.electricityconsumptionsystem.dao.ElectricDao;
import com.yangchenle.electricityconsumptionsystem.dao.TypeTableDao;
import com.yangchenle.electricityconsumptionsystem.entity.CopyRecordEntity;
import com.yangchenle.electricityconsumptionsystem.entity.ElectricEntity;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class StopAndReduceElectricTask {

    @Resource
    private ElectricDao electricDao;
    @Resource
    private CopyRecordDao copyRecordDao;
    @Resource
    private TypeTableDao typeTableDao;

    /**
     * 每月三号，欠费用户停电
     */
    @Scheduled(cron = "0 0 0 3 * *")
    public void stopElectric() {
        List<ElectricEntity> electricEntities = electricDao.selectElectricAll();
        List<Integer> ids = electricEntities.stream().filter(electricEntity -> electricEntity.getMoney().compareTo(BigDecimal.valueOf(0)) < 0).map(ElectricEntity::getElectricId).collect(Collectors.toList());
        for (Integer id : ids) {
            electricDao.updateElectric(null, null, ElectricState.STOP, id);
        }
    }

    /**
     * 每月28号扣费
     */
    @Scheduled(cron = "0 0 0 28 * *")
    public void reduceMoney() {
        BigDecimal factoryPrice = typeTableDao.selectTypeByNum(ElectricType.FACTORY).getPrice();
        BigDecimal homePrice = typeTableDao.selectTypeByNum(ElectricType.HOEM).getPrice();
        List<CopyRecordEntity> copyRecordEntities = copyRecordDao.selectNewRecord();
        for (CopyRecordEntity copyRecordEntity : copyRecordEntities) {
            ElectricEntity electricEntity = electricDao.selectElectricById(copyRecordEntity.getElectricId());
            if (electricEntity == null || electricEntity.getState().equals(ElectricState.STOP)) {
                continue;
            }
            BigDecimal useElectric = copyRecordEntity.getCopyData().subtract(electricEntity.getLastData());
            BigDecimal electricPrice;
            if (electricEntity.getType().equals(ElectricType.FACTORY)) {
                electricPrice = useElectric.multiply(factoryPrice);
            } else {
                electricPrice = useElectric.multiply(homePrice);
            }
            electricDao.updateElectric(copyRecordEntity.getCopyData(), electricEntity.getMoney().subtract(electricPrice), null, electricEntity.getElectricId());
        }
    }
}
