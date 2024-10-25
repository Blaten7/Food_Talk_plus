package com.icia.recipe.repository;

import com.icia.recipe.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {

    // SELECT
    @Query(value = "SELECT t_num, t_title, m_id, t_date, t_views, t_count, visible FROM trade", nativeQuery = true)
    List<Trade> tradeList();

    @Query(value = "SELECT t_num, t_title, m_id, t_date, t_views, t_count " +
            "FROM trade WHERE visible = 1 ORDER BY t_date",
            nativeQuery = true)
    List<Trade> tradeDateSort();

    @Query(value = "SELECT t_num, t_title, m_id, t_date, t_views, t_count " +
            "FROM trade WHERE visible = 1 ORDER BY t_views DESC",
            nativeQuery = true)
    List<Trade> tradeViewSort();

    @Query(value = "SELECT t_num, t_title, m_id, t_date, t_views, t_count " +
            "FROM trade WHERE visible = 1 ORDER BY t_count DESC",
            nativeQuery = true)
    List<Trade> tradeCountSort();

    @Query(value = "SELECT * FROM trade WHERE t_num = :tNum", nativeQuery = true)
    Optional<Trade> tradeExchangefrm(@Param("tNum") Long tNum);

    @Query(value = "SELECT t_num, t_title FROM trade WHERE t_num = :tNum", nativeQuery = true)
    Optional<Trade> tradeUpdateList(@Param("tNum") Long tNum);


    // INSERT
    @Modifying
    @Query(value = "INSERT INTO trade VALUES (NULL, NULL, :mId, DEFAULT, DEFAULT, DEFAULT, :tTitle, DEFAULT)", nativeQuery = true)
    void saveTrade(@Param("mId") String mId, @Param("tTitle") String tTitle);


    // UPDATE
    @Modifying
    @Query(value = "UPDATE trade SET t_title = :tTitle WHERE t_num = :tNum", nativeQuery = true)
    void tradeUpdate(@Param("tTitle") String tTitle, @Param("tNum") Long tNum);

    @Modifying
    @Query(value = "UPDATE trade SET t_views = t_views + 1 WHERE t_num = :tNum", nativeQuery = true)
    void viewsCount(@Param("tNum") Long tNum);

    @Modifying
    @Query(value = "UPDATE trade SET t_count = t_count + 1 WHERE t_num = :tNum", nativeQuery = true)
    void tradeRecommend(@Param("tNum") Long tNum);

    @Modifying
    @Query(value = "UPDATE trade SET visible = 2 WHERE t_num = :tNum", nativeQuery = true)
    void tradeDelete(@Param("tNum") Long tNum);


    // DELETE



}
