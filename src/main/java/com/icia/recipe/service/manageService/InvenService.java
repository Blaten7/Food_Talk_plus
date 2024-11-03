package com.icia.recipe.service.manageService;

import com.icia.recipe.dto.manageDto.FoodItemDto;
import com.icia.recipe.dto.manageDto.InvenDto;
import com.icia.recipe.repository.FoodItemRepository;
import com.icia.recipe.repository.InvenAddRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class InvenService {

    @Autowired
    InvenAddRepository ir;
    @Autowired
    FoodItemRepository fr;

    public List<Object[]> getInvenList(Integer pageNum, Integer pageSize) {
        log.info("[재고] 서비스 진입");
        List<Object[]> fList = fr.getInvenList();
        int totalListCnt = fList.size();
        int fromIdx = (pageNum - 1) * pageSize;
        int toIdx = Math.min(fromIdx + pageSize, totalListCnt);
//        for (FoodItemDto fi : fList) {
//            String count = fi.getF_count();
//            if (count.equals('0')) {
//
//            }
//        }

        if (fromIdx >= totalListCnt) {
            return List.of(); // 페이지 범위가 전체 리스트 크기를 초과하는 경우 빈 리스트 반환
        }
        for (Object[] fi : fList) {
            if (fi.getF_title().length() >= 6) {
                fi.setF_title(fi.getF_title().substring(0, 5) + "...");
            }
        }
        return fList.subList(fromIdx, toIdx);
    }

    boolean flag = true;

    public List<?> getInvenListSort(String id, Integer pageNum, Integer pageSize) {
        log.info("[재고] 정렬 서비스 진입");
        String sort = "";
        String param = "";
        if (flag) {
            sort = "desc";
            flag = false;
        } else {
            sort = "asc";
            flag = true;
        }
        if (id.charAt(0) == 'f') {
            switch (id) {
                case "fdate":
                    param = "oldest_date";
                    break;
                case "fedate":
                    param = "oldest_edate";
                    break;
                case "fcode":
                    param = "f_code";
                    break;
                case "fcname":
                    param = "c_name";
                    break;
                case "ftitle":
                    param = "f_title";
                    break;
                case "fcount":
                    param = "countSum";
                    break;
                case "favgPrice":
                    param = "avgPrice";
                    break;
                case "fcountSum":
                    param = "total";
                    break;
                default:
                    log.info("[재고] 서비스 에러 1");
                    return null;
            }
        } else if (id.charAt(0) == 'e') {
            switch (id) {
                case "edate":
                    param = "f_edate";
                    break;
                case "ecname":
                    param = "c_name";
                    break;
                case "etitle":
                    param = "f_title";
                    break;
                case "ecount":
                    param = "f_count";
                    break;
                default:
                    log.info("[재고] 서비스 에러 2");
                    return null;
            }
        }
        List<Object[]> iList = fr.getSortedInvenList(param, sort);
        for (Object[] fi : iList) {
            if (fi.getF_title().length() >= 6) {
                fi.setF_title(fi.getF_title().substring(0, 5) + "...");
            }
        }
        int totalListCnt = iList.size();
        int fromIdx = (pageNum - 1) * pageSize;
        int toIdx = Math.min(fromIdx + pageSize, totalListCnt);
        if (fromIdx > totalListCnt) {
            return List.of();
        }
        return iList.subList(fromIdx, toIdx);

    }

    @Transactional
    public List<?> deleteFromFooditem(ArrayList deleteKeySet, Integer pageNum, Integer pageSize) {
        boolean update = fr.updateFoodItem(deleteKeySet);
        if (update) {
            return getInvenList(pageNum, pageSize);
        } else {
            log.info("[폐기] 삭제 실패");
            return null;
        }
    }

    public List<?> insertInvenAdd(Integer pageNum, Integer pageSize, String company,
                                  String name, String count, String price) {
        boolean result = ir.insertInvenAdd(count, company, name, price);
        if (result) {
            return ir.getInvenAddList();
        } else {
            log.info("[추가] 서비스 에러");
            return null;
        }
    }

    public List<Object[]> finalOrder() {
        boolean update = ir.finalOrder();
        if (update) {
            return ir.getInvenAddList();
        } else {
            log.info("[발주] 서비스 에러");
            return ir.getInvenAddList();
        }
    }

    public List<?> getSearchListInven(Integer pageNum, Integer pageSize, String searchKeyword, String tab) {
        String table = "";
        List<Object[]> fList = null;
        List<Object[]> iList = null;
        List<?> searchList = null;
        switch (tab) {
            case "invenM":
            case "invenE": // 재고량 확인, 유통기한
                fList = fr.getInvenList();
                break;
            case "invenO": // 발주
                iList = ir.getInvenAddList();
                break;
            case "invenD": // 폐기함
                return null;
        }
        if (fList != null) {
            searchList = fList.stream()
                    .filter(fis ->
                            fis.getF_title().contains(searchKeyword) ||
                                    fis.getF_code().contains(searchKeyword) ||
                                    fis.getF_count().contains(searchKeyword) ||
                                    fis.getF_price().contains(searchKeyword) ||
                                    fis.getC_name().contains(searchKeyword))
                    .toList();
        } else if (iList != null) {
            searchList = iList.stream()
                    .filter(iv ->
                            iv.getIv_company().contains(searchKeyword) ||
                                    iv.getIv_priceSum().contains(searchKeyword) ||
                                    iv.getIv_count().contains(searchKeyword) ||
                                    iv.getIv_current().contains(searchKeyword) ||
                                    iv.getIv_name().contains(searchKeyword) ||
                                    iv.getIv_price().contains(searchKeyword) ||
                                    iv.getIv_total().contains(searchKeyword) ||
                                    iv.getIv_vat().contains(searchKeyword))
                    .toList();
        } else {
            log.info("[재고 검색] 서비스 에러");
            return null;
        }
        int totalListCnt = searchList.size();
        int fromIdx = (pageNum - 1) * pageSize;
        int toIdx = Math.min(fromIdx + pageSize, totalListCnt);

        if (fromIdx >= totalListCnt) {
            return List.of();
        }
        return searchList.subList(fromIdx, toIdx);
    }

    public List<Object[]> getInvenAddList(Integer pageNum, Integer pageSize) {
        List<Object[]> iList = ir.getInvenAddList();
        int totalListCnt = iList.size();
        int fromIdx = (pageNum - 1) * pageSize;
        int toIdx = Math.min(fromIdx + pageSize, totalListCnt);

        if (fromIdx >= totalListCnt) {
            return List.of();
        }
        return iList.subList(fromIdx, toIdx);
    }

    boolean flag2 = true;

    public List<InvenDto> getInvenAddListSort(String id, Integer pageNum, Integer pageSize) {
        String param = "";
        String sort = "";
        if (flag2) {
            sort = "desc";
            flag2 = false;
        } else {
            sort = "asc";
            flag2 = true;
        }
        switch (id) {
            case "pcompany":
                param = "iv_company";
                break;
            case "pcname":
                param = "iv_name";
                break;
            case "pcount":
                param = "iv_count";
                break;
            case "pprice":
                param = "iv_price";
                break;
            case "ppriceSum":
                param = "iv_priceSum";
            case "pvat":
                param = "iv_vat";
                break;
            case "ptotal":
                param = "iv_total";
                break;
            case "pcurrent":
                param = "iv_current";
                break;
            default:
                log.info("[발주 정렬] 서비스 에러");
        }
        List<Object[]> iList = ir.getInvenAddListSort(param, sort);
        int totalListCnt = iList.size();
        int fromIdx = (pageNum - 1) * pageSize;
        int toIdx = Math.min(fromIdx + pageSize, totalListCnt);

        if (fromIdx >= totalListCnt) {
            return List.of();
        }
        return iList.subList(fromIdx, toIdx);

    }

    public List<Object[]> emptyFoodItem() {
        return fr.emptyFoodItem();
    }

    public List<FoodItemDto> getFoodItemList(Integer pageNum, Integer pageSize) {
        List<Object[]> fList = fr.getDeleteFooditemList();
        int totalListCnt = fList.size();
        int fromIdx = (pageNum - 1) * pageSize;
        int toIdx = Math.min(fromIdx + pageSize, totalListCnt);
        return (fromIdx >= totalListCnt) ? List.of() : fList.subList(fromIdx, toIdx);
    }
}
