package com.icia.recipe.repository;

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
    List<InvenAdd> getInvenAddList();

    @Query(value = "SELECT iv_num, iv_company, iv_name, iv_count, FORMAT(iv_price, 0) AS iv_price, " +
            "FORMAT(iv_vat, 0) AS iv_vat, FORMAT(iv_total, 0) AS iv_total, iv_current, " +
            "FORMAT((iv_price + iv_vat), 0) AS iv_priceSum FROM invenAdd " +
            "ORDER BY :param :sort",
            nativeQuery = true)
    List<InvenAdd> getInvenAddListSort(@Param("param") String param,
                                       @Param("sort") String sort);

    @Query(value = "SELECT iv_num, iv_company, iv_name, iv_count, FORMAT(iv_price, 0) AS iv_price, " +
            "FORMAT(iv_vat, 0) AS iv_vat, FORMAT(iv_total, 0) AS iv_total, iv_current, " +
            "FORMAT((iv_price + iv_vat), 0) AS iv_priceSum FROM invenAdd " +
            "WHERE iv_company = :company AND iv_name = :iname",
            nativeQuery = true)
    List<InvenAdd> getSearchModalDetails(@Param("company") String company,
                                         @Param("iname") String iname);

    @Query(value = "SELECT iv_company, iv_name, (iv_price * 0.1) AS iv_vat, (iv_price * 0.9) AS iv_price, " +
            "iv_count, iv_current, iv_price AS iv_priceSum, iv_total FROM invenAdd",
            nativeQuery = true)
    List<InvenAdd> getInvenAddList2();



    // INSERT
    @Modifying
    @Query(value = "INSERT INTO invenAdd (iv_company, iv_name, iv_count, iv_price, iv_vat, iv_total, iv_current) " +
            "VALUES (:company, :name, :count, :price * 0.9, :price * 0.1, :price * :count, DEFAULT)",
            nativeQuery = true)
    void insertInvenAdd(@Param("company") String company,
                        @Param("name") String name,
                        @Param("count") int count,
                        @Param("price") double price);


    // UPDATE
    @Modifying
    @Query(value = "UPDATE invenAdd SET iv_current = '발주 완료' WHERE iv_current = '발주 전'", nativeQuery = true)
    void finalOrder();


    // DELETE
    @Modifying
    @Query(value = "delete from InvenAdd " +
            "where inven_add_num in :deleteKey")
    void deleteFoodItemlist(
            @Param("deleteKey") List<String> deleteKey
    );

}
