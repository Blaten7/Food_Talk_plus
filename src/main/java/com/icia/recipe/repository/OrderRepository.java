package com.icia.recipe.repository;

import com.icia.recipe.entity.Order;
import com.icia.recipe.management.dto.DeliveryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // SELECT
    @Query(value = "SELECT MAX(o_num) FROM order1 WHERE m_id = :dataId", nativeQuery = true)
    int getOrderNum(@Param("dataId") String dataId);

    @Query(value = "SELECT COUNT(*) FROM order1 WHERE o_delivery = 0", nativeQuery = true)
    Integer getTodayOrderCount();

    @Query(value = "SELECT COUNT(*) FROM order1 WHERE o_delivery = 1", nativeQuery = true)
    Integer getTodayOrderDelivering();

    @Query(value = "SELECT COUNT(*) FROM order1 WHERE o_delivery = 2 " +
            "AND o_date BETWEEN DATE_SUB(CURDATE(), INTERVAL 7 DAY) AND CURDATE()",
            nativeQuery = true)
    Integer getWeekOrderDelivery();

    @Query(value = "SELECT COUNT(*) FROM order1 WHERE o_delivery = 0 " +
            "AND o_date BETWEEN DATE_SUB(CURDATE(), INTERVAL 7 DAY) AND CURDATE()",
            nativeQuery = true)
    Integer getWeekOrderCount();

    @Query(value = "SELECT COUNT(*) FROM order1", nativeQuery = true)
    Integer getOrderCount();

    @Query(value = "SELECT COUNT(*) FROM order1 WHERE DATE_FORMAT(o_date, '%Y-%m') = :month " +
            "AND o_delivery = 2",
            nativeQuery = true)
    Integer getMonthlyDelivery(@Param("month") String month);

    @Query(value = "SELECT COUNT(*) FROM order1 WHERE o_delivery = 1 " +
            "AND o_date BETWEEN DATE_SUB(CURDATE(), INTERVAL 7 DAY) AND CURDATE()",
            nativeQuery = true)
    Integer getWeekDelivering();

    @Query(value = "SELECT * FROM order1 WHERE o_delivery = 0", nativeQuery = true)
    List<DeliveryDto> getOrderList();

    @Query(value = "SELECT * FROM order1 WHERE o_delivery = 1", nativeQuery = true)
    List<DeliveryDto> getOrderList2();

    @Query(value = "SELECT COUNT(*) FROM order1 WHERE o_delivery = 2", nativeQuery = true)
    Integer getTotalOrder();

    @Query(value = "SELECT o.o_num, o.o_total, o.o_count, f.f_title, i.i_sys_name, i.i_path, " +
            "MIN(f.f_num) AS f_num, f.f_num, o.o_delivery " +
            "FROM order1 o JOIN orderdetail od ON od.o_num = o.o_num " +
            "JOIN fooditem f ON f.f_num = od.f_num " +
            "JOIN img i ON i.f_num = f.f_num " +
            "JOIN (SELECT MIN(i_num) AS min_inum, f_num FROM img GROUP BY f_num) min_img " +
            "ON min_img.min_inum = i.i_num " +
            "WHERE o.m_id = :dataId GROUP BY o.o_num " +
            "ORDER BY o.o_num DESC LIMIT :startIdx, :listCnt",
          nativeQuery = true)
    List<Order> selectOrder(@Param("dataId") String dataId,
                            @Param("startIdx") int startIdx,
                            @Param("listCnt") int listCnt);

    @Query(value = "SELECT COUNT(*) FROM order1 WHERE m_id = :id", nativeQuery = true)
    Integer getOrderCount(@Param("id") String id);

    @Query(value = "SELECT SUM(o_total) FROM order1 WHERE o_date = CURDATE()", nativeQuery = true)
    Integer getTodayProfitCount();

    @Query(value = "SELECT SUM(o_total) FROM order1 WHERE o_date BETWEEN DATE_SUB(CURDATE(), INTERVAL 7 DAY) AND CURDATE()", nativeQuery = true)
    Integer getWeekProfitCount();

    @Query(value = "SELECT COALESCE(SUM(o_total), 0) FROM order1 WHERE DATE_FORMAT(o_date, '%Y-%m') = :month", nativeQuery = true)
    Integer getMonthlyProfit(@Param("month") String month);



    // INSERT
    @Modifying
    @Query(value = "INSERT INTO order1 (o_num, m_id, o_total, o_date, o_name, o_address, o_phone, o_count, o_delivery, o_post) " +
            "VALUES (NULL, :dataId, :price, DEFAULT, :name, CONCAT(:addr1, '_', :addr2), " +
            "CONCAT('010-', :phone1, '-', :phone2), :count, DEFAULT, :post)",
            nativeQuery = true)
    void insertOrder(@Param("dataId") String dataId,
                     @Param("price") String price,
                     @Param("name") String name,
                     @Param("addr1") String addr1,
                     @Param("addr2") String addr2,
                     @Param("phone1") String phone1,
                     @Param("phone2") String phone2,
                     @Param("count") int count,
                     @Param("post") String post);


    // UPDATE
    @Modifying
    @Query(value = "UPDATE order1 SET o_delivery = 1 WHERE o_delivery = 0 AND o_num IN :orderNumbers",
            nativeQuery = true)
    void startDelivery(@Param("orderNumbers") List<Long> orderNumbers);

    @Modifying
    @Query(value = "UPDATE order1 SET o_delivery = 2 WHERE o_delivery = 1 AND o_num IN :orderNumbers",
            nativeQuery = true)
    void endDelivery(@Param("orderNumbers") List<Long> orderNumbers);




    // DELETE


}
