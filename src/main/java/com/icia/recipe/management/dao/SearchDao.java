package com.icia.recipe.management.dao;

import com.icia.recipe.management.dto.BoardDto;
import com.icia.recipe.management.dto.FoodItemDto;
import com.icia.recipe.management.dto.InvenDto;
import com.icia.recipe.management.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SearchDao {
    List<FoodItemDto> getSearchModalDetails(String cname, String code, String name);

    List<InvenDto> getSearchModalDetailsInven(String company, String iname);

    List<FoodItemDto> getInvenList();

    List<InvenDto> getInvenAddList();

    List<BoardDto> getRecipeList();

    List<FoodItemDto> getFoodItemList();

    List<MemberDto> getMemberList();
}
