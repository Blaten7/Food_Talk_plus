package com.icia.recipe.controller.mainRestController;

import com.icia.recipe.dto.mainDto.TradeDto;
import com.icia.recipe.service.mainService.TradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@Slf4j
@EnableMethodSecurity(securedEnabled = true)
public class RestTradeController {
    @Autowired
    TradeService tSer;

    @GetMapping("/trade/dateSort")
    public List<TradeDto> tradeDateSort(){
        List<TradeDto> tDateList=tSer.tradeDateSort();
        log.info(""+tDateList);
        return tDateList;
    }

    @GetMapping("/trade/viewSort")
    public List<TradeDto> tradeViewSort(){
        List<TradeDto> tViewList=tSer.tradeViewSort();
        log.info(""+tViewList);
        return tViewList;
    }

    @GetMapping("/trade/countSort")
    public List<TradeDto> tradeCountSort(){
        List<TradeDto> tCountList=tSer.tradeCountSort();
        log.info(""+tCountList);
        return tCountList;
    }

    @PostMapping("/trade/recommend")
    public boolean tradeRecommend(TradeDto tDto, @RequestParam("t_num") Integer t_num, @AuthenticationPrincipal UserDetails userDetails){
        String m_id=userDetails.getUsername();
        tDto.setM_id(m_id);
        tDto.setT_num(t_num);
        boolean result=tSer.tradeRecommend(tDto);
        log.info("result: {}",result);
        if(result){
            log.info("성공햇슈ㅋㅋ");
            return true;
        }else{
            log.info("실패햇슈ㅠㅠ");
            return false;
        }
    }

    @PostMapping("/trade/messageSend")
    public boolean messageSend(){
        return true;
    }

