package com.icia.recipe.controller.mainRestController;

import com.icia.recipe.service.mainService.CareService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RestCareController {
    @Autowired
    CareService cSer;

}
