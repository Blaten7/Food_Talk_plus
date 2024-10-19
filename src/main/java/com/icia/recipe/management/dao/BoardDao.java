package com.icia.recipe.management.dao;

import com.icia.recipe.management.dto.BoardDto;
import com.icia.recipe.management.dto.FoodItemDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface BoardDao {

    List<BoardDto> getRecipeList();

    List<BoardDto> getCgName();

    List<BoardDto> getFoodItemBigCg();

    List<BoardDto> getRecipeBigCg();

    List<BoardDto> getFoodItemMidCg(String cg);

    List<BoardDto> getRecipeMidCg(String cgNum);

    List<BoardDto> getFoodItemSmCg(String cg);

    List<BoardDto> getRecipeSmCg(String cgNum);

    List<BoardDto> getBoardTrade();

    boolean deleteCategory(String name, String cg);

    boolean insertFoodItem(String fiCode, String fiExDate, String fiCounts,
                                     String fiBigCg, String fiMidCg,
                                     String fiPrice, String fiContents, String fiTitle,
                                    String fiVolume, String fiOrigin, String fiCal, String fiSave);

    List<FoodItemDto> getFoodItemList();

    List<FoodItemDto> getSortedFoodItemList(String param, String sort);

    boolean addBigCg(String cgName, String cgNum);

    boolean deleteFoodItemList(ArrayList deleteKey);

    boolean insertFoodItemImg(Map<String, String> fiMap);

    String getFoodItemListNaming(String cNum);

    String getFoodItemListNaming2(String cNum2);

    Integer getFIListCnt();

    List<FoodItemDto> getModalFIDetails(String trCode);

    String getFiImg(String code);

    String getFIImg(String trCode);

    List<FoodItemDto> getModalDetailsInfoUpdateBeforeList(String fnum);

    List<FoodItemDto> getCategory();

    List<FoodItemDto> getCategory2();

    String getBigCgNum(String fiBigCg);

    String getMidCgNum(String fiMidCg);

    boolean updateAndGetModalDetailsInfo(Map<String, Object> params);

    List<FoodItemDto> getFoodItemList2();

    boolean permanentDeleteFoodItem(ArrayList deleteKey);

    List<FoodItemDto> deletedFoodItemList();

    boolean getMidSmCg(String cgName, String cgNum);
}
