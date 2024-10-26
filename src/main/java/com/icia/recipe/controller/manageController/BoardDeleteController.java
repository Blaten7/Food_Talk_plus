package com.icia.recipe.controller.manageController;

import com.icia.recipe.management.dto.FoodItemDto;
import com.icia.recipe.service.manageService.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController //@ResponseBody생략 가능
public class BoardDeleteController {
    @Autowired
    private BoardService bSer;

    @Secured("ROLE_ADMIN")
    @GetMapping("/delete/category")
    public List<?> deleteCategory(@RequestParam("name") String name, @RequestParam("cg") String cg) {
        return bSer.deleteCategory(name, cg);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/boardlist/delete")
    public List<?> deleteBoard(@RequestParam("deleteKeySet") ArrayList deleteKey,
                               @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        log.info("[게시글 삭제] {}", deleteKey);
        return bSer.deleteFoodItemList(deleteKey, pageNum, pageSize);
    }
    @Secured("ROLE_ADMIN")
    @GetMapping("/permanent/delete")
    public List<FoodItemDto> perDelete(@RequestParam("deleteKeySet") ArrayList deleteKey) {
        return bSer.permanentDelte(deleteKey);
    }


}