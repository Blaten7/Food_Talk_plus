package com.icia.recipe.service.mainService;

import com.icia.recipe.dto.mainDto.CtgDto;
import com.icia.recipe.dto.mainDto.FooditemDto;
import com.icia.recipe.dto.mainDto.TradeDto;
import com.icia.recipe.dto.mainDto.TradeItemDto;
import com.icia.recipe.dto.manageDto.FoodItemDto;
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

    @Autowired
    CategoryRepository cr;
    @Autowired
    FoodItemRepository fr;
    @Autowired
    TradeRepository tr;



    public List<?> getAllTableList(String value) {
        List<Object[]> categoryList = cr.getCategoryList();
        List<Object[]> fooditemList = fr.getFooditemList();
        List<Object[]> tradeList = tr.getTradeList();
        List<Object[]> tradeItemList = tr.getTradeItemList();
        String[] values;

        if (value.contains(" ")) {
            values = value.split(" ");
        } else if (value.contains(", ")) {
            values = value.split(", ");
        } else {
            values = null;
        }

        if (values != null) {
            List<Object[]> cgFilterList = categoryList.stream()
                    .filter(cg ->
                            Arrays.stream(values).anyMatch(val ->
                                    ((String) cg[0]).contains(val) || // c_num
                                            ((String) cg[1]).contains(value)) // c_name
                    ).toList();

            List<Object[]> fiFilterList = fooditemList.stream()
                    .filter(fi ->
                            Arrays.stream(values).anyMatch(val ->
                                    ((String) fi[1]).contains(val) || // f_title
                                            ((String) fi[3]).contains(val) || // f_price
                                            ((String) fi[4]).contains(val) || // f_count
                                            ((String) fi[5]).contains(val) || // f_date
                                            ((String) fi[6]).contains(val))   // f_edate
                    ).toList();

            List<Object[]> tFilterList = tradeList.stream()
                    .filter(t ->
                            Arrays.stream(values).anyMatch(val ->
                                    ((String) t[2]).contains(val) || // m_name
                                            ((String) t[6]).contains(val))   // t_title
                    ).toList();

            List<Object[]> tiFilterList = tradeItemList.stream()
                    .filter(ti ->
                            Arrays.stream(values).anyMatch(val ->
                                    ((String) ti[2]).contains(val) || // t_item
                                            ((String) ti[5]).contains(val))   // t_change
                    ).toList();

            List<List<?>> allList = new ArrayList<>();
            allList.add(cgFilterList);
            allList.add(fiFilterList);
            allList.add(tFilterList);
            allList.add(tiFilterList);
            return allList;

        } else {
            List<Object[]> cgFilterList = categoryList.stream()
                    .filter(cg ->
                            ((String) cg[0]).contains(value) || // c_num
                                    ((String) cg[1]).contains(value))   // c_name
                .toList();

            List<Object[]> fiFilterList = fooditemList.stream()
                    .filter(fi ->
                            ((String) fi[1]).contains(value) || // f_title
                                    ((String) fi[3]).contains(value) || // f_price
                                    ((String) fi[4]).contains(value) || // f_count
                                    ((String) fi[5]).contains(value) || // f_date
                                    ((String) fi[6]).contains(value))   // f_edate
                .toList();

            List<Object[]> tFilterList = tradeList.stream()
                    .filter(t ->
                            ((String) t[2]).contains(value) || // m_name
                                    ((String) t[6]).contains(value))   // t_title
                .toList();

            List<Object[]> tiFilterList = tradeItemList.stream()
                    .filter(ti ->
                            ((String) ti[2]).contains(value) || // t_item
                                    ((String) ti[5]).contains(value))   // t_change
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
