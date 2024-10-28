package com.icia.recipe.repository;

import com.icia.recipe.dto.manageDto.FoodItemDto;
import com.icia.recipe.dto.manageDto.InvenDto;
import com.icia.recipe.entity.InvenAdd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvenAddRepository extends JpaRepository<InvenAdd, Long> {

    // SELECT
    @Query(value = "SELECT iv_num, iv_company, iv_name, iv_count, FORMAT(iv_price, 0) AS iv_price, " +
            "FORMAT(iv_vat, 0) AS iv_vat, FORMAT(iv_total, 0) AS iv_total, iv_current, " +
            "FORMAT((iv_price + iv_vat), 0) AS iv_priceSum FROM invenAdd " +
            "ORDER BY iv_num DESC",
            nativeQuery = true)
    List<InvenDto> getInvenAddList();

    @Query(value = "SELECT iv_num, iv_company, iv_name, iv_count, FORMAT(iv_price, 0) AS iv_price, " +
            "FORMAT(iv_vat, 0) AS iv_vat, FORMAT(iv_total, 0) AS iv_total, iv_current, " +
            "FORMAT((iv_price + iv_vat), 0) AS iv_priceSum FROM invenAdd " +
            "ORDER BY :param :sort",
            nativeQuery = true)
    List<InvenDto> getInvenAddListSort(@Param("param") String param,
                                       @Param("sort") String sort);

    @Query(value = "SELECT MIN(f.f_date) AS f_date, " +
            "MAX(f.f_date) AS f_date2, " +
            "MIN(f.f_edate) AS f_edate, " +
            "MAX(f.f_edate) AS f_edate2, " +
            "c.c_name AS c_name, " +
            "f.f_code AS f_code, " +
            "f.f_title AS f_title, " +
            "TRUNCATE(SUM(f.f_count), 0) AS f_count, " +
            "FORMAT(TRUNCATE(SUM(f.f_price) / COUNT(*), 0), 0) AS f_price, " +
            "FORMAT(SUM(f.f_count) * TRUNCATE(SUM(f.f_price) / COUNT(*), 0), 0) AS total " +
            "FROM fooditem f " +
            "JOIN category c ON f.c_num = c.c_num " +
            "WHERE c.c_name = :cname " +
            "AND f.f_code = :code " +
            "AND f.f_title LIKE :name", nativeQuery = true)
    List<FoodItemDto> getSearchModalDetails(@Param("cname") String cname,
                                            @Param("code") String code,
                                            @Param("name") String name);


    @Query(value = "SELECT iv_company, iv_name, (iv_price * 0.1) AS iv_vat, (iv_price * 0.9) AS iv_price, " +
            "iv_count, iv_current, iv_price AS iv_priceSum, iv_total FROM invenAdd",
            nativeQuery = true)
    List<InvenAdd> getInvenAddList2();



    // INSERT
    @Modifying
    @Query(value = "INSERT INTO invenAdd (iv_company, iv_name, iv_count, iv_price, iv_vat, iv_total, iv_current) " +
            "VALUES (:company, :name, :count, :price * 0.9, :price * 0.1, :price * :count, DEFAULT)",
            nativeQuery = true)
    boolean insertInvenAdd(@Param("company") String company,
                        @Param("name") String name,
                        @Param("count") String count,
                        @Param("price") String price);


    // UPDATE
    @Modifying
    @Query(value = "UPDATE invenAdd SET iv_current = '발주 완료' WHERE iv_current = '발주 전'", nativeQuery = true)
    boolean finalOrder();


    // DELETE
    @Modifying
    @Query(value = "delete from InvenAdd " +
            "where inven_add_num in :deleteKey")
    void deleteFoodItemlist(
            @Param("deleteKey") List<String> deleteKey
    );

    List<?> getSearchModalDetailsInven(String company, String iname);

    @Modifying
    @Query(value = "DELETE FROM invenAdd WHERE iv_num IN :deleteKeys", nativeQuery = true)
    boolean deleteFoodItemList(@Param("deleteKeys") List<Long> deleteKeys);


}
