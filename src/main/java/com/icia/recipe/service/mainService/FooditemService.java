package com.icia.recipe.service.mainService;

import com.icia.recipe.dto.mainDto.*;
import com.icia.recipe.entity.FoodItem;
import com.icia.recipe.repository.CategoryRepository;
import com.icia.recipe.repository.FoodItemRepository;
import com.icia.recipe.repository.ImgRepository;
import com.icia.recipe.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;

@Service
@Slf4j
public class FooditemService {

    FoodItem fi;

    FoodItemRepository fir;

    @Autowired
    FoodItemRepository fr;

    @Autowired
    CategoryRepository cr;

    @Autowired
    MemberRepository mr;

    @Autowired
    ImgRepository ir;

    public String fooditemOrder(String order, String num, SearchDto sDto) {
        log.info("fooditemOrder 입장");
        String name = "";
        String sort = "";
        switch (order) {
            case "salePriceAsc":
                name = "f.f_price";
                break;
            case "salePriceDesc":
                name = "f.f_price";
                sort = "desc";
                break;
            case "saleCountDesc":
                name = "f.f_views";
                sort = "desc";
                break;
            case "latestAsc":
                name = "f.f_num";
                sort = "desc";
                break;
            default:
                log.info("이상한거 들어옴");
                return null;
        }
        String cNum = num.substring(0, 1);
        log.info("num:{}", num);
        String numName = "";
        switch (cNum) {
            case "1":
                numName = "c1.c_num";
                break;
            case "2":
                numName = "f.c_num2";
                break;
            case "3", "4":
                numName = "f.c_num2";
                break;
            default:
                numName = "zzzz";
        }

        log.info("order: {},{}", name, sort);
        log.info("ctg: {},{}", numName, num);
        List<Object[]> list = null; //new ArrayList<>();
        HashMap<String, String> hMap = new HashMap<>();
        hMap.put("name", name);
        hMap.put("sort", sort);
        hMap.put("num", num);
        sDto.setData(hMap);
        list = fr.searchFoodItem(sDto, numName);
        //list = list.stream().sorted(Comparator.comparing(FooditemDto::getF_price)).collect(Collectors.toList());
        log.info("fDto: {}", list);
        return makeFooditemListHtml(list);
    }

//    public String searchctg(String c_num) {
//        String num = c_num.substring(0, 1);
//        log.info("num:{}", num);
//        String numName = "";
//        switch (num) {
//            case "1":
//                numName = "c1.c_num";
//                break;
//            case "2":
//                numName = "c2.c_num";
//                break;
//            case "3", "4":
//                numName = "c3.c_num";
//                break;
//            default:
//                return null;
//        }
//        log.info("보내는 것: {},{} ", numName, c_num);
//        List<FoodItem> list = .searchCtg(numName, c_num);
//        return makeFooditemListHtml(list);
//    }

    private String makeFooditemListHtml(List<Object[]> list) {
        StringBuilder sb = new StringBuilder();
        list.forEach(fDto -> {
            sb.append("<li class=\"baby-product renew-badge\">")
                    .append("<a class=\"baby-product-link\" href=\"/fooditem/detail?f_num=" + fDto[0] + "\" style=\"height: 466px\">") // f_num
                    .append("<dl class=\"baby-product-wrap adjust-spacing\" style=\"height: 444px\">")
                    .append("<dt class=\"image\">");

            List<Object[]> iList = (List<Object[]>) fDto[14]; // iList

            if (iList.isEmpty()) {
                sb.append("<img src=\"/uploadedImg/main/noImg.png\" width=\"100%\" onerror=\"this.src='/uploadedImg/main/noImg.png\"></dt>");
            } else {
                Object[] img = iList.get(0);
                sb.append("<img src=\"" + img[0] + img[1] + "\" width=\"100%\" onerror=\"this.src='/uploadedImg/main/나만의식단.jpg';\"></dt>"); // i_path + i_sys_name
            }

            sb.append("<dd class=\"descriptions\">")
                    .append("<div class=\"badges\"></div>")
                    .append("<div class=\"name\">" + fDto[1] + "</div>") // f_title
                    .append("<div class=\"price-area\"><div class=\"price-wrap\"><div class=\"price\">")
                    .append("<strong class=\"price-value\" style=\"font-size: 15px\">" + fDto[3] + "</strong>원") // f_price
                    .append("</div></div></div>")
                    .append("<div class=\"other-info\"><div class=\"rating-star\">")
                    .append("<span class=\"rating-total-count\">(" + fDto[7] + ")</span>") // f_views
                    .append("</div></div></dd></dl></a></li>");
        });
        return sb.toString();
    }



    public String fooditemctg() {
        HashMap<String, String> hMap = new HashMap<>();
        List<Object[]> cList = new ArrayList<>();
        cList = cr.searchCtg();
        /* log.info("cList: {}",cList);*/
        return ctgMakeHtml(cList);
    }