    @PostMapping("/trade/accept")
    public String tradeExchange(@RequestParam("t_num") Integer t_num,
                                 @RequestParam("t_item") String t_item,
                                 @RequestParam("t_itemcount") Integer t_itemcount,
                                 TradeDto tDto){
        log.info("등장");
        tDto.setT_num(t_num);
        tDto.setT_item(t_item);
        tDto.setT_itemcount(t_itemcount);
        boolean acceptResult=tSer.tradeExcnage(tDto);
        StringBuilder sb=new StringBuilder();
        LocalDateTime now = LocalDateTime.now();
        // 초를 2자리로 포맷하기 위한 DateTimeFormatter 생성
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 포맷팅된 문자열 생성
        String formattedDate = now.format(formatter);
        // 초를 2자리로 잘라내기 (소수점 이하를 제외)
        String result = formattedDate.substring(0, 19); // "yyyy-MM-dd HH:mm:ss" 형식의 첫 19글자까지 추출
        if(acceptResult){
            boolean delResult=tSer.tradeItemDelete(tDto);
            if(delResult) {
//                List<TradeDto> alertList = tSer.alertList(tDto);
//                for (TradeDto alert : alertList) {
//                    String alertMessage="<div class='me-3' id='notification_"+alert.getT_num()+"_"+alert.getT_item()+"_"+alert.getT_itemcount()+"'>" +
//                            "<i class='fas fa-file-alt text-white'></i>" +
//                            "<div id='socketAlertDiv'>" +
//                            "<span id='current-time' class='small text-gray-500'>"
//                            + result +
//                            "</span>"+" " +
//                            "<button id='accept' onclick='accept("+alert.getT_num()+",\""+alert.getT_item()+"\","+alert.getT_itemcount()+
//                            ", \"" + alert.getTradesend() + "\", \""+alert.getM_id()+"\")'>수락</button>"+" " +
//                            "<button id='refuse' onclick='refuse(" + alert.getT_num()+",\""+alert.getT_item()+"\","+alert.getT_itemcount() +", \"" + alert.getTradesend() + "\", \""+alert.getM_id()+"\")'>거절</button>" +
//                            "<a class='dropdown-item d-flex align-items-center' href='#'>" +
//                            "<div id='socketAlert' class='alert alert-warning' role='alert'>" +
//                            alert.getTradesend() + "님이 교환신청 하였습니다." +
//                            "</div>" +
//                            "</a>" +
//                            "</div>" +
//                            "</div>";
//                    sb.append(alertMessage);
//                }
                return "true";
            }
        }else{
            return null;
        }
        return null;
    }
    @PostMapping("/trade/refuse")
    public String refuse(@RequestParam("t_num") Integer t_num,@RequestParam("t_item") String t_item, @RequestParam("tradesend") String tradesend,
                          @RequestParam("m_id") String m_id, TradeDto tDto){
        tDto.setT_num(t_num);
        tDto.setT_item(t_item);
        tDto.setTradesend(tradesend);
        tDto.setM_id(m_id);
        log.info(m_id);
        StringBuilder sb=new StringBuilder();
        LocalDateTime now = LocalDateTime.now();
        // 초를 2자리로 포맷하기 위한 DateTimeFormatter 생성
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 포맷팅된 문자열 생성
        String formattedDate = now.format(formatter);
        // 초를 2자리로 잘라내기 (소수점 이하를 제외)
        String result = formattedDate.substring(0, 19); // "yyyy-MM-dd HH:mm:ss" 형식의 첫 19글자까지 추출
        boolean alertResult = tSer.alertDelete(tDto);
        if(alertResult){
//            List<TradeDto> alertList = tSer.alertList(tDto);
//            for(TradeDto alert:alertList){
//                String alertMessage="<div class='me-3' id='notification_"+alert.getT_num()+"_"+alert.getT_item()+"_"+alert.getT_itemcount()+"'>" +
//                        "<i class='fas fa-file-alt text-white'></i>" +
//                        "<div id='socketAlertDiv'>" +
//                        "<span id='current-time' class='small text-gray-500'>"
//                        + result +
//                        "</span>"+" " +
//                        "<button id='accept' onclick='accept("+alert.getT_num()+",\""+alert.getT_item()+"\","+alert.getT_itemcount()+
//                        ", \"" + alert.getTradesend() + "\", \""+alert.getM_id()+"\")'>수락</button>"+" " +
//                        "<button id='refuse' onclick='refuse(" + alert.getT_num()+",\""+alert.getT_item()+"\","+alert.getT_itemcount() +", \"" + alert.getTradesend() + "\", \""+alert.getM_id()+"\")'>거절</button>" +
//                        "<a class='dropdown-item d-flex align-items-center' href='#'>" +
//                        "<div id='socketAlert' class='alert alert-warning' role='alert'>" +
//                        alert.getTradesend() + "님이 교환신청 하였습니다." +
//                        "</div>" +
//                        "</a>" +
//                        "</div>" +
//                        "</div>";
//                sb.append(alertMessage);
//            }
            return "true";
        }else{
            return null;
        }
    }
    @PostMapping("/alert/List")
    public String alertList(@AuthenticationPrincipal UserDetails userDetails,TradeDto tDto){
        String m_id=userDetails.getUsername();
        tDto.setM_id(m_id);
        List<TradeDto> alertList=tSer.alertList(tDto);
        StringBuilder sb=new StringBuilder();
        LocalDateTime now = LocalDateTime.now();
        // 초를 2자리로 포맷하기 위한 DateTimeFormatter 생성
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 포맷팅된 문자열 생성
        String formattedDate = now.format(formatter);
        // 초를 2자리로 잘라내기 (소수점 이하를 제외)
        String result = formattedDate.substring(0, 19); // "yyyy-MM-dd HH:mm:ss" 형식의 첫 19글자까지 추출
        for(TradeDto alert:alertList){
            String alertMessage="<div class='me-3' id='notification_"+alert.getT_num()+"_"+alert.getT_item()+"_"+alert.getT_itemcount()+"'>" +
                    "<i class='fas fa-file-alt text-white'></i>" +
                    "<div id='socketAlertDiv'>" +
                    "<span id='current-time' class='small text-gray-500'>"
                    + result +
                    "</span>"+" " +
                    "<button id='accept' onclick='accept("+alert.getT_num()+",\""+alert.getT_item()+"\","+alert.getT_itemcount()+
                    ", \"" + alert.getTradesend() + "\", \""+alert.getM_id()+"\")'>수락</button>"+" " +
                    "<button id='refuse' onclick='refuse(" + alert.getT_num()+",\""+alert.getT_item()+"\","+alert.getT_itemcount() +", \"" + alert.getTradesend() + "\", \""+alert.getM_id()+"\")'>거절</button>" +
                    "<a class='dropdown-item d-flex align-items-center' href='#'>" +
                    "<div id='socketAlert' class='alert alert-warning' role='alert'>" +
                    alert.getTradesend() + "님이 "+alert.getT_item()+" "+alert.getT_itemcount()+alert.getT_unit()+" 과 "+alert.getT_change()+"를 교환신청 하였습니다." +
                    "</div>" +
                    "</a>" +
                    "</div>" +
                    "</div>";
            sb.append(alertMessage);
        }
        return sb.toString();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/trade/list/paging")
    public List<TradeDto> tradeListPaging(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        List<TradeDto> t= tSer.getTradeListPaging(pageNum, pageSize);
        log.info("[물물교환 페이징] : {}",t);
        return t;
    }


}
