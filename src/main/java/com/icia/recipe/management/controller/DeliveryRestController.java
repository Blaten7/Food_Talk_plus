package com.icia.recipe.management.controller;

import com.icia.recipe.management.dao.DeliveryDao;
import com.icia.recipe.management.dto.DeliveryDto;
import com.icia.recipe.management.service.DeliveryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class DeliveryRestController {

    @Autowired
    private DeliveryService dSer;

    @Autowired
    private DeliveryDao dDao;

    @PostMapping("/order/delivery/start")
    @Secured("ROLE_ADMIN")
    public List<DeliveryDto> deliveryStart(@RequestParam("keySet") ArrayList keySet, Model model) {
        boolean result = dSer.deliveryStart(keySet);
        if (result) {
            return dDao.getOrderList();
        } else {
            log.info("[배송완료] 컨트롤러 에러");
            return null;
        }
    }

    @PostMapping("/order/auto/update")
    @Secured("ROLE_ADMIN")
    public List<DeliveryDto> deliveryAutoUpdate(@RequestParam("keySet") ArrayList keySet, Model model) {
        boolean update = dSer.deliveryEndUpdate(keySet);
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return dDao.getOrderList();

    }

    @PostMapping("/order/delivery/end")
    @Secured("ROLE_ADMIN")
    public List<DeliveryDto> deliveryEnd(@RequestParam("keySet") ArrayList keySet, Model model) {
        boolean result = dSer.deliveryEnd(keySet);
        if (result) {
            return dDao.getOrderList();
        } else {
            return null;
        }
    }
}

