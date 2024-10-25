package com.icia.recipe.repository;

import com.icia.recipe.entity.Order;
import com.icia.recipe.entity.Order_Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<Order_Detail, Long> {

    // SELECT


    // INSERT
    @Modifying
    @Query(value = "INSERT INTO orderDetail (o_num, f_num, o_unit) VALUES (:oNum, :dvCartDetlId, :dvCartCount)",
            nativeQuery = true)
    void insertOrderDetail(@Param("oNum") int oNum,
                           @Param("dvCartDetlId") Long dvCartDetlId,
                           @Param("dvCartCount") int dvCartCount);

    @Query(value = "SELECT o.o_total AS o_total, o.o_count, f.f_title, i.i_sys_name, i.i_path, " +
            "o.o_num, od.o_unit, f.f_num, f.f_price, o.o_num " +
            "FROM order1 o JOIN orderdetail od ON od.o_num = o.o_num " +
            "JOIN fooditem f ON f.f_num = od.f_num " +
            "JOIN img i ON i.f_num = f.f_num " +
            "JOIN (SELECT MIN(i_num) min_inum, f_num FROM img GROUP BY f_num) min " +
            "ON min.min_inum = i.i_num " +
            "WHERE o.o_num = :num ORDER BY o.o_num",
            nativeQuery = true)
    List<Order> selectOrderDetail(@Param("num") int num);


    // UPDATE


    // DELETE
}
