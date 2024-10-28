package com.icia.recipe.service.manageService;

import com.icia.recipe.dto.manageDto.DeliveryDto;
import com.icia.recipe.management.dto.DeliveryDto;
import com.icia.recipe.repository.OrderRepository;
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
    OrderRepository or;

    public HashMap getMonthlyDelivery() {
        HashMap monthMap = new HashMap<>();
        for (int i = 1; i <= 12; i++) {
            int monthlyDelivery = or.getMonthlyDelivery(i);
            monthMap.put((i+"월"), monthlyDelivery);
        }
        return monthMap;
    }

    public List<DeliveryDto> getOrderList() {
        return or.getOrderList();
    }

    public boolean deliveryStart(ArrayList keySet) {
        return or.deliveryStart(keySet);
    }

    public List<DeliveryDto> getOrderList2() {
        return or.getOrderList2();
    }

    public boolean deliveryEnd(ArrayList keySet) {
        return or.deliveryEnd(keySet);
    }

    public boolean deliveryEndUpdate(ArrayList keySet) {
        boolean update = or.updateDeliveryStatus(keySet);
        if (update) {
            return true;
        } else {
            return false;
        }
    }
}
