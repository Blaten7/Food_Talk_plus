package com.icia.recipe.repository;

import com.icia.recipe.dto.mainDto.CartDto;
import com.icia.recipe.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    // SELECT
    @Query(value = "SELECT ca.ca_num, ca.m_id, ca.ca_count, f.f_title, f.f_price, ca.ca_date, i.i_path, i.i_sys_name, " +
            "f.f_num, f.f_count, c.c_name " +
            "FROM cart ca " +
            "JOIN fooditem f ON f.f_num = ca.f_num " +
            "JOIN img i ON f.f_num = i.f_num " +
            "JOIN (SELECT MIN(i_num) min_inum, f_num FROM img GROUP BY f_num) min ON min.min_inum = i.i_num " +
            "JOIN category c ON c.c_num = f.c_num " +
            "WHERE ca.m_id = :name",
            nativeQuery = true)
    List<Object[]> selectCart(@Param("name") String name);

    @Query(value = "SELECT COUNT(*) FROM cart WHERE m_id = :mId", nativeQuery = true)
    String selectCartCount(@Param("mId") String mId);


    // INSERT
    @Modifying
    @Query(value = "INSERT INTO cart VALUES (DEFAULT, :user, :num, :count, DEFAULT)", nativeQuery = true)
    int insertCartList(@Param("user") String user,
                        @Param("num") String num,
                        @Param("count") String count);


    // UPDATE


    // DELETE
    @Modifying
    @Query(value = "DELETE FROM cart WHERE ca_num = :e", nativeQuery = true)
    int deleteCart(@Param("e") Long e);

    @Modifying
    @Query(value = "DELETE FROM cart WHERE m_id = :id", nativeQuery = true)
    void deleteCartByName(@Param("id") String id);

    @Modifying
    @Query(value = "DELETE FROM CART WHERE M_ID = :id", nativeQuery = true)
    int deleteCartName(@Param("id") String id);
}
