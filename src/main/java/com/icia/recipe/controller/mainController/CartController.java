package com.icia.recipe.controller.mainController;

import com.icia.recipe.entity.Order;
import com.icia.recipe.service.mainService.CartService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@Slf4j
public class CartController {

    @Autowired
    CartService cSer;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/cart/main")
    public String cartMain(HttpSession session, Principal principal, Model model) {
        log.info("장바구니 메인 입장");
        String name = principal.getName();
        if (!name.isEmpty()) {
            log.info("user: {}", name);
            String list = cSer.selectCart(name, model);
            model.addAttribute("caList", list);
        }
        return "main/cart/cartMain";
    }

    @GetMapping("/cart/test")
    public String cartTest() {
        return "main/fooditem/test";
    }

    @PostMapping("/cart/order")
    public String cartOrder(@RequestParam("orderList")List<List<String>> oList, Principal pric, Model model) {
        log.info("카트 오더 입장: {}", oList);
        List<HashMap<String, String>> list = new ArrayList<>();
        if(oList.get(0).size() < 2){
            List<String> list1 = new ArrayList<>();
             oList.forEach(list1::addAll);
             oList.clear();
             oList.add(list1);
             log.info("olist: {}" ,oList );
        }
        oList.forEach(o -> {
            HashMap<String, String> hMap = new HashMap<>();
            o.forEach(o2 -> {
                String[] element = o2.split(":");
                if (element.length == 2) {
                    hMap.put(element[0], element[1]);
                } else {
                    log.info("이상한거: {}", o2);
                }
            });
            list.add(hMap);
        });
        String name = pric.getName();
        cSer.selectMember(name, model);
        model.addAttribute("cartOrder", cSer.makeHtmlCartOrder(list, model));
        log.info("배열 리스트:{}", list);
        log.info("배열중 하나:{}", list.get(0).get("dvItemMaxcount"));
        return "main/cart/cartOrder";
    }

    @PostMapping("/cart/inputOrder")
    public String inputOrder(@ModelAttribute Order iOrder, Model model, Principal principal) {
        /*log.info("inputOrder입장{},{},{}", price, inputList, itemList);
        log.info("itemList Controller:{}", itemList);
        String count = String.valueOf(itemList.size());
        boolean index = cSer.inputOrder(price, inputList, itemList, id, count);
        */
        log.info("inputOrder: {}", iOrder);

        String id = principal.getName();
        //boolean index = cSer.inputOrder(price,iOrder,id);
        /*if(!index){
            model.addAttribute("msg", "주문을 실패하였습니다. 지속되면 관리자에게 문의해주십시요");
            return "redirect:/cart/main";
        }*/

        return "index";
    }


}

