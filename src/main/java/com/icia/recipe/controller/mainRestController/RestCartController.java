package com.icia.recipe.controller.mainRestController;

import com.icia.recipe.service.mainService.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
public class RestCartController {
    @Autowired
    CartService cSer;
    @GetMapping("/cart/deleteList")
    public String deleteList(@RequestParam("ca_num") List num, Model model, Principal pr){
        String name = pr.getName();
        log.info("restDelete 입장.{}",num);
        return cSer.deleteCart(num,model,name);

    }
    @GetMapping("/cart/intoCart")
    public boolean cartIntoCart(@RequestParam("num") String num, @RequestParam("count") String count, @AuthenticationPrincipal UserDetails user, Model model) {
        log.info("intoCart 입장");
        HashMap<String, String> hMap = new HashMap<>();
        hMap.put("num", num);
        hMap.put("count", count);
        hMap.put("user", user.getUsername());
        cSer.intoCartList(hMap, model);
        return true;
    }
}
