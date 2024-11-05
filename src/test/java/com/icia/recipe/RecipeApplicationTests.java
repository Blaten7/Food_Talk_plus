package com.icia.recipe;

import com.icia.recipe.repository.FoodItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@SpringBootTest
class RecipeApplicationTests {

    FoodItemRepository fr;

    @Test
    void String Object tradeDetail(Model model, @RequestParam("t_num") Integer t_num) {
        List<Object[]> tDList = tSer.tradeDetail(t_num);
        int tNum = tDList.get(0).getT_num();
        String t_title = tDList.get(0).getT_title();
        String m_id = tDList.get(0).getM_id();
        String t_item=tDList.get(0).getT_item();
        int t_itemcount=tDList.get(0).getT_itemcount();
        String t_unit=tDList.get(0).getT_unit();
        String t_change=tDList.get(0).getT_change();
        String m_name = tSer.getMemberName(m_id);

        log.info(">>>>>m_id:{}", m_id);
        model.addAttribute("m_id", m_id);
        model.addAttribute("m_name", m_name);
        model.addAttribute("t_num", tNum);
        model.addAttribute("tDList", tDList);
        model.addAttribute("t_title", t_title);
        model.addAttribute("t_item", t_item);
        model.addAttribute("t_itemcount", t_itemcount);
        model.addAttribute("t_unit", t_unit);
        model.addAttribute("t_change", t_change);
        return "/main/trade/tradeDetail";
    }

}
