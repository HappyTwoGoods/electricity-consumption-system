package com.yangchenle.electricityconsumptionsystem.impl;

import com.yangchenle.electricityconsumptionsystem.service.RandomCodeService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomCodeServiceImpl implements RandomCodeService {

    @Override
    public String buildCode() {
        final int length = 6;
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
