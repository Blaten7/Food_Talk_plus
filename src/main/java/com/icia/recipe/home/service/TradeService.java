package com.icia.recipe.home.service;

import com.icia.recipe.home.dao.TradeDao;
import com.icia.recipe.home.dto.AlertMessage;
import com.icia.recipe.home.dto.TradeDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TradeService {
    @Autowired
    TradeDao tDao;

    public List<TradeDto> tradeList() {
        List<TradeDto> tList = new ArrayList<>();
        tList = tDao.tradeList();
        return tList;
    }

    public boolean tradeSave(TradeDto tDto) {
        log.info("서비스 입장");
        return tDao.save(tDto);
    }

    public List<TradeDto> tradeDetail(Integer t_num) {
        tDao.viewsCount(t_num);
        return tDao.tradeDetail(t_num);
    }

    public boolean tradeUpdate(TradeDto tDto) {
        return tDao.tradeUpdate(tDto);
    }

    public boolean tradeDelete(Integer t_num) {
        return tDao.tradeDelete(t_num);
    }

    public List<TradeDto> tradeDateSort() {
        List<TradeDto> tDateList = new ArrayList<>();
        tDateList = tDao.tradeDateSort();
        return tDateList;
    }

    public List<TradeDto> tradeViewSort() {
        List<TradeDto> tViewList = new ArrayList<>();
        tViewList = tDao.tradeViewSort();
        return tViewList;
    }

    public boolean tradeRecommend(TradeDto tDto) {
        String selResult = tDao.selectRecommend(tDto);
        log.info(selResult);
        if (selResult == null) {
            if (tDao.insertRecommend(tDto) && tDao.tradeRecommend(tDto)) {
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
        tCountList = tDao.tradeCountSort();
        return tCountList;
    }

    public boolean tradeSaveItem(TradeDto tDto) {
        return tDao.tradeSaveItem(tDto);
    }

    public List<TradeDto> tradeUpdateList(Integer t_num) {
        return tDao.tradeUpdateList(t_num);
    }

    public List<TradeDto> tradeUpList(Integer t_num) {
        return tDao.tradeUpList(t_num);
    }

    public boolean tradeUpdateItem(TradeDto tDto) {
        return tDao.tradeUpdateItem(tDto);
    }

    public boolean tradeUpInsert(TradeDto tDto) {
        return tDao.tradeUpInsert(tDto);
    }

    public boolean tradeUpDelete(TradeDto tDto) {
        return tDao.tradeUpDelete(tDto);
    }

    public boolean tradeExcnage(TradeDto tDto) {
        return tDao.tradeExchange(tDto);
    }

    public boolean alertSave(AlertMessage alertMessage) {
        return tDao.alertSave(alertMessage);
    }

    public List<TradeDto> alertList(TradeDto tDto) {
        return tDao.alertList(tDto);
    }

    public boolean alertDelete(TradeDto tDto) {
        return tDao.alertDelete(tDto);
    }

    public boolean tradeItemDelete(TradeDto tDto) {
        return tDao.tradeItemDelete(tDto);
    }

    public String getMemberName(String mId) {
        return tDao.getMemberName(mId);
    }

    public List<TradeDto> getTradeListPaging(Integer pageNum, Integer pageSize) {
        List<TradeDto> tList = tDao.tradeList();
        int totalListCnt = tList.size();
        int fromIdx = (pageNum - 1) * pageSize;
        int toIdx = Math.min(fromIdx + pageSize, totalListCnt);
        if (fromIdx > totalListCnt) {
            return List.of();
        }
        log.info("[페이징 서비스] : {}",tList.subList(fromIdx, toIdx));
        return tList.subList(fromIdx, toIdx);
    }
}