    private String ctgMakeHtml(List<Object[]> cList) {
        StringBuilder sb = new StringBuilder();
        cList.forEach(c1 -> {
            String c1Num = (String) c1[0];
            String c1Name = (String) c1[1];
            List<CtgDto> c1List = (List<CtgDto>) c1[2];

            if (!c1Num.isEmpty()) {
                sb.append("<li class=\"search-option-item\">")
                        .append("<input type=\"radio\" id=\"").append(c1Num).append("\">")
                        .append("<label for=\"").append(c1Num).append("\">")
                        .append("<a class=\"seo-link-url\" href=\"#\" onclick=\"searchctg(this,").append(c1Num).append(")\">")
                        .append(c1Name).append("</a></label>")
                        .append("<a href=\"#\" class=\"btn-fold\">열림</a>");

                if (!c1List.isEmpty()) {
                    sb.append("<ul class=\"search-option-items-child\">");
                    c1List.forEach(c2 -> {
                        String c2Num = c2.getC_num();
                        String c2Name = c2.getC_name();
                        List<CtgDto> c2List = c2.getList();

                        if (!c2Num.isEmpty()) {
                            sb.append("<li class=\"search-option-item\">")
                                    .append("<input type=\"radio\" id=\"").append(c2Num).append("\">")
                                    .append("<label for=\"").append(c2Num).append("\">")
                                    .append("<a class=\"seo-link-url\" href=\"#\" onclick=\"searchctg(this,").append(c2Num).append(")\">")
                                    .append(c2Name).append("</a></label>")
                                    .append("<a href=\"#\" class=\"btn-fold\">열림</a>");

                            if (!c2List.isEmpty()) {
                                sb.append("<ul class=\"search-option-items-child\">");
                                c2List.forEach(c3 -> {
                                    String c3Num = c3.getC_num();
                                    String c3Name = c3.getC_name();

                                    if (!c3Num.isEmpty()) {
                                        sb.append("<li class=\"search-option-item\">")
                                                .append("<input type=\"radio\" id=\"").append(c3Num).append("\">")
                                                .append("<label for=\"").append(c3Num).append("\">")
                                                .append("<a class=\"seo-link-url\" href=\"#\" onclick=\"searchctg(this,").append(c3Num).append(")\">")
                                                .append(c3Name).append("</a></label>")
                                                .append("</li>");
                                    }
                                });
                                sb.append("</ul>");
                            }
                            sb.append("</li>");
                        }
                    });
                    sb.append("</ul>");
                }
                sb.append("</li>");
            }
        });
        return sb.toString();
    }


    public List<FooditemDto> searchFoodDetail(String num, Model model) {
        log.info("views : ", fr.viewsPlus(num));
        List<Object[]> list = fr.searchFoodDetail(num);

        // FooditemDto 리스트로 변환
        List<FooditemDto> fooditemList = new ArrayList<>();
        list.forEach(obj -> {
            FooditemDto fooditem = new FooditemDto(
                    (int) obj[0],                  // f_num
                    (String) obj[1],               // f_title
                    (String) obj[2],               // f_contents
                    (String) obj[3],               // f_price
                    (String) obj[4],               // f_count
                    (String) obj[5],               // f_date
                    (String) obj[6],               // f_edate
                    (int) obj[7],                  // f_views
                    (String) obj[8],               // f_volume
                    (String) obj[9],               // f_cal
                    (String) obj[10],              // f_save
                    (String) obj[11],              // e_date
                    (String) obj[12],              // f_code
                    (String) obj[13]               // f_origin
            );
            List<CtgDto> cList = (List<CtgDto>) obj[14]; // 카테고리 리스트
            List<ImgDto> iList = (List<ImgDto>) obj[15]; // 이미지 리스트

            fooditem.setCList(cList);
            fooditem.setIList(iList);
            fooditemList.add(fooditem);
        });

        // 이미지 리스트가 있는 경우 처리
        if (!fooditemList.isEmpty() && !fooditemList.get(0).getIList().isEmpty()) {
            String img = makeFoodDetailImg(fooditemList.get(0).getIList());
            model.addAttribute("img", img);
        }

        return fooditemList;
    }


    public String makeFoodDetailImg(List<ImgDto> iList) {
        StringBuilder sb = new StringBuilder();
        iList.forEach(i -> {
            sb.append("<div class=\"slick-slide\"><div><div class=\"slider__list\" style=\"width: 100%;display: inline-block;\">");
            sb.append("<figure><img src=\"" + i.getI_path() + i.getI_sys_name() + "\"style=\"width: 550px; height: 550px;\" alt=\"Food Image\">");
            sb.append("</figure></div></div></div>");
        });
        return sb.toString();
    }

    public String fooditemDetailinfo(String num, String type) {
        List<Object[]> fDto;
        String makeHtml = "";

        switch (type) {
            case "foodInfo":
                fDto = fr.searchFoodDetailInfo(num);

                // Object[] 배열의 첫 번째 요소로부터 f_contents 필드를 가져옵니다.
                if (fDto != null && !fDto.isEmpty()) {
                    String fContents = (String) fDto.get(0)[2]; // f_contents는 Object[]의 세 번째 요소
                    makeHtml = "<div>" + fContents + "</div>";
                }
                break;
            case "change":
                makeHtml = " <img src=\"/uploadedImg/main/@1100x2275.png\" style=\"width:1100px; height: auto; max-width:none\" >";
                break;
        }

        return makeHtml;
    }


    public String getPaging(String num, SearchDto sDto, String listUrl) {
        log.info("여기가 아니야?{}", num);
        if (num == null) {
            num = "no";
        }
        int totalNum = fr.getFooditemCount(num);
        log.info("totalNum : {}", totalNum);
        Paging paging = new Paging(totalNum, sDto.getPageNum(), sDto.getListCnt(), sDto.getListCnt(), listUrl);
        return paging.makeHtmlPaging();
    }

    public List<Object[]> getRanking50() {
        return fr.getRanking50();
    }
}

