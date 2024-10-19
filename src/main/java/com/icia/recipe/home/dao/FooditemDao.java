package com.icia.recipe.home.dao;

import com.icia.recipe.home.dto.FooditemDto;
import com.icia.recipe.home.dto.CtgDto;
import com.icia.recipe.home.dto.SearchDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FooditemDao {
    List<FooditemDto> searchFooditem(@Param("sDto") SearchDto sDto,@Param("numName") String numName);

    List<CtgDto> searchCtg();

    List<FooditemDto> searchFoodDetail(@Param("num") String num);

    List<FooditemDto> searchFoodDetailInfo(@Param("num") String num);

    boolean viewsPlus(String num);

    int getFooditemCount(String num);

    /*List<FooditemDto> searchctgFoodtitem(@Param("numName") String numName,@Param("cNum") String cNum);*/
}
