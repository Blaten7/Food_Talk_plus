package com.icia.recipe.service.mainService;

import com.icia.recipe.dto.mainDto.*;
import com.icia.recipe.dto.manageDto.MemberDto;
import com.icia.recipe.repository.FoodItemRepository;
import com.icia.recipe.repository.MemberRepository;
import com.icia.recipe.repository.NoticeRepository;
import com.icia.recipe.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class MemberService {
    @Autowired
    MemberRepository mr;
    @Autowired
    OrderRepository or;
    @Autowired
    FoodItemRepository fr;
    @Autowired
    NoticeRepository nr;

    @Autowired
    PasswordEncoder passwordEncoder;

    PasswordManager pm = new PasswordManager();
    private int pageCount = 0;

    public boolean join(Member member) {
        System.out.println("pwEncoder: " + passwordEncoder);
        member.setM_pw(passwordEncoder.encode(member.getM_pw()));
        if (member.getM_pw()!=null) {
            return true;
        } else {
            return false;
        }
    }

    public String searchid(MemberDto member) {
        return mr.searchid(member);
    }

    public String searchpw(MemberDto member) {
        return mr.searchpw(member);
    }

    public boolean changepw(MemberDto member) {
        member.setM_pw(passwordEncoder.encode(member.getM_pw()));
        return mr.changepw(member);
    }

    public String findId(String m_id) {
        return mr.findId(m_id);
    }

    public List<MemberDto> getSearchIdPw(String mname, String phone) {
        return mr.getSearchIdPw(mname, phone);
    }

    public String getSearchPw(String id, String name, String phone) {
        return mr.getSearchPw(id, name, phone);
    }

    public boolean updateTempPw(String id, String name, String phone, String pw) {
        if (phone.length() == 10) { // 예: 0101234567
            String phone1 = phone.substring(0, 3);
            String phone2 = phone.substring(3, 6);
            String phone3 = phone.substring(6, 10);
            phone = phone1 + '-' + phone2 + '-' + phone3;
        } else if (phone.length() == 11) { // 예: 01012345678
            String phone1 = phone.substring(0, 3);
            String phone2 = phone.substring(3, 7);
            String phone3 = phone.substring(7, 11);
            phone = phone1 + '-' + phone2 + '-' + phone3;
        } else {
            // 전화번호가 예상치 못한 길이인 경우에 대한 처리
            throw new IllegalArgumentException("잘못된 전화번호 형식: " + phone);
        }
//        pw = passwordEncoder.encode(pw);
//        log.info("[비번암호화] 성공 : {}", pw);
        boolean result = mr.updateTempPw(id, name, phone, pw);
        if (result) {
            log.info("[비번] 첫번째 이프 통과");
            String newPw = mr.tempPwConfirm(pw);
            if (newPw != null) {
                log.info("비번 업데이트 진짜 완료");
                return true;
            } else {
                return false;
            }
        } else {
            log.info("[비번] 첫번째 리설트 실패");
            return false;
        }
    }

    public boolean updateNewPw(String pw, String newPw) {
        log.info("[현재비번] pw : {}", pw);
        log.info("[바꿀비번] newPw : {}", newPw);
//        String a = pm.hashPassword(pw);
//        String b = pm.hashPassword(pw);
//        System.out.println("a: " + a);
//        System.out.println("b: " + b);
//        return false;
        newPw = passwordEncoder.encode(newPw);
        if (newPw.length() > 150) {
            newPw = newPw.substring(0, 150);
        }
//        pw = passwordEncoder.encode(pw);
//        if (pw.length() > 150) {
//            pw = newPw.substring(0, 150);
//        }
        log.info("[비번변경] newPw 암호화 : {}", newPw);
        boolean result = mr.updateNewPw(pw, newPw);
        if (result) {
            log.info("[비번] 업데이트 성공");
            return true;
        } else {
            log.info("[비번] 업데이트 실패");
            return false;
        }
    }

    public String getPaging(String id, SearchDto sDto){
        int totalNum = or.getOrderCount(id);
        log.info("totalNum : {}", totalNum);
        String listUrl = "javascript:paging(";
        Paging paging = new Paging(totalNum, sDto.getPageNum(),sDto.getListCnt(),pageCount,listUrl);
        return paging.makeHtmlPaging();
    }


    public void selectOrder(String id, Model model, SearchDto sDto) {
        pageCount = 4;
        if (sDto.getPageNum() == null)
            sDto.setPageNum(1);
        if (sDto.getListCnt() == null)
            sDto.setListCnt(pageCount);
        if (sDto.getStartIdx() == null)
            sDto.setStartIdx(0);
       sDto.setStartIdx((sDto.getPageNum()-1)*sDto.getListCnt());
        HashMap<String,String> hMap = new HashMap<>();
        hMap.put("id",id);
        sDto.setData(hMap);
        List<Object[]> list = or.selectOrder(id, sDto);
        log.info("selectOrder:{}",list);
        model.addAttribute("orderTable", makeOrder(list));
        model.addAttribute("empty", "ok");
    }

    private String makeOrder(List<Object[]> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("<section class=\"orderPayment__sect accordion itemConfirm\">")
                .append("<div class=\"orderPayment__cont\" id=\"dvCartListArea\" style=\"padding-bottom: 0;\">")
                .append("<strong id=\"normalTitle\" class=\"itemConfirm__title normal-title\">-진행중인 주문</strong>");

        list.forEach(l -> {
            int index = 0;

            // 데이터 추출
            String price = ((String) l[0]).replaceAll("\\B(?=(\\d{3})+(?!\\d))", ",");
            String delivery;

            // 배송 상태 결정
            switch ((String) l[7]) {
                case "0":
                    delivery = "배송 준비중";
                    break;
                case "1":
                    delivery = "배송 중";
                    break;
                case "2":
                    delivery = "배송 완료";
                    break;
                default:
                    delivery = "알 수 없음";
                    break;
            }

            int count = Integer.parseInt(l[1].toString()) - 1;

            // 리스트 데이터 추출
            List<FooditemDto> fList = (List<FooditemDto>) l[4];
            List<ImgDto> iList = (List<ImgDto>) l[3];

            // HTML 생성
            sb.append("<div class=\"planMeals box\" id=\"normal-item\">")
                    .append("<div class=\"box__list single\">")
                    .append("<strong class=\"title\">")
                    .append("<a href=\"javascript:void(0)\" onclick=\"modalOpen(" + l[2] + ")\">" + fList.get(index).getF_title() + "...외" + count + "개</a>")
                    .append("</strong>")
                    .append("<div class=\"boxInner\">")
                    .append("<figure class=\"boxInner__thumb\">")
                    .append("<img src=\"" + iList.get(index).getI_path() + iList.get(index).getI_sys_name() + "\">")
                    .append("</figure>")
                    .append("<div class=\"detail\">")
                    .append("<div class=\"detail__lft\">")
                    .append("<p class=\"boxInner__txt\">옵션 : " + fList.get(index).getF_title() + "</p>")
                    .append("</div>")
                    .append("<div class=\"detail__rgt price\">")
                    .append("<span class=\"price__list\">배송상태: <span class=\"num\">" + delivery + "</span></span>")
                    .append("<strong class=\"price__list\">결제금액 <span class=\"num\">" + price + "원</span></strong>")
                    .append("</div>")
                    .append("</div>")
                    .append("</div>")
                    .append("</div>");
        });

        sb.append("</div>").append("</section>");
        return sb.toString();
    }


    public String selectOrderDetail(String num) {
        List<Object[]> list = or.selectOrderDetail(num);
        log.info("selectOrderDetail:,{}", list);
        return makeOrderDetail(list);
    }

    private String makeOrderDetail(List<Object[]> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("<section class=\"orderPayment__sect accordion itemConfirm\">")
                .append("<div class=\"orderPayment__cont\" id=\"dvCartListArea\" style=\"padding-bottom: 0;\">")
                .append("<strong id=\"normalTitle\" class=\"itemConfirm__title normal-title\">- 주문 상세목록</strong>");
        list.forEach(l -> {
            int index = ((List<?>) l[4]).size();
            for (int i = 0; i < index; i++) {
                String dvPrice = ((List<FooditemDto>) l[4]).get(i).getF_price().replaceAll("\\B(?=(\\d{3})+(?!\\d))", ",");
                int price = Integer.parseInt(((List<FooditemDto>) l[4]).get(i).getF_price());
                int count = Integer.parseInt(((List<ItemListDto>) l[5]).get(i).getDvCartCount());
                String price1 = (price * count) + "";
                price1 = price1.replaceAll("\\B(?=(\\d{3})+(?!\\d))", ",");
                sb.append("<div class=\"planMeals box\" id=\"normal-item\">")
                        .append("<div class=\"box__list single\">")
                        .append("<strong class=\"title\">")
                        .append("<a href=\"/fooditem/detail?f_num=" + ((List<FooditemDto>) l[4]).get(i).getF_num() + "\">" + ((List<FooditemDto>) l[4]).get(i).getF_title() + "</a>")
                        .append("</strong>")
                        .append("<div class=\"boxInner\">")
                        .append("<figure class=\"boxInner__thumb\">")
                        .append("<img src=\"" + ((List<FooditemDto>) l[4]).get(i).getIList().get(0).getI_path() + ((List<FooditemDto>) l[4]).get(i).getIList().get(0).getI_sys_name() + "\">")
                        .append("</figure>")
                        .append("<div class=\"detail\">")
                        .append("<div class=\"detail__lft\">")
                        .append("<p class=\"boxInner__txt\">옵션 : " + ((List<FooditemDto>) l[4]).get(i).getF_title() + "</p>")
                        .append("<p class=\"boxInner__txt\">수량 : " + ((List<ItemListDto>) l[5]).get(i).getDvCartCount() + "개 </p>")
                        .append("</div>")
                        .append("<div class=\"detail__rgt price\">")
                        .append("<span class=\"price__list\" id=\"total_price_list\">상품금액 <span class=\"num\">" + dvPrice + "원</span></span>")
                        .append("<strong class=\"price__list\">결제금액 <span class=\"num\">" + price1 + "원</span></strong>")
                        .append("</div>")
                        .append("</div>")
                        .append("</div>")
                        .append("</div>");
            }
        });
        sb.append("</div>").append("</section>");
        return sb.toString();
    }


    public List<Object[]> getRanking() {
        return fr.getRanking();
    }

    public boolean insertNotice(String title, String contents, String id) {
        return nr.insertNotice(title, contents, id);
    }

    public List<NoticeDto> getNoticeList() {
        List<NoticeDto> nList = nr.getNoticeList();
        for (NoticeDto n : nList) {
            n.setM_id("관리자");
        }
        return nList;
    }

    public List<Object[]> checkId(String m_id) {
        return mr.checkId(m_id);
    }
}
