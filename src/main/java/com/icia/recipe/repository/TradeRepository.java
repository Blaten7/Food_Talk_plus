package com.icia.recipe.repository;

import com.icia.recipe.dto.mainDto.TradeDto;
import com.icia.recipe.dto.mainDto.TradeItemDto;
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
    List<TradeDto> tradeList();

    @Query(value = "SELECT t_num, t_title, m_id, t_date, t_views, t_count " +
            "FROM trade WHERE visible = 1 ORDER BY t_date",
            nativeQuery = true)
    List<TradeDto> tradeDateSort();

    @Query(value = "SELECT t_num, t_title, m_id, t_date, t_views, t_count " +
            "FROM trade WHERE visible = 1 ORDER BY t_views DESC",
            nativeQuery = true)
    List<TradeDto> tradeViewSort();

    @Query(value = "SELECT t_num, t_title, m_id, t_date, t_views, t_count " +
            "FROM trade WHERE visible = 1 ORDER BY t_count DESC",
            nativeQuery = true)
    List<TradeDto> tradeCountSort();

    @Query(value = "SELECT * FROM trade WHERE t_num = :#{#tDto.t_num}", nativeQuery = true)
    boolean tradeExchange(@Param("tDto")TradeDto tDto);

    @Query(value = "SELECT t_num, t_title FROM trade WHERE t_num = :tNum", nativeQuery = true)
    List<TradeDto> tradeUpdateList(@Param("tNum")Integer tNum);

    @Query(value = "SELECT m_id FROM traderecommend WHERE m_id = :#{#tDto.m_id} AND t_num = :#{#tDto.t_num}", nativeQuery = true)
    List<TradeDto> selectRecommend(@Param("tDto")TradeDto tDto);

    @Query(value = "SELECT ti.t_num, t.m_id, t.t_title, ti.t_item, ti.t_itemcount, ti.t_unit, ti.t_change, t.visible " +
            "FROM trade t JOIN tradeitem ti ON t.t_num = ti.t_num WHERE ti.t_num = :#{#tDto.t_num}",
            nativeQuery = true)
    List<TradeDto> tradeDetail(@Param("tDto")Integer tDto);

    @Query(value = "SELECT * FROM tradeitem WHERE t_num = :tNum", nativeQuery = true)
    List<TradeDto> tradeUpList(@Param("tNum")Integer tNum);

    @Query(value = "select * from trade t join member m on t.m_id = m.m_id", nativeQuery = true)
    List<TradeDto> getTradeList();

    @Query(value = "select * from tradeitem", nativeQuery = true)
    List<TradeItemDto> getTradeItemList();


    // INSERT
    @Modifying
    @Query(value = "INSERT INTO trade VALUES (NULL, NULL, :#{#tDto.m_id}, DEFAULT, DEFAULT, DEFAULT, :#{tDto.t_title}, DEFAULT)", nativeQuery = true)
    boolean saveTrade(@Param("tDto")TradeDto tDto);

    @Modifying
    @Query(value = "INSERT INTO traderecommend VALUES (:#{#tDto.m_id}, :#{#tDto.t_num})", nativeQuery = true)
    boolean insertRecommend(@Param("tDto")TradeDto tDto);

    // UPDATE
    @Modifying
    @Query(value = "UPDATE trade SET t_title = :#{tDto.t_title} WHERE t_num = :#{#tDto.t_num}", nativeQuery = true)
    boolean tradeUpdate(@Param("tDto")TradeDto tDto);

    @Modifying
    @Query(value = "UPDATE trade SET t_views = t_views + 1 WHERE t_num = :tNum", nativeQuery = true)
    void viewsCount(@Param("tNum") Integer tNum);

    @Modifying
    @Query(value = "UPDATE trade SET t_count = t_count + 1 WHERE t_num = :#{#tDto.t_num}", nativeQuery = true)
    boolean tradeRecommend(@Param("tDto")TradeDto tDto);

    @Modifying
    @Query(value = "UPDATE trade SET visible = 2 WHERE t_num = :tNum", nativeQuery = true)
    boolean tradeDelete(@Param("tNum") Integer tNum);

    @Modifying
    @Query(value = "INSERT INTO tradeitem (t_num, t_item, t_itemcount, t_unit, t_change, t_order) " +
            "SELECT MAX(t_num), :#{#tDto.t_item}, :#{#tDto.t_itemcount}, :#{#tDto.t_unit}, :#{#tDto.t_change}, :#{#tDto.t_order} FROM trade",
            nativeQuery = true)
    boolean tradeSaveItem(@Param("tDto")TradeDto tDto);

    @Modifying
    @Query(value = "INSERT INTO tradeitem (t_num, t_item, t_itemcount, t_unit, t_change, t_order) " +
            "SELECT :#{#tDto.t_num}, :#{#tDto.t_item}, :#{#tDto.t_itemcount}, :#{#tDto.t_unit}, :#{#tDto.t_change}, " +
            "MAX(t_order) + 1 FROM tradeitem WHERE t_num = :#{#tDto.t_num}",
            nativeQuery = true)
    boolean tradeUpInsert(@Param("tDto") TradeDto tDto);


    @Modifying
    @Query(value = "UPDATE tradeitem SET t_item = :#{#tDto.t_item}, t_itemcount = :#{#tDto.t_itemcount}, t_unit = :#{#tDto.t_unit}, " +
            "t_change = :#{tDto.t_change} WHERE t_num = :#{#tDto.t_num} AND t_order = :#{tDto.t_order}",
            nativeQuery = true)
    boolean tradeUpdateItem(@Param("tDto")TradeDto tDto);

    @Modifying
    @Query(value = "UPDATE tradeitem SET t_itemcount = t_itemcount - :#{#tDto.t_itemcount} " +
            "WHERE t_num = :#{#tDto.t_num} AND t_item = :#{#tDto.t_item}",
            nativeQuery = true)
    boolean tradeItemDelete(@Param("tDto")TradeDto tDto);


    // DELETE
    @Modifying
    @Query(value = "DELETE FROM tradeitem WHERE t_num = :#{#tDto.t_num} AND t_order = :#{tDto.t_order}", nativeQuery = true)
    boolean tradeUpDelete(@Param("tDto")TradeDto tDto);


}
