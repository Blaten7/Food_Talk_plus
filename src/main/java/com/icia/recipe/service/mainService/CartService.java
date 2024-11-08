package com.icia.recipe.service.mainService;

import com.icia.recipe.dto.mainDto.CartDto;
import com.icia.recipe.dto.mainDto.FooditemDto;
import com.icia.recipe.dto.mainDto.ImgDto;
import com.icia.recipe.dto.mainDto.InputOrderDto;
import com.icia.recipe.dto.manageDto.MemberDto;
import com.icia.recipe.repository.CartRepository;
import com.icia.recipe.repository.FoodItemRepository;
import com.icia.recipe.repository.MemberRepository;
import com.icia.recipe.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class CartService {

    @Autowired
    CartRepository cr;
    @Autowired
    MemberRepository mr;
    @Autowired
    OrderRepository or;
    @Autowired
    FoodItemRepository fr;

    public String intoCartList(HashMap<String, String> hMap, Model model) {
        int result = cr.insertCartList(hMap.get("num"), hMap.get("count"), hMap.get("user"));
        if (result <= 0) {
            model.addAttribute("msg", "성공하지 못하였습니다. 관리자에게 문의해주십시요");
            return "redirect:/";
        }
        return null;
    }

    public String selectCart(String name, Model model) {
        List<Object[]> cDto = cr.selectCart(name);
        log.info("장바구니에 있는 수: {}", cDto.size());
        model.addAttribute("count", cDto.size());
        return makeCartList(cDto);
    }

    private String makeCartList(List<Object[]> cDtoList) {
        StringBuilder sb = new StringBuilder();
        cDtoList.forEach(cart -> {
            // Object[] 배열 인덱스에 따라 필요한 값 추출
            String caNum = (String) cart[0];
            int caCount = (int) cart[1];
            String caDate = (String) cart[2];
            String cName = (String) cart[3];
            List<FooditemDto> fList = (List<FooditemDto>) cart[4];
            List<ImgDto> iList = (List<ImgDto>) cart[5];

            // 상품 정보
            int fPrice = Integer.parseInt(fList.get(0).getF_price());
            String soldAll = String.format("%,d", fPrice * caCount);
            String sold = String.format("%,d", fPrice);

            // HTML 구조 생성
            sb.append("<li class=\"orderCartBox__list single\"><div class=\"orderCartBox__list__inner\">")
                    .append("<input type=\"hidden\" name=\"dvItemId\" value=\"").append(fList.get(0).getF_num()).append("\">")
                    .append("<input type=\"hidden\" name=\"dvItemName\" value=\"").append(fList.get(0).getF_title()).append("\">")
                    .append("<input type=\"hidden\" name=\"dvItemPrice\" value=\"").append(fPrice).append("\">")
                    .append("<input type=\"hidden\" name=\"dvCartId\" value=\"").append(caNum).append("\">")
                    .append("<input type=\"hidden\" name=\"dvItemCount\" value=\"").append(caCount).append("\">")
                    .append("<input type=\"hidden\" name=\"dvItemMaxcount\" value=\"").append(fList.get(0).getF_count()).append("\">")
                    .append("<input type=\"hidden\" name=\"dvItemImg\" value=\"").append(iList.get(0).getI_path()).append(iList.get(0).getI_sys_name()).append("\">")
                    .append("<div class=\"checkBoxAll\"><span class=\"checks\">")
                    .append("<input type=\"checkbox\" id=\"prdSelect").append(caNum).append("\" name=\"all\" title=\"상품선택\" checked=\"checked\">")
                    .append("<label for=\"prdSelect").append(caNum).append("\"></label>")
                    .append("</span></div>")
                    .append("<div class=\"orderCartBox__list__cont\"><figure class=\"cont__img\">")
                    .append("<a href=\"/fooditem/detail?f_num=").append(fList.get(0).getF_num()).append("\">")
                    .append("<img src=\"").append(iList.get(0).getI_path()).append(iList.get(0).getI_sys_name()).append("\" onerror=\"this.src='/uploadedImg/main/noImg.png';\">")
                    .append("</a></figure><div class=\"cont__text\">")
                    .append("<a href=\"/fooditem/detail?f_num=").append(fList.get(0).getF_num()).append("\">")
                    .append("<strong class=\"cont__title\">").append(cName).append(" ").append(fList.get(0).getF_title()).append("</strong>")
                    .append("</a></div></div>")
                    .append("<span class=\"selling-price price01\">").append(sold).append("원</span><div class=\"prd_value\">")
                    .append("<span class=\"prd_length\"><input type=\"text\" min=\"1\" max=\"").append(fList.get(0).getF_count()).append("\" class=\"dvCartDetlQty\" value=\"").append(caCount).append("\">")
                    .append("<button type=\"button\" class=\"btn_st1_plus\">수량 감소</button><button type=\"button\" class=\"btn_st1_minus\">수량 증가</button>")
                    .append("</span> <span class=\"discount-tooltip bottom qtyDcToolTip_127565 hide\"></span> </div>")
                    .append("<span class=\"selling-price price02\">").append(soldAll).append("원</span><button type=\"button\" class=\"btn__list-delete dvDelCartDetl\">")
                    .append("<span class=\"hidden\">삭제</span></button></div></li>");
        });
        return sb.toString();
    }


    public String deleteCart(List<Long> num, Model model, String name) {
        for (Long e : num) {
            int result = cr.deleteCart(e);
        }
        List<Object[]> cDto = cr.selectCart(name);
        return makeCartList(cDto);
    }

    public void selectMember(String name, Model model) {
        Object[] mDto = mr.check(name);
        String[] phoneNumber = mDto[4].toString().split("-");
        String[] memberId = mDto[0].toString().split("@");
        Arrays.stream(phoneNumber).forEach(c -> log.info("번호:{}", c));
        model.addAttribute("name", mDto[2]);
        model.addAttribute("phoneNumber1", phoneNumber[1]);
        model.addAttribute("phoneNubmer2", phoneNumber[2]);
        model.addAttribute("memberId1", memberId[0]);
        model.addAttribute("memberId2", memberId[1]);

    }

    public String makeHtmlCartOrder(List<HashMap<String, String>> list, Model model) {
        StringBuilder sb = new StringBuilder();
        AtomicInteger dvItemAllPrice = new AtomicInteger();
        list.forEach(l -> {
            int price = Integer.parseInt(l.get("dvItemPrice"));
            int count = Integer.parseInt(l.get("dvItemCount"));
            int allPrice = price * count;
            dvItemAllPrice.addAndGet(allPrice);
            // 숨겨진 입력 필드 추가
            sb.append("<input type=\"hidden\" class=\"dvCartCount\" id=\"dvCartCount_").append(count).append("\" value=\"").append(count).append("\">")
                    .append("<input type=\"hidden\" class=\"dvCartDetlId\" id=\"dvCartDetlId_").append(l.get("dvItemId")).append("\" value=\"").append(l.get("dvItemId")).append("\">");
            // 제목 추가
            sb.append("<strong class=\"title\">").append(l.get("dvItemName")).append("</strong>");
            // 내부 박스 추가
            sb.append("<div class=\"boxInner\">")
                    .append("<figure class=\"boxInner__thumb\">")
                    .append("<img src=\"").append(l.get("dvItemImg")).append("\"").append(">")
                    .append("</figure>")
                    .append("<div class=\"detail\">")
                    .append("<div class=\"detail__lft\"><p class=\"boxInner__txt\">옵션 :").append(l.get("dvItemName")).append("</p>")
                    .append("<p class=\"boxInner__txt\">수량 :").append(count).append("개").append("</p></div>")
                    .append("<div class=\"detail__rgt price\">")
                    .append("<span class=\"price__list\" id=\"total_price_list\">상품금액 <span class=\"num\">").append((price + "").replaceAll("\\B(?=(\\d{3})+(?!\\d))", ",")).append("원").append("</span></span>")
                    .append("<strong class=\"price__list\">결제예정금액 <span class=\"num\">").append((price * count + "").replaceAll("\\B(?=(\\d{3})+(?!\\d))", ",")).append("원</span></strong>")
                    .append("</div>").append("</div>").append("</div>");
        });
        model.addAttribute("price", (dvItemAllPrice.get() + "").replaceAll("\\B(?=(\\d{3})+(?!\\d))", ","));
        return sb.toString();
    }
    @Transactional
    public boolean inputOrder(String price, InputOrderDto iOrder, String id) {
        // 주문 삽입
        HashMap<String,Object> hMap=new HashMap<>();
        log.info("inputOrder,{}",iOrder);
        String result = price.replace(",", "");
        hMap.put("price", result);
        hMap.put("id", id);
        hMap.put("count",iOrder.getItemList().size());
        iOrder.getInputList().get(0).setData(hMap);
        boolean order = or.insertOrder(iOrder.getInputList().get(0));
        if (!order) {
            throw new RuntimeException("Failed to insert order");
        }
        // 주문 상세 삽입
        iOrder.getItemList().forEach(i->{
            i.setData(hMap);
            boolean itemResult = or.insertOrderDetail(i);
            if (!itemResult) {
                throw new RuntimeException("Failed to insert order detail");
            }
        });
        // 게시글 수량 빼기
        iOrder.getItemList().forEach(i->{
            boolean itemMinus = fr.updateFooditemCount(i);
            if (!itemMinus) {
                throw new RuntimeException("Failed to update fooditem count");
            }
        });
        // 장바구니 삭제
        int cartDelete = cr.deleteCartName(id);
        if (cartDelete<0) {
            throw new RuntimeException("Failed to delete cart name");
        }
        return true;
    }
   /* @Transactional
    public boolean inputOrder(String price, List<String> inputList, List<String> itemList, String id, String count) {
        HashMap<String, String> inputMap = new HashMap<>();
        inputList.forEach(i -> {
            String[] element = i.split(":");
            inputMap.put(element[0], element[1]);
        });
        String result = price.replace(",", "");
        inputMap.put("id", id);
        inputMap.put("price", result);
        inputMap.put("count", count);
        log.info("아...{}", inputMap);
        List<HashMap<String, String>> itemMapList = new ArrayList<>();
        log.info("itemList: {}", itemList);
        log.info("itemList size: {}", itemList.size());

        itemList.forEach(i -> {
            String[] element = i.replaceAll("[\\[\\]]", "").replaceAll("[\\{\\}\"]", "").split(",");
            HashMap<String, String> itemMap = new HashMap<>();
            log.info("왜 이게 2번? {}", element);
            Arrays.stream(element).forEach(a -> {
                String[] element2 = a.split("_");
                if(!Objects.equals(element2[0], "dvCartDetlId")){
                    itemMap.put(element2[0], element2[1]);
                }else{
                    itemMap.put(element2[0], element2[1]);
                    itemMap.put("id", id);
                    itemMapList.add(itemMap);
                }
            });
        });
        log.info("itemMapList {}", itemMapList);
        inserOrederLast(inputMap, itemMapList, id);
        return false;
    }

    @Transactional
    public void inserOrederLast(HashMap<String, String> inputMap, List<HashMap<String, String>> itemMapList, String id) {
        // 주문 삽입
        boolean order = cDao.insertOrder(inputMap);
        if (!order) {
            throw new RuntimeException("Failed to insert order");
        }

        // 주문 상세 삽입
        for (HashMap<String, String> item : itemMapList) {
            boolean itemResult = cDao.insertOrderDetail(item);
            if (!itemResult) {
                throw new RuntimeException("Failed to insert order detail");
            }
        }

        // 장바구니 삭제
        boolean cartDelete = cDao.deleteCartName(id);
        if (!cartDelete) {
            throw new RuntimeException("Failed to delete cart name");
        }
    }*/
}