package com.icia.recipe.repository;

import com.icia.recipe.entity.Trade;
import com.icia.recipe.entity.Trade_Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeItemRepository extends JpaRepository<Trade_Item, Long> {

    // SELECT
    @Query(value = "SELECT ti.t_num, t.m_id, t.t_title, ti.t_item, ti.t_itemcount, ti.t_unit, ti.t_change, t.visible " +
            "FROM trade t JOIN tradeitem ti ON t.t_num = ti.t_num WHERE ti.t_num = :tNum",
            nativeQuery = true)
    List<Trade> tradeDetail(@Param("tNum") Long tNum);

    @Query(value = "SELECT * FROM tradeitem WHERE t_num = :tNum", nativeQuery = true)
    List<Trade> tradeUpList(@Param("tNum") Long tNum);


    // INSERT
    @Modifying
    @Query(value = "INSERT INTO tradeitem (t_num, t_item, t_itemcount, t_unit, t_change, t_order) " +
            "SELECT MAX(t_num), :tItem, :tItemCount, :tUnit, :tChange, :tOrder FROM trade",
            nativeQuery = true)
    void tradeSaveItem(@Param("tItem") String tItem,
                       @Param("tItemCount") int tItemCount,
                       @Param("tUnit") String tUnit,
                       @Param("tChange") String tChange,
                       @Param("tOrder") int tOrder);

    @Modifying
    @Query(value = "INSERT INTO tradeitem (t_num, t_item, t_itemcount, t_unit, t_change, t_order) " +
            "SELECT :tNum, :tItem, :tItemCount, :tUnit, :tChange, MAX(t_order) + 1 " +
            "FROM tradeitem WHERE t_num = :tNum",
            nativeQuery = true)
    void tradeUpInsert(@Param("tNum") Long tNum,
                       @Param("tItem") String tItem,
                       @Param("tItemCount") int tItemCount,
                       @Param("tUnit") String tUnit,
                       @Param("tChange") String tChange);



    // UPDATE
    @Modifying
    @Query(value = "UPDATE tradeitem SET t_item = :tItem, t_itemcount = :tItemCount, t_unit = :tUnit, " +
            "t_change = :tChange WHERE t_num = :tNum AND t_order = :tOrder",
            nativeQuery = true)
    void tradeUpdateItem(@Param("tItem") String tItem,
                         @Param("tItemCount") int tItemCount,
                         @Param("tUnit") String tUnit,
                         @Param("tChange") String tChange,
                         @Param("tNum") Long tNum,
                         @Param("tOrder") int tOrder);

    @Modifying
    @Query(value = "UPDATE tradeitem SET t_itemcount = t_itemcount - :tItemCount " +
            "WHERE t_num = :tNum AND t_item = :tItem",
            nativeQuery = true)
    void tradeItemDelete(@Param("tNum") Long tNum,
                         @Param("tItem") String tItem,
                         @Param("tItemCount") int tItemCount);




    // DELETE
    @Modifying
    @Query(value = "DELETE FROM tradeitem WHERE t_num = :tNum AND t_order = :tOrder", nativeQuery = true)
    void tradeUpDelete(@Param("tNum") Long tNum, @Param("tOrder") int tOrder);
}
