package com.icia.recipe.management.service;

import com.icia.recipe.management.dao.DeliveryDao;
import com.icia.recipe.management.dto.DeliveryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class DeliveryService {
    @Autowired
    DeliveryDao dDao;

    public HashMap getMonthlyDelivery() {
        HashMap monthMap = new HashMap<>();
        for (int i = 1; i <= 12; i++) {
            int monthlyDelivery = dDao.getMonthlyDelivery(i);
            monthMap.put((i+"월"), monthlyDelivery);
        }
        return monthMap;
    }

    public List<DeliveryDto> getOrderList() {
        return dDao.getOrderList();
    }

    public boolean deliveryStart(ArrayList keySet) {
        return dDao.deliveryStart(keySet);
    }

    public List<DeliveryDto> getOrderList2() {
        return dDao.getOrderList2();
    }

    public boolean deliveryEnd(ArrayList keySet) {
        return dDao.deliveryEnd(keySet);
    }

    public boolean deliveryEndUpdate(ArrayList keySet) {
        boolean update = dDao.updateDeliveryStatus(keySet);
        if (update) {
            return true;
        } else {
            return false;
        }
    }
}
