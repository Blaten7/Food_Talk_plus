package com.icia.recipe.home.restController;

import com.icia.recipe.home.service.CareService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RestCareController {
    @Autowired
    CareService cSer;

}
