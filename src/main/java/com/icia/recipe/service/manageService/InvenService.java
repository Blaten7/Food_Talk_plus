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

    public List<FoodItemDto> getInvenList(Integer pageNum, Integer pageSize) {
        log.info("[재고] 서비스 진입");

        List<Object[]> fListData = fr.getInvenList();
        List<FoodItemDto> fList = new ArrayList<>();

        // Object[] 데이터를 FoodItemDto 객체로 변환
        for (Object[] fiData : fListData) {
            FoodItemDto fi = FoodItemDto.builder()
                    .f_num((int) fiData[0])
                    .c_num((String) fiData[1])
                    .c_numName((String) fiData[2])
                    .c_num2((String) fiData[3])
                    .c_num2Name((String) fiData[4])
                    .c_name((String) fiData[5])
                    .total((String) fiData[6])
                    .f_title(((String) fiData[7]).length() > 5 ? ((String) fiData[7]).substring(0, 5) + "..." : (String) fiData[7])
                    .f_contents((String) fiData[8])
                    .f_price((String) fiData[9])
                    .f_count((String) fiData[10])
                    .f_date((String) fiData[11])
                    .f_edate((String) fiData[13])
                    .f_code((String) fiData[15])
                    .f_origin((String) fiData[16])
                    .f_save((String) fiData[17])
                    .f_img((String) fiData[18])
                    .f_views((String) fiData[19])
                    .f_volume((String) fiData[20])
                    .f_cal((String) fiData[21])
                    .i_path((String) fiData[24])
                    .i_sys_name((String) fiData[25])
                    .i_original_name((String) fiData[26])
                    .build();
            fList.add(fi);
        }

        int totalListCnt = fList.size();
        int fromIdx = (pageNum - 1) * pageSize;
        int toIdx = Math.min(fromIdx + pageSize, totalListCnt);

        if (fromIdx >= totalListCnt) {
            return List.of(); // 페이지 범위가 전체 리스트 크기를 초과하는 경우 빈 리스트 반환
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
            // f_title이 위치한 인덱스를 설정 (예: 인덱스 7이 f_title로 가정)
            int fTitleIndex = 7; // 정확한 위치로 수정 필요
            String fTitle = (String) fi[fTitleIndex];

            // 제목이 6자 이상일 경우 잘라서 저장
            if (fTitle.length() >= 6) {
                fi[fTitleIndex] = fTitle.substring(0, 5) + "...";
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

    public List<Object[]> getSearchListInven(Integer pageNum, Integer pageSize, String searchKeyword, String tab) {
        List<Object[]> resultList = new ArrayList<>();

        switch (tab) {
            case "invenM":
            case "invenE": // 재고량 확인, 유통기한
                List<Object[]> fList = fr.getInvenList(); // Object[]로 결과 받음
                resultList = fList.stream()
                        .filter(fis ->
                                fis[7].toString().contains(searchKeyword) ||  // f_title
                                        fis[15].toString().contains(searchKeyword) || // f_code
                                        fis[10].toString().contains(searchKeyword) || // f_count
                                        fis[9].toString().contains(searchKeyword) ||  // f_price
                                        fis[5].toString().contains(searchKeyword))    // c_name
                        .toList();
                break;

            case "invenO": // 발주
                List<Object[]> iList = ir.getInvenAddList(); // Object[]로 결과 받음
                resultList = iList.stream()
                        .filter(iv ->
                                iv[1].toString().contains(searchKeyword) || // iv_company
                                        iv[3].toString().contains(searchKeyword) || // iv_priceSum
                                        iv[5].toString().contains(searchKeyword) || // iv_count
                                        iv[6].toString().contains(searchKeyword) || // iv_current
                                        iv[0].toString().contains(searchKeyword) || // iv_name
                                        iv[2].toString().contains(searchKeyword) || // iv_price
                                        iv[4].toString().contains(searchKeyword) || // iv_total
                                        iv[7].toString().contains(searchKeyword))   // iv_vat
                        .toList();
                break;

            case "invenD": // 폐기함
                return null;
        }

        // 페이지네이션 처리
        int totalListCnt = resultList.size();
        int fromIdx = (pageNum - 1) * pageSize;
        int toIdx = Math.min(fromIdx + pageSize, totalListCnt);

        if (fromIdx >= totalListCnt) {
            return List.of(); // 페이지 범위가 전체 리스트 크기를 초과하는 경우 빈 리스트 반환
        }

        return resultList.subList(fromIdx, toIdx);
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

    public List<Object[]> getInvenAddListSort(String id, Integer pageNum, Integer pageSize) {
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

    public List<Object[]> getFoodItemList(Integer pageNum, Integer pageSize) {
        List<Object[]> fList = fr.getDeleteFooditemList();
        int totalListCnt = fList.size();
        int fromIdx = (pageNum - 1) * pageSize;
        int toIdx = Math.min(fromIdx + pageSize, totalListCnt);
        return (fromIdx >= totalListCnt) ? List.of() : fList.subList(fromIdx, toIdx);
    }
}
