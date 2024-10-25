package com.icia.recipe.repository;

import com.icia.recipe.entity.Trade_Recommend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TradeRecommendRepository extends JpaRepository<Trade_Recommend, Long> {

    // SELECT
    @Query(value = "SELECT m_id FROM traderecommend WHERE m_id = :mId AND t_num = :tNum", nativeQuery = true)
    Optional<String> selectRecommend(@Param("mId") String mId, @Param("tNum") Long tNum);


    // INSERT
    @Modifying
    @Query(value = "INSERT INTO traderecommend VALUES (:mId, :tNum)", nativeQuery = true)
    void insertRecommend(@Param("mId") String mId, @Param("tNum") Long tNum);


    // UPDATE


    // DELETE
}
