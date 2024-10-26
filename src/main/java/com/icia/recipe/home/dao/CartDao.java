//package com.icia.recipe.home.dao;
//
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Param;
//import org.springframework.security.core.parameters.P;
//
//import java.util.HashMap;
//import java.util.List;
//
//@Mapper
//public interface CartDao {
//
//    int insertCartList(@Param("num") String num, @Param("count") String count, @Param("user") String user);
//
//    List<CartDto> selectCart(String name);
//
//    int deleteCart(Object e);
//
//    boolean insertOrderDetail(HashMap<String, String> e);
//
//    int deleteCartName(String id);
//
//    boolean insertOrderDetail();
//
//    boolean insertOrder(InputListDto inputListDto);
//
//    boolean updateFooditemCount(ItemListDto i);
//
//    String selectCartCount(String mId);
//}
