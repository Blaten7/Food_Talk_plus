package com.icia.recipe.management.dao;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MoneyDao {
    Integer getTodayProfitCount();

    Integer getWeekProfitCount();

    int getMonthlyProfit(int i);
}
