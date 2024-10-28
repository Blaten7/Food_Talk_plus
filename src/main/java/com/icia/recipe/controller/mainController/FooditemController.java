package com.icia.recipe.controller.mainController;

import com.icia.recipe.entity.FoodItem;
import com.icia.recipe.service.mainService.FooditemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
public class FooditemController {
    @Autowired
    FooditemService fSer;

//    @GetMapping("/fooditem/main")
//    public String foodItemMain(Model model, SearchDto sDto){
//        int pageCount = 20;
//        if (sDto.getPageNum() == null)
//            sDto.setPageNum(1);
//        if (sDto.getListCnt() == null)
//            sDto.setListCnt(pageCount);
//        if (sDto.getStartIdx() == null)
//            sDto.setStartIdx(0);
//        sDto.setStartIdx((sDto.getPageNum()-1)*sDto.getListCnt());
//        String listUrl = "javascript:paging(";
//        String a ="salePriceAsc";
//        String num = "no";
//        String list = fSer.fooditemOrder(a,num,sDto);
//        String cList = fSer.fooditemctg();
//        String pageHtml = fSer.getPaging(num,sDto,listUrl);
//        model.addAttribute("list",list);
//        model.addAttribute("cList",cList);
//        model.addAttribute("pageHtml",pageHtml);
//        return "main/fooditem/fooditemMain";
//    }
//
    @GetMapping("/fooditem/detail")
    public String foodItemDetail(@RequestParam("f_num") String num, Model model){
        log.info("searchFoodDetail 입장 {}",num);

        /*if (result<0) {
            model.addAttribute("msg","잘못된 요청입니다. 관리자에게 문의해 주세요");
            return "index";
        }*/
        List<FoodItem> list = fSer.searchFoodDetail(num , model);
        list.forEach(l ->{
            model.addAttribute("title",l.getFoodItem_Title());
            model.addAttribute("price",l.getFoodItem_price());
            model.addAttribute("volume",l.getFoodItem_Volume());
            model.addAttribute("cal",l.getFoodItem_Cal());
            model.addAttribute("save",l.getFoodItem_Save());
            model.addAttribute("date",l.getFoodItem_Date());
            model.addAttribute("c_name", l.getCartList().get(0).getC_name());
            model.addAttribute("count",l.getFoodItem_Count());
        });
            String info=fSer.fooditemDetailinfo(num,"foodInfo");
            model.addAttribute("info",info);
        return "main/fooditem/fooditemDetail";
    }
    @GetMapping("/fooditem/ranking")
    public String ranking(Model model) {
        List<FoodItem> fList = fSer.getRanking50();
        model.addAttribute("list", fList);
        return "main/fooditem/ranking";
    }

}
