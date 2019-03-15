package com.yangchenle.electricityconsumptionsystem.dao;

import com.yangchenle.electricityconsumptionsystem.constant.ElectricState;
import com.yangchenle.electricityconsumptionsystem.constant.ElectricType;
import com.yangchenle.electricityconsumptionsystem.entity.CopyRecordEntity;
import com.yangchenle.electricityconsumptionsystem.entity.ElectricEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class copyDaoTest {
    @Resource
    private CopyRecordDao copyRecordDao;
    @Resource
    private ElectricDao electricDao;
    @Resource
    private TypeTableDao typeTableDao;

    @Test
    public void selectNewRecordTest() {
        List<CopyRecordEntity> copyRecordEntities = copyRecordDao.selectNewRecord();
        System.out.println(copyRecordEntities);
    }

    @Test
    public void reduceMoney() {
        BigDecimal factoryPrice = typeTableDao.selectTypeByNum(ElectricType.FACTORY).getPrice();
        BigDecimal homePrice = typeTableDao.selectTypeByNum(ElectricType.HOEM).getPrice();
        List<CopyRecordEntity> copyRecordEntities = copyRecordDao.selectNewRecord();
        for (CopyRecordEntity copyRecordEntity : copyRecordEntities) {
            ElectricEntity electricEntity = electricDao.selectElectricById(copyRecordEntity.getElectricId());
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

    @Test
    public void stopElectric() {
        List<ElectricEntity> electricEntities = electricDao.selectElectricAll();
        List<Integer> ids = electricEntities.stream().filter(electricEntity -> electricEntity.getMoney().compareTo(BigDecimal.valueOf(0)) < 0).map(ElectricEntity::getElectricId).collect(Collectors.toList());
        for (Integer id : ids) {
            electricDao.updateElectric(null, null, ElectricState.STOP, id);
        }
    }
}
