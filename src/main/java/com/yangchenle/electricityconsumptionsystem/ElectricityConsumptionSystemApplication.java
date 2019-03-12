package com.yangchenle.electricityconsumptionsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.yangchenle.electricityconsumptionsystem.dao")
@SpringBootApplication
public class ElectricityConsumptionSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElectricityConsumptionSystemApplication.class, args);
	}

}
