package com.icia.recipe.controller.mainController;

import com.icia.recipe.dto.mainDto.FooditemDto;
import com.icia.recipe.dto.manageDto.FoodItemDto;
import com.icia.recipe.entity.FoodItem;
import com.icia.recipe.entity.Member;
import com.icia.recipe.service.mainService.MemberService;
import jakarta.persistence.Tuple;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
@Slf4j
public class HomeController {
    @Autowired
    MemberService mSer;

    @GetMapping("/")
    public String index(Member member, HttpSession session, Model model, Principal principal, Authentication auth,
                        @AuthenticationPrincipal UserDetails userDetails) {

        System.out.println("Principal:"+principal);
//        if(session.getAttribute("msg")!=null) {
//            model.addAttribute("msg", session.getAttribute("msg"));
//            session.removeAttribute("msg");
//        }
        List<Object[]> Rank = mSer.getRanking();
        if (!Rank.isEmpty()) {
            System.out.println("Ranking 정보 Get 성공");
            model.addAttribute("Rank", Rank);
//            System.out.println(Rank.toString());
            return "index";
        } else {
            System.out.println("Ranking 정보 Get 실패");
            return null;
        }
    }

}
