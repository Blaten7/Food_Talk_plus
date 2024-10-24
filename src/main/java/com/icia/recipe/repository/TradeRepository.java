package com.icia.recipe.repository;

import com.icia.recipe.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {

    void executeSave();

    @Modifying
    void saveInsertrecommend();

    void executeTradesaveitem();

    @Modifying
    void saveTradeupinsert();

    void executeAlertsave();

    @Modifying
    void updateTradeupdate();

    void executeViewscount();

    void executeTraderecommend();

    @Modifying
    void deleteTradedelete();

    @Modifying
    void updateTradeupdateitem();

    void executeTradeexchange();

    @Modifying
    void deleteAlertdelete();

    @Modifying
    void deleteTradeitemdelete();

    @Modifying
    void deleteTradeupdelete();

    void executeTradedetail();

    void executeTradelist();

    void executeTradedatesort();

    void executeTradeviewsort();

    List<Trade> findBySelectrecommend();

    void executeTradecountsort();

    void executeTradeexchangefrm();

    @Modifying
    void updateTradeupdatelist();

    void executeTradeuplist();

    void executeAlertlist();

    void executeGetmembername();
}
