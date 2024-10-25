package com.icia.recipe.repository;

import com.icia.recipe.entity.FoodItem;
import com.icia.recipe.management.dto.FoodItemDto;
import io.micrometer.common.lang.NonNullApi;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {

    // SELECT
    @Query(value = "select * from FoodItem f " +
            "join img i " +
            "on f.f_num = i.f_num " +
            "where f.status = 1 " +
            "order by f.f_num desc", nativeQuery = true)
    List<FoodItem> getFoodItemList();

    @Query(value = "SELECT f_num, c_num, c_num2, f_title, f_price, f_count, f_date, f_edate, f_views, f_code, f_volume, f_origin, f_cal, f_save " +
            "FROM fooditem " +
            "WHERE status = 1 " +
            "ORDER BY :param",
            nativeQuery = true)
    List<FoodItemDto> getSortedFoodItemList(
            @Param("param") String param
    );

    @Query(value = "select count(*) from FoodItem", nativeQuery = true)
    int getFoodItemListCount();

    @Query(value = "select * from fooditem f " +
            "join category c " +
            "on f.c_num = c.c_num " +
            "join img i " +
            "on f.f_num = i.f_num " +
            "where f.f_num = :trCode " +
            "and f.status = 1", nativeQuery = true)
    List<FoodItemDto> getFoodItemListByTrCode(
            @Param("trCode") String trCode
    );



    // INSERT


    // UPDATE


    // DELETE

    @Modifying
    @Query(value = "UPDATE fooditem f " +
            "JOIN category c ON f.c_num = c.c_num " +
            "SET f.f_code = COALESCE(:u_code, f.f_code), " +
            "f.c_num = COALESCE(:u_cnum, f.c_num), " +
            "f.c_num2 = COALESCE(:u_cnum2, f.c_num2), " +
            "f.f_price = COALESCE(:u_price, f.f_price), " +
            "f.f_count = COALESCE(:u_count, f.f_count), " +
            "f.f_origin = COALESCE(:u_origin, f.f_origin), " +
            "f.f_save = COALESCE(:u_save, f.f_save), " +
            "f.f_cal = COALESCE(:u_cal, f.f_cal) " +
            "WHERE f.c_num = :c_cnum " +
            "AND f.c_num2 = :c_cnum2 " +
            "AND f.f_title = :c_ftitle",
            nativeQuery = true)
    void updateAndGetModalDetailsInfo(
            @Param("u_code") String uCode,
            @Param("u_cnum") String uCnum,
            @Param("u_cnum2") String uCnum2,
            @Param("u_price") Integer uPrice,
            @Param("u_count") String uCount,
            @Param("u_origin") String uOrigin,
            @Param("u_save") String uSave,
            @Param("u_cal") String uCal,
            @Param("c_cnum") String cCnum,
            @Param("c_cnum2") String cCnum2,
            @Param("c_ftitle") String cFtitle);

    @Modifying
    @Query(value = "UPDATE FoodItem f " +
            "SET f.status = 4 " +
            "WHERE f.status = 0 " +
            "AND f.foodItem_Code IN :deleteKeys")
    void permanentDeleteFoodItem(
            @Param("deleteKeys") List<String> deleteKeys
    );

    @Nullable
    @Query(value = "select * from FoodItem where status = 0", nativeQuery = true)
    List<FoodItem> deletedFooItemlist();


}
