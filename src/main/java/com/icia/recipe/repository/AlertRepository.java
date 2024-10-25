package com.icia.recipe.repository;

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
    @Query(value = "SELECT * FROM alert WHERE m_id = :mId AND t_alertnum = 1", nativeQuery = true)
    List<Alert> alertList(@Param("mId") String mId);


    // INSERT
    @Modifying
    @Query(value = "INSERT INTO alert VALUES (:mId, :tradeSend, :tNum, :tItem, :tItemCount, :tUnit, :tChange, DEFAULT)",
            nativeQuery = true)
    void alertSave(@Param("mId") String mId,
                   @Param("tradeSend") String tradeSend,
                   @Param("tNum") Long tNum,
                   @Param("tItem") String tItem,
                   @Param("tItemCount") int tItemCount,
                   @Param("tUnit") String tUnit,
                   @Param("tChange") String tChange);


    // UPDATE
    @Modifying
    @Query(value = "UPDATE alert SET t_alertnum = 2 WHERE t_num = :tNum AND t_item = :tItem", nativeQuery = true)
    void tradeExchange(@Param("tNum") Long tNum, @Param("tItem") String tItem);

    @Modifying
    @Query(value = "UPDATE alert SET t_alertnum = 3 WHERE t_num = :tNum AND t_item = :tItem " +
            "AND tradesend = :tradeSend AND m_id = :mId",
            nativeQuery = true)
    void alertDelete(@Param("tNum") Long tNum,
                     @Param("tItem") String tItem,
                     @Param("tradeSend") String tradeSend,
                     @Param("mId") String mId);


    // DELETE

}
