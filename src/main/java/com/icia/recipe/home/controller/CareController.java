package com.icia.recipe.home.controller;

import com.icia.recipe.home.service.CareService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
