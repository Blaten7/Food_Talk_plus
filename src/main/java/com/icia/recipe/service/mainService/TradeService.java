package com.icia.recipe.service.mainService;

import com.icia.recipe.dto.mainDto.AlertMessage;
import com.icia.recipe.dto.mainDto.TradeDto;
import com.icia.recipe.entity.Trade;
import com.icia.recipe.repository.TradeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TradeService {
    @Autowired
    TradeRepository tr;

    public List<Trade> tradeList() {
        List<Trade> tList = new ArrayList<>();
        tList = tr.tradeList();
        return tList;
    }

    public boolean tradeSave(TradeDto tDto) {
        log.info("서비스 입장");
        return tr.saveTrade(tDto);
    }

    public List<TradeDto> tradeDetail(Integer t_num) {
        tr.viewsCount(t_num);
        return tr.tradeDetail(t_num);
    }

    public boolean tradeUpdate(TradeDto tDto) {
        return tr.tradeUpdate(tDto);
    }

    public boolean tradeDelete(Integer t_num) {
        return tr.tradeDelete(t_num);
    }

    public List<TradeDto> tradeDateSort() {
        List<TradeDto> tDateList = new ArrayList<>();
        tDateList = tr.tradeDateSort();
        return tDateList;
    }

    public List<Trade> tradeViewSort() {
        List<Trade> tViewList = new ArrayList<>();
        tViewList = tr.tradeViewSort();
        return tViewList;
    }

    public boolean tradeRecommend(TradeDto tDto) {
        List<TradeDto> selResult = tr.selectRecommend(tDto);
        if (selResult == null) {
            if (tr.insertRecommend(tDto) && tr.tradeRecommend(tDto)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public List<TradeDto> tradeCountSort() {
        List<TradeDto> tCountList = new ArrayList<>();
        tCountList = tr.tradeCountSort();
        return tCountList;
    }

    public boolean tradeSaveItem(TradeDto tDto) {
        return tr.tradeSaveItem(tDto);
    }

    public List<TradeDto> tradeUpdateList(Integer tNum) {
        return tr.tradeUpdateList(tNum);
    }

    public List<TradeDto> tradeUpList(Integer t_num) {
        return tr.tradeUpList(t_num);
    }

    public boolean tradeUpdateItem(TradeDto tDto) {
        return tr.tradeUpdateItem(tDto);
    }

    public boolean tradeUpInsert(TradeDto tDto) {
        return tr.tradeUpInsert(tDto);
    }

    public boolean tradeUpDelete(TradeDto tDto) {
        return tr.tradeUpDelete(tDto);
    }

    public boolean tradeExcnage(TradeDto tDto) {
        return tr.tradeExchange(tDto);
    }

    public boolean alertSave(AlertMessage alertMessage) {
        return tr.alertSave(alertMessage);
    }

    public List<TradeDto> alertList(TradeDto tDto) {
        return tr.alertList(tDto);
    }

    public boolean alertDelete(TradeDto tDto) {
        return tr.alertDelete(tDto);
    }

    public boolean tradeItemDelete(TradeDto tDto) {
        return tr.tradeItemDelete(tDto);
    }

    public String getMemberName(String mId) {
        return tr.getMemberName(mId);
    }

    public List<TradeDto> getTradeListPaging(Integer pageNum, Integer pageSize) {
        List<Trade> tList = tr.tradeList();
        int totalListCnt = tList.size();
        int fromIdx = (pageNum - 1) * pageSize;
        int toIdx = Math.min(fromIdx + pageSize, totalListCnt);
        if (fromIdx > totalListCnt) {
            return List.of();
        }
        log.info("[페이징 서비스] : {}", tList.subList(fromIdx, toIdx));
        return tList.subList(fromIdx, toIdx);
    }
}
