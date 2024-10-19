package com.icia.recipe.home.dao;

import com.icia.recipe.home.dto.CtgDto;
import com.icia.recipe.home.dto.FooditemDto;
import com.icia.recipe.home.dto.TradeDto;
import com.icia.recipe.home.dto.TradeItemDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommonDao {

    @Select("select * from category")
    List<CtgDto> getCategoryList();

    @Select("select f_num, c_num, c_num2, f_title, f_price, f_count, f_date, f_edate, f_views, f_code, f_volume, f_origin, f_cal, f_save from fooditem")
    List<FooditemDto> getFooditemList();

    @Select("select * from trade t join member m on t.m_id = m.m_id")
    List<TradeDto> getTradeList();

    @Select("select * from tradeitem")
    List<TradeItemDto> getTradeItemList();
}
