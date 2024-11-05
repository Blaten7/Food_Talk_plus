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
        List<Object[]> inven = fr.getInvenList();
        List<Object[]> searchInven = inven.stream()
                .filter(fis -> keywords == null ? (
                        fis[1].toString().contains(Keyword) || // C_name
                                fis[5].toString().contains(Keyword) || // F_date
                                fis[6].toString().contains(Keyword) || // F_date2
                                fis[7].toString().contains(Keyword) || // F_edate
                                fis[8].toString().contains(Keyword) || // F_edate2
                                fis[2].toString().contains(Keyword) || // F_price
                                fis[3].toString().contains(Keyword) || // F_code
                                fis[4].toString().contains(Keyword) || // F_count
                                fis[0].toString().contains(Keyword)   // F_title
                ) : Arrays.stream(keywords).anyMatch(k ->
                        fis[1].toString().contains(k) ||
                                fis[5].toString().contains(k) ||
                                fis[6].toString().contains(k) ||
                                fis[7].toString().contains(k) ||
                                fis[8].toString().contains(k) ||
                                fis[2].toString().contains(k) ||
                                fis[3].toString().contains(k) ||
                                fis[4].toString().contains(k) ||
                                fis[0].toString().contains(k)
                ))
                .toList();

        // 발주내역
        List<Object[]> invenAdd = ir.getInvenAddList();
        List<Object[]> searchInvenAdd = invenAdd.stream()
                .filter(ia -> keywords == null ? (
                        ia[7].toString().contains(Keyword) || // Iv_vat
                                ia[6].toString().contains(Keyword) || // Iv_total
                                ia[5].toString().contains(Keyword) || // Iv_price
                                ia[4].toString().contains(Keyword) || // Iv_name
                                ia[3].toString().contains(Keyword) || // Iv_current
                                ia[2].toString().contains(Keyword) || // Iv_count
                                ia[1].toString().contains(Keyword) || // Iv_company
                                ia[0].toString().contains(Keyword)    // Iv_priceSum
                ) : Arrays.stream(keywords).anyMatch(k ->
                        ia[7].toString().contains(k) ||
                                ia[6].toString().contains(k) ||
                                ia[5].toString().contains(k) ||
                                ia[4].toString().contains(k) ||
                                ia[3].toString().contains(k) ||
                                ia[2].toString().contains(k) ||
                                ia[1].toString().contains(k) ||
                                ia[0].toString().contains(k)
                ))
                .toList();

        // 식자재
        List<Object[]> fiList = fr.getFooditemList();
        List<Object[]> searchFoodItem = fiList.stream()
                .filter(fi -> keywords == null ? (
                        fi[0].toString().contains(Keyword) || // F_title
                                fi[1].toString().contains(Keyword) || // F_price
                                fi[2].toString().contains(Keyword) || // F_count
                                fi[3].toString().contains(Keyword) || // F_date
                                fi[4].toString().contains(Keyword)    // F_edate
                ) : Arrays.stream(keywords).anyMatch(k ->
                        fi[0].toString().contains(k) ||
                                fi[1].toString().contains(k) ||
                                fi[2].toString().contains(k) ||
                                fi[3].toString().contains(k) ||
                                fi[4].toString().contains(k)
                ))
                .toList();

        List<Object[]> memList = mr.getMemberList();
        List<Object[]> searchMember = memList.stream()
                .filter(m -> keywords == null ? (
                        m[0].toString().contains(Keyword) || // M_id
                                m[1].toString().contains(Keyword) || // M_name
                                m[2].toString().contains(Keyword) || // M_address
                                m[3].toString().contains(Keyword)    // M_phone
                ) : Arrays.stream(keywords).anyMatch(k ->
                        m[0].toString().contains(k) ||
                                m[1].toString().contains(k) ||
                                m[2].toString().contains(k) ||
                                m[3].toString().contains(k)
                ))
                .toList();

        List<List<?>> combinedList = new ArrayList<>();
        combinedList.add(searchInven);
        combinedList.add(searchInvenAdd);
        combinedList.add(searchFoodItem);
        combinedList.add(searchMember);
        return combinedList;
    }

}
