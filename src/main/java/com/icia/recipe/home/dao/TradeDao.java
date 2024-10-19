package com.icia.recipe.home.dao;

import com.icia.recipe.home.dto.AlertMessage;
import com.icia.recipe.home.dto.TradeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TradeDao {

    List<TradeDto> tradeList();

    boolean save(TradeDto tDto);

    List<TradeDto> tradeDetail(Integer t_num);

    boolean tradeUpdate(TradeDto tDto);

    boolean tradeDelete(Integer t_num);

    List<TradeDto> tradeDateSort();

    void viewsCount(Integer tNum);

    List<TradeDto> tradeViewSort();

    boolean insertRecommend(TradeDto tDto);

    boolean tradeRecommend(TradeDto tDto);

    String selectRecommend(TradeDto tDto);

    List<TradeDto> tradeCountSort();


    boolean tradeSaveItem(TradeDto tDto);

    List<TradeDto> tradeUpdateList(Integer tNum);

    List<TradeDto> tradeUpList(Integer tNum);

    boolean tradeUpdateItem(TradeDto tDto);

    boolean tradeUpInsert(TradeDto tDto);

    boolean tradeUpDelete(TradeDto tDto);

    boolean tradeExchange(TradeDto tDto);

    boolean alertSave(AlertMessage alertMessage);

    List<TradeDto> alertList(TradeDto tDto);

    boolean alertDelete(TradeDto tDto);

    boolean tradeItemDelete(TradeDto tDto);

    String getMemberName(String mId);
}
