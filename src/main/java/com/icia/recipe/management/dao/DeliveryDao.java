package com.icia.recipe.management.dao;

import com.icia.recipe.management.dto.DeliveryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface DeliveryDao {

    int getTodayOrderDelivery();

    int getTodayOrderCount();

    int getWeekOrderDelivery();

    int getWeekOrderCount();

    int getOrderCount();

    int getMonthlyDelivery(int i);

    int getTodayOrderDelivering();

    int getWeekDelivering();

    List<DeliveryDto> getOrderList();

    boolean deliveryStart(ArrayList keySet);

    List<DeliveryDto> getOrderList2();

    boolean deliveryEnd(ArrayList keySet);

    boolean updateDeliveryStatus(ArrayList keySet);

    int getTotalOrder();
}
