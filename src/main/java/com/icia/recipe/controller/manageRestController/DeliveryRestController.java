package com.icia.recipe.controller.manageRestController;

import com.icia.recipe.dto.manageDto.DeliveryDto;
import com.icia.recipe.repository.OrderRepository;
import com.icia.recipe.service.manageService.DeliveryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class DeliveryRestController {

    @Autowired
    private DeliveryService dSer;

    @Autowired
    OrderRepository or;

    @PostMapping("/order/delivery/start")
    @Secured("ROLE_ADMIN")
    public List<DeliveryDto> deliveryStart(@RequestParam("keySet") ArrayList keySet, Model model) {
        boolean result = dSer.deliveryStart(keySet);
        if (result) {
            return or.getOrderList();
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
        return or.getOrderList();

    }

    @PostMapping("/order/delivery/end")
    @Secured("ROLE_ADMIN")
    public List<DeliveryDto> deliveryEnd(@RequestParam("keySet") ArrayList keySet, Model model) {
        boolean result = dSer.deliveryEnd(keySet);
        if (result) {
            return or.getOrderList();
        } else {
            return null;
        }
    }
}

