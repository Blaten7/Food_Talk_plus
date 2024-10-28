package com.icia.recipe.repository;

import com.icia.recipe.dto.mainDto.AlertMessage;
import com.icia.recipe.dto.mainDto.TradeDto;
import com.icia.recipe.entity.Alert;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {

    // SELECT
    @Query(value = "SELECT * FROM alert WHERE m_id = :#{#tDto.m_id} AND t_alertnum = 1", nativeQuery = true)
    List<TradeDto> alertList(@Param("tDto")TradeDto tDto);


    // INSERT
    @Modifying
    @Query(value = "INSERT INTO alert VALUES (:#{#alert.m_id}, :#{#alert.tradesend}, :#{#alert.t_num}, :#{#alert.t_item}, :#{#alert.t_itemcount}, :#{#alert.t_unit}, :#{#alert.t_change}, DEFAULT)",
            nativeQuery = true)
    boolean alertSave(@Param("alert")AlertMessage alert);


    // UPDATE
    @Modifying
    @Query(value = "UPDATE alert SET t_alertnum = 2 WHERE t_num = :tNum AND t_item = :tItem", nativeQuery = true)
    void tradeExchange(@Param("tNum") Long tNum, @Param("tItem") String tItem);

    @Modifying
    @Query(value = "UPDATE alert SET t_alertnum = 3 WHERE t_num = :#{#tDto.t_num} AND t_item = :#{#tDto.t_item} " +
            "AND tradesend = :#{#tDto.tradesend} AND m_id = :#{tDto.m_id}",
            nativeQuery = true)
    boolean alertDelete(@Param("tDto")TradeDto tDto);


    // DELETE

}
