package com.icia.recipe.controller.mainRestController;

import com.icia.recipe.service.mainService.MainSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
public class MainSearchController {

    @Autowired
    MainSearchService cSer;

    @GetMapping("/common/main/search")
    public String mainSearchList(@RequestParam("value") String value, Model model) {
        log.info("[메인 검색] 컨트롤러 진입");
        List<?> mainSearchList = cSer.getAllTableList(value);
        model.addAttribute("mainSearchList", mainSearchList);
        return "main/common/mainsearch";

    }


}
