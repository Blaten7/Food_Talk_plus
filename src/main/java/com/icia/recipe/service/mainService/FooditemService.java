package com.icia.recipe.service.mainService;

import com.icia.recipe.dto.mainDto.*;
import com.icia.recipe.entity.Category;
import com.icia.recipe.entity.FoodItem;
import com.icia.recipe.entity.Img;
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

    private String makeFooditemListHtml(List<FooditemDto> list) {
        StringBuilder sb = new StringBuilder();
        list.forEach(fDto -> {
            sb.append("<li class=\"baby-product renew-badge\">")
                    .append("<a class=\"baby-product-link\" href=\"/fooditem/detail?f_num=" + fi.getFooditem_num() + "\" style=\"height: 466px\">")
                    .append("<dl class=\"baby-product-wrap adjust-spacing\" style=\"height: 444px\">")
                    .append("<dt class=\"image\">");
            if (fDto.getIList().isEmpty()) {
                sb.append("<img src=\"/uploadedImg/main/noImg.png\" width=\"100%\" onerror=\"this.src='/uploadedImg/main/noImg.png\"></dt>");
            } else {
                sb.append("<img src=\"" + fDto.getIList().get(0).getI_path() + fDto.getIList().get(0).getI_sys_name() + "\" width=\"100%\" onerror=\"this.src='/uploadedImg/main/나만의식단.jpg';\"></dt>");
            }
            sb.append("<dd class=\"descriptions\">")
                    .append("<div class=\"badges\"></div>")
                    .append("<div class=\"name\">" + fDto.getF_title() + "</div>")
                    .append("<div class=\"price-area\"><div class=\"price-wrap\"><div class=\"price\">")
                    .append("<strong class=\"price-value\" style=\"font-size: 15px\">" + fDto.getF_price() + "</strong>원")
                    .append("</div></div></div>")
                    .append("<div class=\"other-info\"><div class=\"rating-star\">")
                    .append("<span class=\"rating-total-count\">(" + fDto.getF_views() + ")</span>")
                    .append("</div></div></dd></dl></a></li>");
        });
        return sb.toString();
        //return null;
    }


    public String fooditemctg() {
        HashMap<String, String> hMap = new HashMap<>();
        List<Object[]> cList = new ArrayList<>();
        cList = cr.searchCtg();
        /* log.info("cList: {}",cList);*/
        return ctgMakeHtml(cList);
    }

    private String ctgMakeHtml(List<CtgDto> cList) {
        StringBuilder sb = new StringBuilder();
        cList.forEach(c1 -> {
            if (!c1.getC_num().isEmpty()) {
                sb.append("<li class=\"search-option-item\">")
                        .append("<input type=\"radio\" id=\"").append(c1.getC_num()).append("\">")
                        .append("<label for=\"").append(c1.getC_num()).append("\">")
                        .append("<a class=\"seo-link-url\" href=\"#\" onclick=\"searchctg(this,"+c1.getC_num()+")\"").append(c1.getC_num()).append("\">")
                        .append(c1.getC_name()).append("</a></label>")
                        .append("<a href=\"#\" class=\"btn-fold\">열림</a>");

                if (!c1.getList().isEmpty()) {
                    sb.append("<ul class=\"search-option-items-child\">");
                    c1.getList().forEach(c2 -> {
                        if (!c2.getC_num().isEmpty()) {
                            sb.append("<li class=\"search-option-item\">")
                                    .append("<input type=\"radio\" id=\"").append(c2.getC_num()).append("\">")
                                    .append("<label for=\"").append(c2.getC_num()).append("\">")
                                    .append("<a class=\"seo-link-url\" href=\"#\" onclick=\"searchctg(this,"+c2.getC_num()+")\"").append(c2.getC_num()).append("\">")
                                    .append(c2.getC_name()).append("</a></label>")
                                    .append("<a href=\"#\" class=\"btn-fold\">열림</a>");

                            if (!c2.getList().isEmpty()) {
                                sb.append("<ul class=\"search-option-items-child\">");
                                c2.getList().forEach(c3 -> {
                                    if (!c3.getC_num().isEmpty()) {
                                        sb.append("<li class=\"search-option-item\">")
                                                .append("<input type=\"radio\" id=\"").append(c3.getC_num()).append("\">")
                                                .append("<label for=\"").append(c3.getC_num()).append("\">")
                                                .append("<a class=\"seo-link-url\" href=\"#\" onclick=\"searchctg(this,"+c3.getC_num()+")\"").append(c3.getC_num()).append("\">")
                                                .append(c3.getC_name()).append("</a></label>")
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
//        if (!list.get(0).getCList().isEmpty()) {
//            String img = makeFoodDetailImg(list.get(0).getIList());
//            model.addAttribute("img", img);
//        }
        return list;
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
        List<Object[]> fDto = null;
        String makeHtml = "";
        switch (type) {
            case "foodInfo":
                fDto = fr.searchFoodDetailInfo(num);
                makeHtml = "<div>" + fDto.get(0).getF_contents() + "</div>";
                break;
//            case "foodReview":
//                fDao.searchFoodDetailReview();
//                break;
//            case "foodInquiry":
//                fDao.searchFoodDetailInquiry();
//                break;
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

    public List<FoodItem> getRanking50() {
        return fr.getRanking50();
    }
}

