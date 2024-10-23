package com.icia.recipe.home.controller;

import com.icia.recipe.home.dto.TradeDto;
import com.icia.recipe.home.service.TradeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/trade")
public class TradeController {

    @Autowired
    TradeService tSer;

    @GetMapping("/main")
    public String tradeMain(Model model) {
        List<TradeDto> tList = tSer.tradeList();
        model.addAttribute("tList", tList);
        log.info(">>>>>tList:{}", tList);
        return "main/trade/tradeMain";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/detail")
    public String tradeDetail(Model model, @RequestParam("t_num") Integer t_num) {
        log.info("...." + t_num);
        List<TradeDto> tDList = tSer.tradeDetail(t_num);
        log.info(">>>>>tDList:{}", tDList);
        int tNum = tDList.get(0).getT_num();
        String t_title = tDList.get(0).getT_title();
        String m_id = tDList.get(0).getM_id();
        String t_item=tDList.get(0).getT_item();
        int t_itemcount=tDList.get(0).getT_itemcount();
        String t_unit=tDList.get(0).getT_unit();
        String t_change=tDList.get(0).getT_change();
        String m_name = tSer.getMemberName(m_id);

        log.info(">>>>>m_id:{}", m_id);
        model.addAttribute("m_id", m_id);
        model.addAttribute("m_name", m_name);
        model.addAttribute("t_num", tNum);
        model.addAttribute("tDList", tDList);
        model.addAttribute("t_title", t_title);
        model.addAttribute("t_item", t_item);
        model.addAttribute("t_itemcount", t_itemcount);
        model.addAttribute("t_unit", t_unit);
        model.addAttribute("t_change", t_change);
        return "/main/trade/tradeDetail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write")
    public String tradeWrite() {
        return "main/trade/tradeWrite";
    }

    @PostMapping("/save")
    public String tradesave(TradeDto tDto, @AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {
        String m_id = userDetails.getUsername();
        log.info("컨트롤러 입장");
        String t_title = request.getParameter("t_title");
        String[] t_item = request.getParameterValues("t_item");
        String[] t_itemcount = request.getParameterValues("t_itemcount");
        String[] t_unit = request.getParameterValues("t_unit");
        String[] t_change = request.getParameterValues("t_change");
        String[] t_order = request.getParameterValues("t_order");
        log.info(t_title);
        log.info(Arrays.toString(t_item));
        log.info(Arrays.toString(t_itemcount));
        log.info(Arrays.toString(t_unit));
        log.info(Arrays.toString(t_change));
        log.info(Arrays.toString(t_order));
        if (t_item != null) {
            tDto.setM_id(m_id);
            tDto.setT_title(t_title);
            boolean result = tSer.tradeSave(tDto);
            if (result) {
                int size = t_item.length;
                for (int i = 0; i < size; i++) {
                    tDto.setT_item(t_item[i]);
                    tDto.setT_itemcount(Integer.parseInt(t_itemcount[i]));
                    tDto.setT_unit(t_unit[i]);
                    tDto.setT_change(t_change[i]);
                    tDto.setT_order(Integer.parseInt(t_order[i]));
                    boolean result2 = tSer.tradeSaveItem(tDto);
                    if (result2) {
                        log.info(">>>>>>>>>>saveItem 성공");
                    } else {
                        log.info(">>>>>>>>>>saveItem 실패");
                    }
                }
            } else {
                log.info(">>>>>>>>>save실패");
            }
        }
        return "redirect:main";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/update")
    public String tradeUpdate(TradeDto tDto, HttpServletRequest request) {
        log.info("글 수정 Controller");
        String[] t_item = request.getParameterValues("t_item");
        String[] t_itemcount = request.getParameterValues("t_itemcount");
        String[] t_unit = request.getParameterValues("t_unit");
        String[] t_change = request.getParameterValues("t_change");
        String[] t_order = request.getParameterValues("t_order");
        String[] order = request.getParameterValues("order");
        log.info(Arrays.toString(t_item));
        log.info(Arrays.toString(t_itemcount));
        log.info(Arrays.toString(t_unit));
        log.info(Arrays.toString(t_change));
        log.info(Arrays.toString(t_order));
        log.info(Arrays.toString((order)));
        boolean result = tSer.tradeUpdate(tDto);
        if (result) {
            int size = t_item.length;
            for (int i = 0; i < size; i++) {
                tDto.setT_item(t_item[i]);
                tDto.setT_itemcount(Integer.parseInt(t_itemcount[i]));
                tDto.setT_unit(t_unit[i]);
                tDto.setT_change(t_change[i]);
                tDto.setT_order(Integer.parseInt(t_order[i]));
                boolean result2 = tSer.tradeUpdateItem(tDto);
                if (result2) {
                    log.info("tradeUpdate 성공 ^^");
                } else {
                    log.info("tradeUpdate 실패 ㅠㅠ");
                    boolean result3 = tSer.tradeUpInsert(tDto);
                    if (result3) {
                        log.info("tradeUpInsert 성공 ^^");
                    } else {
                        log.info("tradeUpInsert 실패 ㅠㅠ");
                    }
                }
            }
            if (order != null) {
                int oSize = order.length;
                for (int i = 0; i < oSize; i++) {
                    tDto.setT_order(Integer.parseInt(order[i]));
                    boolean result4 = tSer.tradeUpDelete(tDto);
                    if (result4) {
                        log.info("tradeUpDelete 성공 ^^");
                    } else {
                        log.info("tradeUpDelete 실패 ㅠㅠ");
                    }
                }
            }
        } else {
            log.info("tradeUpdate 실패 ㅠㅠ");
        }
        return "redirect:/detail?t_num=" + tDto.getT_num();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/updatefrm")
    public String tradeUpdatefrm(@RequestParam("t_num") Integer t_num, Model model) {
        log.info("글 수정창 열기");
        List<TradeDto> tList = tSer.tradeUpdateList(t_num);
        List<TradeDto> tUList = tSer.tradeUpList(t_num);
        log.info("" + tList);
        log.info("" + tUList);
        String t_title = tList.get(0).getT_title();
        int tNum = tList.get(0).getT_num();
        model.addAttribute("t_num", tNum);
        model.addAttribute("t_title", t_title);
        model.addAttribute("tUList", tUList);

        // 모델에 배열을 저장
        return "main/trade/tradeUpdate";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete")
    public String tradeDelete(@RequestParam("t_num") Integer t_num) {
        log.info(">>>>>>>>글 delete controller");
        boolean result = tSer.tradeDelete(t_num);
        if (result) {
            log.info(">>>>>>>>>>글삭제 성공");
            return "redirect:main";
        } else {
            log.info(">>>>>>>>>>글삭제 성공");
            return "redirect:/trade/update?t_num=" + t_num;
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/login")
    public String tradeRecommend(TradeDto tDto) {
        return "redirect:/trade/detail?t_num=" + tDto.getT_num();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/exchangefrm")
    public String tradeExcnahgeFrm(Model model, @RequestParam("t_num") Integer t_num){
        List<TradeDto> tDList = tSer.tradeDetail(t_num);
        log.info("tDList:{} ",tDList);
        int tNum = tDList.get(0).getT_num();
        String t_title = tDList.get(0).getT_title();
        String m_id = tDList.get(0).getM_id();
        String t_item=tDList.get(0).getT_item();
        int t_itemcount=tDList.get(0).getT_itemcount();
        String t_unit=tDList.get(0).getT_unit();
        String t_change=tDList.get(0).getT_change();

        // 모델에 배열을 저장
        log.info(">>>>>m_id:{}", m_id);
        model.addAttribute("m_id", m_id);
        model.addAttribute("t_num", tNum);
        model.addAttribute("tDList", tDList);
        model.addAttribute("t_title", t_title);
        model.addAttribute("t_item", t_item);
        model.addAttribute("t_itemcount", t_itemcount);
        model.addAttribute("t_unit", t_unit);
        model.addAttribute("t_change", t_change);
        return "/main/trade/exchangefrm";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/refrigerator")
    public String NaengJangGo() {
        return "main/trade/refrigerator";
    }

}