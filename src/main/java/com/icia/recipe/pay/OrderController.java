package com.icia.recipe.pay;

import com.icia.recipe.home.dto.InputOrderDto;
import com.icia.recipe.home.dto.OrderDto;
import com.icia.recipe.home.service.CartService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final KakaoPayService kakaoPayService;
    private final CartService cSer;
    @PostMapping("/pay/ready")
    public @ResponseBody ReadyResponse payReady(InputOrderDto orderDto) {
        log.info("Pay ready,{}",orderDto);
        String name = orderDto.getName();
        int totalPrice = Integer.parseInt(orderDto.getPrice().replaceAll(",",""));

        log.info("주문 상품 이름: " + name);
        log.info("주문 금액: " + totalPrice);

        // 카카오 결제 준비하기
        ReadyResponse readyResponse = kakaoPayService.payReady(name, totalPrice);
        // 세션에 결제 고유번호(tid) 저장
        SessionUtils.addAttribute("tid", readyResponse.getTid());
        log.info("결제 고유번호: " + readyResponse.getTid());
        SessionUtils.addAttribute("orderDto",orderDto);

        return readyResponse;
    }

    @GetMapping("/pay/completed")
    public String payCompleted(@RequestParam("pg_token") String pgToken, Principal principal, Model model, HttpSession session) {
        InputOrderDto orderDto = (InputOrderDto) SessionUtils.getAttribute("orderDto");
        session.removeAttribute("orderDto");
        log.info("pay completed 입장 {}",orderDto);
        String tid = SessionUtils.getStringAttributeValue("tid");
        log.info("결제승인 요청을 인증하는 토큰: " + pgToken);
        log.info("결제 고유번호: " + tid);
        // 카카오 결제 요청하기
        ApproveResponse approveResponse = kakaoPayService.payApprove(tid, pgToken);

        String id = principal.getName();
        boolean index = cSer.inputOrder(orderDto.getPrice(),orderDto,id);

        if(!index){
            model.addAttribute("msg", "주문을 실패하였습니다. 지속되면 관리자에게 문의해주십시요");
            return "redirect:/cart/main";
        }

        return "index";
    }

    @GetMapping("/pay/cancel")
    public String payCompleted2(@RequestParam("pg_token") String pgToken) {
        return "redirect:/cart/main";
    }
}