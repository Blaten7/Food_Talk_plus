package com.icia.recipe.service.mainService;

import com.icia.recipe.entity.FoodItem;
import com.icia.recipe.entity.Member;
import com.icia.recipe.entity.Notice;
import com.icia.recipe.entity.Order;
import com.icia.recipe.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MemberService {
    @Autowired
    MemberRepository mr;

    @Autowired
    PasswordEncoder passwordEncoder;

    PasswordManager pm = new PasswordManager();
    private int pageCount = 0;

    public String join(Member member) {
        System.out.println("pwEncoder: " + passwordEncoder);
        member.setMember_pw(passwordEncoder.encode(member.getMember_pw()));
        return member.getMember_pw();
    }

    public String searchid(Member member) {
        return mr.searchid(member);
    }

    public String searchpw(Member member) {
        return mr.searchpw(member);
    }

    public boolean changepw(Member member) {
        member.setMember_pw(passwordEncoder.encode(member.getMember_pw()));
        return mr.changepw(member);
    }

    public String findId(String m_id) {
        return mr.findId(m_id);
    }

    public List<Member> getSearchIdPw(String mname, String phone) {
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

//    public String getPaging(String id, SearchDto sDto){
//        int totalNum = mr.getorderCount(id);
//        log.info("totalNum : {}", totalNum);
//        String listUrl = "javascript:paging(";
//        Paging paging = new Paging(totalNum, sDto.getPageNum(),sDto.getListCnt(),pageCount,listUrl);
//        return paging.makeHtmlPaging();
//    }


//    public void selectOrder(String id, Model model, SearchDto sDto) {
//        pageCount = 4;
//        if (sDto.getPageNum() == null)
//            sDto.setPageNum(1);
//        if (sDto.getListCnt() == null)
//            sDto.setListCnt(pageCount);
//        if (sDto.getStartIdx() == null)
//            sDto.setStartIdx(0);
//       sDto.setStartIdx((sDto.getPageNum()-1)*sDto.getListCnt());
//        HashMap<String,String> hMap = new HashMap<>();
//        hMap.put("id",id);
//        sDto.setData(hMap);
//        List<OrderDto> list = mr.selectOrder(sDto);
//        log.info("selectOrder:{}",list);
//        model.addAttribute("orderTable", makeOrder(list));
//        model.addAttribute("empty", "ok");
//    }

    private String makeOrder(List<Order> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("<section class=\"orderPayment__sect accordion itemConfirm\">")
                .append("<div class=\"orderPayment__cont\" id=\"dvCartListArea\" style=\"padding-bottom: 0;\">")
                .append("<strong id=\"normalTitle\" class=\"itemConfirm__title normal-title\">-진행중인 주문</strong>");
        list.forEach(l -> {
            int index = 0;
            int price = l.getOrder_total();
//            price = price.replaceAll("\\B(?=(\\d{3})+(?!\\d))", ",");
            String delivery = null;
            switch (l.getOrder_delivery()) {
                case "0":
                    delivery = "배송 준비중";
                    break;
                case "1":
                    delivery = "배송 중";
                    break;
                case "2":
                    delivery = "배송 완료";
                    break;
            }
            int count = l.getOrder_count() - 1;
            sb.append("<div class=\"planMeals box\" id=\"normal-item\">")
                    .append("<div class=\"box__list single\">")
                    .append("<strong class=\"title\">")
                    .append("<a href=\"javascript:void(0)\" onclick=\"modalOpen(" + l.getOrder_num() + ")\">" + l.getMembersOrderList().get(index).getF_title() + "...외" + count + "개</a>")
                    .append("</strong>")
                    .append("<div class=\"boxInner\">")
                    .append("<figure class=\"boxInner__thumb\">")
                    .append("<img src=\"" + l.getIList().get(index).getI_path() + l.getIList().get(index).getI_sys_name() + "\">")
                    .append("</figure>")
                    .append("<div class=\"detail\">")
                    .append("<div class=\"detail__lft\">")
                    .append("<p class=\"boxInner__txt\">옵션 : " + l.getFList().get(index).getF_title() + "</p>")
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
        List<Order> list = mr.selectOrderDetail(num);
        log.info("selectOrderDetail:,{}", list);
        return makeOrderDatail(list);
    }

    private String makeOrderDatail(List<Order> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("<section class=\"orderPayment__sect accordion itemConfirm\">")
                .append("<div class=\"orderPayment__cont\" id=\"dvCartListArea\" style=\"padding-bottom: 0;\">")
                .append("<strong id=\"normalTitle\" class=\"itemConfirm__title normal-title\">- 주문 상세목록</strong>");
        list.forEach(l -> {
            int index = l.getFList().size();
            for (int i = 0; i < index; i++) {
                String dvPrice = l.getFList().get(i).getF_price().replaceAll("\\B(?=(\\d{3})+(?!\\d))", ",");
                int price = Integer.parseInt(l.getFList().get(i).getF_price());
                int count = Integer.parseInt(l.getOList().get(i).getDvCartCount());
                String price1 = (price * count) + "";
                price1 = price1.replaceAll("\\B(?=(\\d{3})+(?!\\d))", ",");
                sb.append("<div class=\"planMeals box\" id=\"normal-item\">")
                        .append("<div class=\"box__list single\">")
                        .append("<strong class=\"title\">")
                        .append("<a href=\"/fooditem/detail?f_num=" + l.getFList().get(i).getF_num() + "\">" + l.getFList().get(i).getF_title() + "</a>")
                        .append("</strong>")
                        .append("<div class=\"boxInner\">")
                        .append("<figure class=\"boxInner__thumb\">")
                        .append("<img src=\"" + l.getFList().get(i).getIList().get(0).getI_path() + l.getFList().get(i).getIList().get(0).getI_sys_name() + "\">")
                        .append("</figure>")
                        .append("<div class=\"detail\">")
                        .append("<div class=\"detail__lft\">")
                        .append("<p class=\"boxInner__txt\">옵션 : " + l.getFList().get(i).getF_title() + "</p>")
                        .append("<p class=\"boxInner__txt\">수량 : " + l.getOList().get(i).getDvCartCount() + "개 </p>")
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

    public List<FoodItem> getRanking() {
        return mr.getRanking();
    }

    public boolean insertNotice(String title, String contents, String id) {
        return mr.insertNotice(title, contents, id);
    }

    public List<Notice> getNoticeList() {
        List<Notice> nList = mr.getNoticeList();
        for (Notice n : nList) {
            n.setMember("관리자");
        }
        return nList;
    }

    public List<Member> checkId(String m_id) {
        log.info(">>>>>>>" + mr.checkId(m_id));
        return mr.checkId(m_id);
    }
}
