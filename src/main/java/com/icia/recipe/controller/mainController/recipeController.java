package com.icia.recipe.controller.mainController;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class recipeController {
    @GetMapping("/recipe/main")
    public String recipeMain(){
        log.info("recipeMain 입장");
        return "main/recipe/recipeMain";
    }
}
