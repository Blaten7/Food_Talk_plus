package com.icia.recipe.service.mainService;

import com.icia.recipe.dto.mainDto.CtgDto;
import com.icia.recipe.dto.mainDto.FooditemDto;
import com.icia.recipe.dto.mainDto.TradeDto;
import com.icia.recipe.dto.mainDto.TradeItemDto;
import com.icia.recipe.repository.CategoryRepository;
import com.icia.recipe.repository.FoodItemRepository;
import com.icia.recipe.repository.TradeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class MainSearchService {

//    @Autowired
//    CommonDao cDao;
    @Autowired
    CategoryRepository cr;
    @Autowired
    FoodItemRepository fr;
    @Autowired
    TradeRepository tr;



    public List<?> getAllTableList(String value) {
        List<CtgDto> categoryList = cr.getCategoryList();
        List<FooditemDto> fooditemList = fr.getFooditemList();
        List<TradeDto> tradeList = tr.getTradeList();
        List<TradeItemDto> tradeItemList = tr.getTradeItemList();
        String[] values;
        if (value.contains(" ")) {
            values = value.split(" ");
        } else if (value.contains(", ")) {
            values = value.split(", ");
        } else {
            values = null;
        }
        if (values != null) {
            List<CtgDto> cgFilterList = categoryList.stream()
                    .filter(cg ->
                            Arrays.stream(values).anyMatch(val -> cg.getC_num().contains(val)) ||
                                    cg.getC_name().contains(value))
                    .toList();
            List<FooditemDto> fiFilterList = fooditemList.stream()
                    .filter(fi ->
                            Arrays.stream(values).anyMatch(val ->
                                    fi.getF_title().contains(val) ||
                                            fi.getF_price().contains(val) ||
                                            fi.getF_count().contains(val) ||
                                            fi.getF_date().contains(val) ||
                                            fi.getF_edate().contains(val)
                            )
                    ).toList();

            List<TradeDto> tFilterList = tradeList.stream()
                    .filter(t ->
                            Arrays.stream(values).anyMatch(val ->
                                    t.getM_name().contains(val) ||
                                            t.getT_title().contains(val)
                            )
                    ).toList();

            List<TradeItemDto> tiFilterList = tradeItemList.stream()
                    .filter(ti ->
                            Arrays.stream(values).anyMatch(val ->
                                    ti.getT_item().contains(val) ||
                                            ti.getT_change().contains(val)
                            )
                    ).toList();

            List<List<?>> allList = new ArrayList<>();
            allList.add(cgFilterList);
            allList.add(fiFilterList);
            allList.add(tFilterList);
            allList.add(tiFilterList);
            return allList;

        } else {
            List<CtgDto> cgFilterList = categoryList.stream()
                    .filter(cg ->
                            cg.getC_num().contains(value) ||
                                    cg.getC_name().contains(value))
                    .toList();
            List<FooditemDto> fiFilterList = fooditemList.stream()
                    .filter(fi ->
                            fi.getF_title().contains(value) ||
                                    fi.getF_price().contains(value) ||
                                    fi.getF_count().contains(value) ||
                                    fi.getF_date().contains(value) ||
                                    fi.getF_edate().contains(value))
                    .toList();
            List<TradeDto> tFilterList = tradeList.stream()
                    .filter(t ->
                            t.getM_name().contains(value) ||
                                    t.getT_title().contains(value))
                    .toList();
            List<TradeItemDto> tiFilterList = tradeItemList.stream()
                    .filter(ti ->
                            ti.getT_item().contains(value) ||
                                    ti.getT_change().contains(value))
                    .toList();

            List<List<?>> allList = new ArrayList<>();
            allList.add(cgFilterList);
            allList.add(fiFilterList);
            allList.add(tFilterList);
            allList.add(tiFilterList);
            return allList;
        }

    }
}
