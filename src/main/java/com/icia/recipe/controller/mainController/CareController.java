package com.icia.recipe.controller.mainController;

import com.icia.recipe.service.mainService.CareService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class CareController {
    @Autowired
    CareService cSer;

    @GetMapping("/careMain")
    public String careMain(){
        return "main/care/careMain";
    }

    @GetMapping("/lowsugar")
    public String lowSugar(){
        String lowSugar=cSer.lowSugar();
        log.info(lowSugar);
        return "main/care/lowsugar";
    }
    @GetMapping("/cal")
    public String cal(){
        return "main/care/cal";
    }
    @GetMapping("/longlivetheking")
    public String longlivetheking(){
        return "main/care/longlivetheking";
    }
    @GetMapping("/protine")
    public String protine(){
        return "main/care/protine";
    }
    @GetMapping("/selfcare")
    public String selfcare(){
        return "main/care/selfcare";
    }
}
