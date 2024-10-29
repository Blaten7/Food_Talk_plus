package com.icia.recipe.service.manageService;

import com.icia.recipe.dto.mainDto.FooditemDto;
import com.icia.recipe.dto.manageDto.FoodItemDto;
import com.icia.recipe.dto.manageDto.InvenDto;
import com.icia.recipe.dto.manageDto.MemberDto;
import com.icia.recipe.repository.FoodItemRepository;
import com.icia.recipe.repository.InvenAddRepository;
import com.icia.recipe.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class SearchService {

    @Autowired
    InvenAddRepository ir;
    @Autowired
    FoodItemRepository fr;
    @Autowired
    MemberRepository mr;

    public List<?> getSearchModalDetails(String className, ArrayList param) {
        List<?> thisList = null;
        switch (className) {
            case "inven":
                String cname = param.get(0).toString();
                String code = param.get(1).toString();
                String name = param.get(2).toString();
                thisList = ir.getSearchModalDetails(cname, code, name);
                break;
            case "invenAdd":
                String company = param.get(0).toString();
                String iname = param.get(1).toString();
//                thisList = ir.getSearchModalDetailsInven(company, iname);
                thisList = null;
                break;
            default:
        }
        log.info(thisList.toString());
        return thisList;
    }
    public List<?> getSearchListAll(String Keyword) {
        String[] keywords;
        if (Keyword.contains(" ")) {
            keywords = Keyword.split(" ");
        } else if (Keyword.contains(", ")) {
            keywords = Keyword.split(", ");
        } else {
            keywords = null;
        }
        // 재고
        List<FoodItemDto> inven = fr.getInvenList();
        List<FoodItemDto> searchInven = inven.stream()
                .filter(fis -> keywords == null ? (
                        fis.getC_name().contains(Keyword) ||
                                fis.getF_date().contains(Keyword) ||
                                fis.getF_date2().contains(Keyword) ||
                                fis.getF_edate().contains(Keyword) ||
                                fis.getF_edate2().contains(Keyword) ||
                                fis.getF_price().contains(Keyword) ||
                                fis.getF_code().contains(Keyword) ||
                                fis.getF_count().contains(Keyword) ||
                                fis.getF_title().contains(Keyword)
                ) : Arrays.stream(keywords).anyMatch(k ->
                        fis.getC_name().contains(k) ||
                                fis.getF_date().contains(k) ||
                                fis.getF_date2().contains(k) ||
                                fis.getF_edate().contains(k) ||
                                fis.getF_edate2().contains(k) ||
                                fis.getF_price().contains(k) ||
                                fis.getF_code().contains(k) ||
                                fis.getF_count().contains(k) ||
                                fis.getF_title().contains(k)
                ))
                .toList();

// 발주내역
        List<InvenDto> invenAdd = ir.getInvenAddList();
        List<InvenDto> searchInvenAdd = invenAdd.stream()
                .filter(ia -> keywords == null ? (
                        ia.getIv_vat().contains(Keyword) ||
                                ia.getIv_total().contains(Keyword) ||
                                ia.getIv_price().contains(Keyword) ||
                                ia.getIv_name().contains(Keyword) ||
                                ia.getIv_current().contains(Keyword) ||
                                ia.getIv_count().contains(Keyword) ||
                                ia.getIv_company().contains(Keyword) ||
                                ia.getIv_priceSum().contains(Keyword)
                ) : Arrays.stream(keywords).anyMatch(k ->
                        ia.getIv_vat().contains(k) ||
                                ia.getIv_total().contains(k) ||
                                ia.getIv_price().contains(k) ||
                                ia.getIv_name().contains(k) ||
                                ia.getIv_current().contains(k) ||
                                ia.getIv_count().contains(k) ||
                                ia.getIv_company().contains(k) ||
                                ia.getIv_priceSum().contains(k)
                ))
                .toList();
        // 식자재
        List<FoodItemDto> fiList = fr.getFooditemList();
        List<FoodItemDto> searchFoodItem = fiList.stream()
                .filter(fi -> keywords == null ? (
                        fi.getF_title().contains(Keyword) ||
                                fi.getF_price().contains(Keyword) ||
                                fi.getF_count().contains(Keyword) ||
                                fi.getF_date().contains(Keyword) ||
                                fi.getF_edate().contains(Keyword)
                        ) : Arrays.stream(keywords).anyMatch(k ->
                        fi.getF_title().contains(k) ||
                                fi.getF_price().contains(k) ||
                                fi.getF_count().contains(k) ||
                                fi.getF_date().contains(k) ||
                                fi.getF_edate().contains(k)
                )).toList();
        List<MemberDto> memList = mr.getMemberList();
        List<MemberDto> searchMember = memList.stream()
                .filter(m -> keywords == null ? (
                        m.getM_id().contains(Keyword) ||
                                m.getM_name().contains(Keyword) ||
                                m.getM_address().contains(Keyword) ||
                                m.getM_phone().contains(Keyword)
                        ) : Arrays.stream(keywords).anyMatch(k ->
                        m.getM_id().contains(k) ||
                        m.getM_name().contains(k) ||
                                m.getM_address().contains(k) ||
                                m.getM_phone().contains(k)
                        )).toList();
        List<List<?>> combinedList = new ArrayList<>();
        combinedList.add(searchInven);
        combinedList.add(searchInvenAdd);
        combinedList.add(searchFoodItem);
        combinedList.add(searchMember);
        return combinedList;
    }
}
