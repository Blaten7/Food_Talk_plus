package com.icia.recipe.management.service;

import com.icia.recipe.management.dao.MoneyDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashMap;

@Slf4j
@Service
public class MoneyService {

    @Autowired
    MoneyDao mDao;

    public String getTodayProfitCount() {
        Integer dayProfit = mDao.getTodayProfitCount();
        return (dayProfit== null)?makeMoneyFormat(0):makeMoneyFormat(dayProfit);
    }

    public String getWeekProfitCount() {
        /*String weekProfit = mDao.getWeekProfitCount();*/
        return makeMoneyFormat(mDao.getWeekProfitCount());
    }

    public String makeMoneyFormat(Integer money) {
        /*DecimalFormat df = null;
        log.info("makeMoneyFormat {}", money);
        log.info(money);
        if (money.length() > 6) {
            df = new DecimalFormat("000,000,000");
        } else {
            df = new DecimalFormat("000,000");
        }
        log.info("그래서 뭔데 시발{}",df.format(money));
        return df.format(money);*/
       /*DecimalFormat df  = (money.length() > 6)
                ? new DecimalFormat("000,000,000")
                : new DecimalFormat("000,000");
                return df.format(money);*/
        return (money+"").replaceAll("\\B(?=(\\d{3})+(?!\\d))", ",");
    }

    public HashMap<String,String> getMonthProfitCount() {
        HashMap<String,String> monthMap = new HashMap<>();
        for (int i = 1; i <= 12; i++) {
            int monthlyProfit = mDao.getMonthlyProfit(i);
            monthMap.put((i+"월"), monthlyProfit+"");
            log.info("{}",monthlyProfit+"응애..");
        }
        return monthMap;
    }
}
