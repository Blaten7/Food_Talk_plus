package com.icia.recipe.repository;

import com.icia.recipe.dto.mainDto.FooditemDto;
import com.icia.recipe.dto.mainDto.ItemListDto;
import com.icia.recipe.dto.mainDto.SearchDto;
import com.icia.recipe.dto.manageDto.FoodItemDto;
import com.icia.recipe.entity.FoodItem;
import jakarta.annotation.Nullable;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {

    // SELECT
    @Query(value = "select * from FoodItem f " +
            "join img i " +
            "on f.f_num = i.f_num " +
            "where f.status = 1 " +
            "order by f.f_num desc", nativeQuery = true)
    List<FoodItem> getFoodItemImgList();

    @Query(value = "SELECT * " +
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
    List<FoodItem> getFoodItemListByTrCode(
            @Param("trCode") String trCode
    );

    @Query(value = "SELECT f.f_num, f.c_num, f.c_num2, f.f_title, f.f_price, f.f_count, f.f_date, " +
            "f.f_edate, f.f_views, f.f_code, f.f_volume, f.f_origin, f.f_cal, f.f_save " +
            "FROM fooditem f " +
            "JOIN category c ON f.c_num = c.c_num " +
            "WHERE f.f_num = :fnum AND f.status = 1", nativeQuery = true)
    List<FoodItemDto> getModalDetailsInfoUpdateBeforeList(@Param("fnum") String fnum);


//    @Query(value = "select * from fooditem " +
//            "where status = 1", nativeQuery = true)
//    List<FoodItemDto> getFoodItemList();

    @Query(value = "SELECT f.f_num, i.i_path, i.i_sys_name, f.f_title, FORMAT(f.f_price, 0) AS f_price, " +
            "f.f_views, c1.c_num AS c1_num, c2.c_num AS c2_num, c3.c_num AS c3_num, i.i_num " +
            "FROM fooditem f " +
            "LEFT JOIN img i ON i.f_num = f.f_num " +
            "LEFT JOIN (SELECT MIN(i_num) min_inum, f_num FROM img GROUP BY f_num) img " +
            "ON img.f_num = i.f_num AND img.min_inum = i.i_num " +
            "JOIN category c1 ON c1.c_num = f.c_num " +
            "LEFT JOIN category c2 ON c1.c_num = c2.c_num2 " +
            "LEFT JOIN category c3 ON c2.c_num = c3.c_num2 " +
            "WHERE f.status = 1 " +
            "AND (:numName IS NULL OR :numName != 'zzzz' OR :numName = :sDtoDataNum) " +
            "GROUP BY f.f_num " +
            "ORDER BY :#{#sDto.data.num} =:sDtoDataSort " +
            "LIMIT :sDtoStartIdx, :sDtoListCnt", nativeQuery = true)
    List<FooditemDto> searchFoodItem(@Param("sDto")SearchDto sDto,
                                  @Param("numName") String numName);

    @Query(value = "SELECT COUNT(*) FROM fooditem WHERE status = 1 AND (:num IS NULL OR c_num = :num)", nativeQuery = true)
    int getFooditemCount(@Param("num") String num);


    @Query(value = "SELECT c1.c_name AS c1_name, f.f_volume, FORMAT(f.f_price, 0) AS f_price, f.f_cal, f.f_save, " +
            "i.i_path, i.i_sys_name, f.f_contents, f.f_title, f.f_date, " +
            "c1.c_num AS c1_num, f.f_count " +
            "FROM fooditem f " +
            "LEFT JOIN img i ON f.f_num = i.f_num " +
            "JOIN category c1 ON f.c_num = c1.c_num " +
            "WHERE f.f_num = :num",
            nativeQuery = true)
    List<FooditemDto> searchFoodDetail(@Param("num") String num);

    @Query(value = "SELECT f_contents FROM fooditem WHERE f_num = :num", nativeQuery = true)
    List<FooditemDto> searchFoodDetailInfo(@Param("num")String num);

    @Query(value = "SELECT COUNT(*) FROM fooditem WHERE status = 1 " +
            "AND (:num IS NULL OR :num != 'no' OR c_num = :num)",
            nativeQuery = true)
    Integer getFoodItemCount(@Param("num") String num);

    @Nullable
    @Query(value = "select * from FoodItem where status = 0", nativeQuery = true)
    List<FoodItem> deletedFooItemlist();

    @Query(value = "SELECT MIN(f.f_date) AS oldest_date, MAX(f.f_date) AS recent_date, " +
            "MIN(f.f_edate) AS oldest_edate, MAX(f.f_edate) AS recent_edate, " +
            "c.c_name, c.c_num, c.c_num2, f.f_code, f.f_title, " +
            "TRUNCATE(SUM(f.f_count), 0) AS countSum, " +
            "FORMAT(TRUNCATE(SUM(f.f_price) / COUNT(*), 0), 0) AS avgPrice, " +
            "FORMAT(SUM(f.f_count) * TRUNCATE(SUM(f.f_price) / COUNT(*), 0), 0) AS total " +
            "FROM fooditem f JOIN category c ON f.c_num = c.c_num " +
            "WHERE f.status = 1 GROUP BY c.c_num, c.c_num2, c.c_name, f.f_title",
            nativeQuery = true)
    List<FoodItemDto> getInvenList();

    @Query(value = "SELECT MIN(f.f_date) AS oldest_date, MAX(f.f_date) AS recent_date, " +
            "MIN(f.f_edate) AS oldest_edate, MAX(f.f_edate) AS recent_edate, " +
            "c.c_name, f.f_code, f.f_title, TRUNCATE(SUM(f.f_count), 0) AS countSum, " +
            "FORMAT(TRUNCATE(SUM(f.f_price) / COUNT(*), 0), 0) AS avgPrice, " +
            "FORMAT(SUM(f.f_count) * TRUNCATE(SUM(f.f_price) / COUNT(*), 0), 0) AS total " +
            "FROM fooditem f JOIN category c ON f.c_num = c.c_num " +
            "WHERE f.status = 1 GROUP BY c.c_num, c.c_num2, c.c_name, f.f_title " +
            "ORDER BY :param =:sort",
            nativeQuery = true)
    List<FoodItemDto> getSortedInvenList(@Param("param") String param,
                                      @Param("sort") String sort);


    @Query(value = "SELECT f.f_num, f.c_num, f.c_num2, f.f_title, f.f_price, f.f_count, " +
            "f.f_date, f.f_edate, f.f_views, f.f_code, f.f_volume, f.f_origin, f.f_cal, f.f_save " +
            "FROM fooditem f JOIN category c ON f.c_num = c.c_num " +
            "WHERE f.f_title = :title AND f.f_code = :code AND c.c_name = :cgName AND c.c_num = :bigCgNum " +
            "AND f.status = 1",
            nativeQuery = true)
    List<FooditemDto> getFoodItemList(@Param("title") String title,
                                   @Param("code") String code,
                                   @Param("cgName") String cgName,
                                   @Param("bigCgNum") String bigCgNum);

    @Query(value = "select * from fooditem", nativeQuery = true)
    List<FoodItemDto> getFooditemList();


    @Query(value = "SELECT f.f_num, f.c_num, f.c_num2, f.f_title, f.f_price, f.f_count, " +
            "f.f_date, f.f_edate, f.f_views, f.f_code, f.f_volume, f.f_origin, f.f_cal, f.f_save " +
            "FROM fooditem f JOIN category c ON c.c_num = f.c_num WHERE f.status = 1 " +
            "GROUP BY f.c_num, f.c_num2, f.f_title HAVING SUM(f_count) = 0",
            nativeQuery = true)
    List<FoodItemDto> emptyFoodItem();

    @Query(value = "SELECT f_num, c_num, c_num2, f_title, f_price, f_count, f_date, f_edate, " +
            "f_views, f_code, f_volume, f_origin, f_cal, f_save FROM fooditem WHERE status = 0",
            nativeQuery = true)
    List<FoodItemDto> getDeleteFooditemList();

//    @Query("SELECT " +
//            "f.fooditem_num, f.foodItem_Title, f.foodItem_price, f.foodItem_Views, f.foodItem_Code, " +
//            "f.foodItem_Volume, f.foodItem_Origin, f.foodItem_Cal, f.foodItem_Save, " +
//            "i.img_path, i.img_sys_name, i.img_origin_name " +
//            "FROM FoodItem f JOIN Img i " +
//            "ON f.fooditem_num = i.img_num " +
//            "WHERE f.status = 1 " +
//            "ORDER BY f.foodItem_Views DESC")
//    List<FoodItem> getRanking();
    @Query(value = "select * from FoodItem f " +
            "join img i " +
            "on i.f_num = f.f_num where f.status = 1 " +
            "order by f.f_views desc", nativeQuery = true)
    List<Object[]> getRanking();




    @Query(value = "SELECT f.f_num, f.f_title, f.f_price, f.f_views, f.f_code, f.f_volume, " +
            "f.f_origin, f.f_cal, f.f_save, i.i_path, i.i_sys_name, i.i_original_name " +
            "FROM fooditem f JOIN img i ON f.f_num = i.f_num " +
            "WHERE f.status = 1 ORDER BY f.f_views DESC LIMIT 50",
            nativeQuery = true)
    List<FoodItem> getRanking50();

    @Query(value = "SELECT MIN(f.f_date) AS f_date, MAX(f.f_date) AS f_date2, " +
            "MIN(f.f_edate) AS f_edate, MAX(f.f_edate) AS f_edate2, " +
            "c.c_name, f.f_code, f.f_title, " +
            "TRUNCATE(SUM(f.f_count), 0) AS f_count, " +
            "FORMAT(TRUNCATE(SUM(f.f_price) / COUNT(*), 0), 0) AS f_price, " +
            "FORMAT(SUM(f.f_count) * TRUNCATE(SUM(f.f_price) / COUNT(*), 0), 0) AS total " +
            "FROM fooditem f JOIN category c ON f.c_num = c.c_num " +
            "WHERE c.c_name = :cname AND f.f_code = :code AND f.f_title LIKE :name",
            nativeQuery = true)
    List<FoodItem> getSearchModalDetails(@Param("cname") String cname,
                                         @Param("code") String code,
                                         @Param("name") String name);

    @Query(value = "SELECT f_num, c_num, c_num2, f_title, f_price, f_count, f_date, f_edate, " +
            "f_views, f_code, f_volume, f_origin, f_cal, f_save " +
            "FROM fooditem " +
            "WHERE status = 1", nativeQuery = true)
    List<FoodItemDto> getFoodItemList2();

    @Query(value = "SELECT f.f_num, f.c_num, f.c_num2, f.f_title, f.f_price, f.f_count, f.f_date, " +
            "f.f_edate, f.f_views, f.f_code, f.f_volume, f.f_origin, f.f_cal, f.f_save, " +
            "i.i_path, i.i_sys_name " +
            "FROM fooditem f " +
            "JOIN category c ON f.c_num = c.c_num " +
            "JOIN img i ON f.f_num = i.f_num " +
            "WHERE f.f_num = :trCode AND f.status = 1", nativeQuery = true)
    List<FoodItemDto> getModalFIDetails(@Param("trCode") String trCode);





    // INSERT
    @Modifying
    @Query(value = "INSERT INTO fooditem (c_num, c_num2, f_title, f_contents, f_price, f_count, f_edate, f_code, f_volume, f_origin, f_cal, f_save) " +
            "VALUES (:#{#fDto.c_num}, :#{#fDto.c_num2}, :#{#fDto.f_title}, :#{#fDto.f_contents}, :#{#fDto.f_price}," +
            " :#{#fDto.f_count}, :#{#fDto.f_edate}, :#{#fDto.f_code}, :#{#fDto.f_volume}, :#{#fDto.f_origin}, :#{#fDto.f_cal}, :#{#fDto.f_save})",
            nativeQuery = true)
    boolean insertFoodItem(@Param("fDto")FoodItemDto fDto);


    // UPDATE
    @Modifying
    @Query(value = "UPDATE fooditem SET f_count = (f_count - :#{#i.dvCartCount}) WHERE f_num = :#{#i.dvCartDetlId}", nativeQuery = true)
    boolean updateFooditemCount(@Param("i")ItemListDto i);

    @Modifying
    @Query(value = "UPDATE fooditem SET f_views = f_views + 1 WHERE f_num = :num", nativeQuery = true)
    void incrementViews(@Param("num") String num);



    @Modifying
    @Query(value = "UPDATE FoodItem f " +
            "SET f.status = 4 " +
            "WHERE f.status = 0 " +
            "AND f.foodItem_Code IN :deleteKeys")
    boolean permanentDeleteFoodItem(
            @Param("deleteKeys") List<String> deleteKeys
    );

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
    boolean updateAndGetModalDetailsInfo(
            @Param("u_code") String uCode,
            @Param("u_cnum") String uCnum,
            @Param("u_cnum2") String uCnum2,
            @Param("u_price") String uPrice,
            @Param("u_count") String uCount,
            @Param("u_origin") String uOrigin,
            @Param("u_save") String uSave,
            @Param("u_cal") String uCal,
            @Param("c_cnum") String cCnum,
            @Param("c_cnum2") String cCnum2,
            @Param("c_ftitle") String cFtitle);

    @Modifying
    @Query(value = "UPDATE fooditem SET status = 0 WHERE f_code IN :deleteKeys", nativeQuery = true)
    boolean updateFoodItem(@Param("deleteKeys") List<String> deleteKeys);

    @Modifying
    @Query(value = "update FoodItem set f_views = fooditem.f_views +1 where f_num = :num", nativeQuery = true)
    Throwable viewsPlus(String num);

    // DELETE


    @Modifying
    @Query(value = "DELETE FROM fooditem WHERE f_code = :code AND f_title = :title AND c_num = :bigCgNum", nativeQuery = true)
    void deleteFromFoodItem(@Param("code") String code,
                            @Param("title") String title,
                            @Param("bigCgNum") String bigCgNum);





}
