package com.icia.recipe.controller.manageController;

import com.icia.recipe.dto.manageDto.DeliveryDto;
import com.icia.recipe.repository.OrderRepository;
import com.icia.recipe.service.manageService.DeliveryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Controller
public class DeliveryController {

    @Autowired
    DeliveryService dSer;
    @Autowired
    OrderRepository or;

    @Secured("ROLE_ADMIN")
    @GetMapping("/delivery")
    public String delivery(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String m_id = userDetails.getUsername();
        model.addAttribute("m_id", m_id);
        // 일간 배송 관련
        int dayDelivery = or.getTodayOrderDelivery();
        int dayOrder = or.getTodayOrderCount();
        int dayDelivering = or.getTodayOrderDelivering();
        model.addAttribute("todayDelivery", dayDelivery);
        model.addAttribute("todayOrder", dayOrder);
        model.addAttribute("todayDelivering", dayDelivering);
        int dayOrderCount = or.getOrderCount();
        double percentage = 0;
        int getTotal = or.getTotalOrder();
        percentage = ((double) getTotal / dayOrderCount) * 100;
        DecimalFormat df = new DecimalFormat("0.0");
        log.info("퍼센트 비율 : {}", percentage);
        model.addAttribute("todayPercentage", df.format(percentage));

        // 주간 배송 관련 ㅇㅇ
        int weekOrder = or.getWeekOrderCount(); // 준비중
        int weekDelivery = or.getWeekOrderDelivery(); // 배송와뇰
        int weekDelivering = or.getWeekDelivering(); // 배송중
        model.addAttribute("weekDelivery", weekDelivery);
        model.addAttribute("weekOrder", weekOrder);
        model.addAttribute("weekDelivering", weekDelivering);
        // 월간 배송 관련
        HashMap MonthlyDelivery = dSer.getMonthlyDelivery();
        model.addAttribute("monthlyDelivery", MonthlyDelivery);
        // 주문리스트
        List<DeliveryDto> dList = dSer.getOrderList();
        List<DeliveryDto> aList = dSer.getOrderList2();
        model.addAttribute("orderList", dList);
        model.addAttribute("orderList2", aList);
        model.addAttribute("deliveryList", aList);
        return "management/delivery";
    }
}
