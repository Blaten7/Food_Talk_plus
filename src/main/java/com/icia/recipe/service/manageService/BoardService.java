package com.icia.recipe.service.manageService;

import com.icia.recipe.dto.mainDto.FooditemDto;
import com.icia.recipe.dto.manageDto.FoodItemDto;
import com.icia.recipe.entity.Category;
import com.icia.recipe.repository.*;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BoardService {

    @Autowired
    private InvenService iSer;

    @Autowired
    CategoryRepository cr;
    @Autowired
    FoodItemRepository fr;
    @Autowired
    RecipeRepository rr;
    @Autowired
    ImgRepository imgr;
    @Autowired
    InvenAddRepository ir;

    public static final int PAGECOUNT = 2;

    // 식자재 대분류 값 가져오기
    public List<Category> getFoodItemBigCg() {
        return cr.getFoodItemBigCg();
    }

    // 레시피 대분류 값 가져오기
    public List<Category> getRecipeBigCg() {
        return cr.getRecipeBigCg();
    }

    // 식자재 중분류 값 가져오기
    public List<Category> getFoodItemMidCg(String cg) {

        return cr.getFoodItemMidCg(cg);
    }

    // 레시피 중분류 값 가져오기
    public List<Category> getRecipeMidCg(String cgNum) {
        return cr.getRecipeMidCg(cgNum);
    }

    // 식자재 소분류 값 가져오기
    public List<Category> getFoodItemSmCg(String cg) {
        return cr.getFoodItemSmallCg(cg);
    }

    // 레시피 소분류 값 가져오기
    public List<Category> getRecipeSmCg(String cgNum) {
        return cr.getRecipeSmallCg(cgNum);
    }

    public List<?> deleteCategory(String name, String cg) {
        boolean result = cr.deleteCategory(name);
        log.info(name);
        if (result) {
            return getAllCategory(name, cg);
        } else {
            log.info("[삭제] 서비스 에러");
            return null;
        }
    }

    // 모달 식자재 등록
    public boolean insertFoodItem(MultipartHttpServletRequest request, HttpSession session) throws IOException {
        log.info("[모달] 서비스 진입");

        // 파라미터 추출
        String fiCode = request.getParameter("fiCode");
        String fiPrice = request.getParameter("fiPrice");
        String fiBigCg = cr.getBigCg(request.getParameter("fiBigCg"));
        String fiMidCg = cr.getMidCg(request.getParameter("fiMidCg"));
        String fiCounts = request.getParameter("fiCounts");
        String fiExDate = request.getParameter("fiExDate");
        String fiContents = request.getParameter("fiContents");
        String fiTitle = request.getParameter("fiTitle");
        String fiVolume = request.getParameter("fiVolume");
        String fiOrigin = request.getParameter("fiOrigin");
        String fiCal = request.getParameter("fiCal");
        String fiSave = request.getParameter("fiSave");
        String role = "ADMIN";

        // 식자재 정보 DB 저장
        FoodItemDto fDto = new FoodItemDto();
        boolean update = fr.insertFoodItem(fDto);

        // 파일 업로드 및 DB 저장
        List<MultipartFile> files = request.getFiles("fiFiles");
        boolean insertFoodItemImg = saveFiles(files, session, role);

        // 결과 반환
        if (update && insertFoodItemImg) {
            log.info("[모달] 파일&업데이트 성공");
            return true;
        } else {
            log.error("[모달] 서비스 에러");
            return false;
        }
    }

    private boolean saveFiles(List<MultipartFile> files, HttpSession session, String role) {
        String uploadPath = session.getServletContext().getRealPath("/") + "uploadedImg/fooditem/";
        String realPath = "/uploadedImg/fooditem/";
        log.info("[파일] 업로드 경로 : {}", realPath);

        for (MultipartFile mf : files) {
            if (mf.isEmpty()) continue;  // 빈 파일 체크

            String oriFileName = mf.getOriginalFilename();
            log.info("[파일] 기존파일명 : {}", oriFileName);

            String sysFileName = System.currentTimeMillis() + "." + getFileExtension(oriFileName);
            log.info("[파일] 시스템파일명 : {}", sysFileName);

            String filesize = formatFileSize(mf.getSize());
            log.info("[파일] 크기 : {}", filesize);

            // 파일 정보 Map에 저장
            Map<String, String> fiMap = new HashMap<>();
            fiMap.put("i_original_name", oriFileName);
            fiMap.put("i_sys_name", sysFileName);
            fiMap.put("i_path", realPath);
            fiMap.put("m_id", role);
            fiMap.put("i_filesize", filesize);

            // 파일 저장 및 DB 삽입
            try {
                mf.transferTo(new File(uploadPath + sysFileName));
                String maxNum = imgr.getMaxFNum();
                boolean isInserted = imgr.insertFoodItemImg(oriFileName, sysFileName, realPath, role, filesize, maxNum);
                if (!isInserted) return false;  // 하나라도 실패하면 false 반환
            } catch (IOException e) {
                log.error("[파일] 업로드 실패: {}", e.getMessage());
                return false;
            }
        }
        return true;
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private String formatFileSize(long size) {
        if (size >= 1024) {
            double sizeKB = size / 1024.0;
            if (sizeKB >= 1024) {
                double sizeMB = sizeKB / 1024.0;
                return String.format("%.2f MB", sizeMB);
            } else {
                return String.format("%.2f KB", sizeKB);
            }
        } else {
            return size + " B";
        }
    }


    // 식자재 리스트 가져오기. 대분류 중분류에 해당하는 이름으로 바꾸고 ㅇㅇ
    public List<FoodItemDto> getFoodItemList(Integer pageNum, Integer pageSize) {
        List<Object[]> fiList = fr.getFoodItemList2();
        List<FoodItemDto> foodItemList = new ArrayList<>();

        for (Object[] fi : fiList) {
            FoodItemDto foodItem = FoodItemDto.builder()
                    .f_num((int) fi[0])
                    .c_num(cr.getFoodItemListNaming((String) fi[1]))
                    .c_numName((String) fi[2])
                    .c_num2(cr.getFoodItemListNaming2((String) fi[3]))
                    .c_num2Name((String) fi[4])
                    .c_name((String) fi[5])
                    .total((String) fi[6])
                    .f_title(((String) fi[7]).length() > 5 ? ((String) fi[7]).substring(0, 5) + "..." : (String) fi[7])
                    .f_contents((String) fi[8])
                    .f_price((String) fi[9])
                    .f_count((String) fi[10])
                    .f_date((String) fi[11])
                    .f_date2((String) fi[12])
                    .f_edate((String) fi[13])
                    .f_edate2((String) fi[14])
                    .f_code((String) fi[15])
                    .f_origin((String) fi[16])
                    .f_save((String) fi[17])
                    .f_img((String) fi[18])
                    .f_views((String) fi[19])
                    .f_volume((String) fi[20])
                    .f_cal((String) fi[21])
                    .f_total((String) fi[22])
                    .i_num((String) fi[23])
                    .i_path((String) fi[24])
                    .i_sys_name((String) fi[25])
                    .i_original_name((String) fi[26])
                    .m_id((String) fi[27])
                    .i_filesize((String) fi[28])
                    .i_register_date((String) fi[29])
                    .build();

            foodItemList.add(foodItem);
        }

        int totalListCnt = foodItemList.size();
        int fromIdx = (pageNum - 1) * pageSize;
        int toIdx = Math.min(fromIdx + pageSize, totalListCnt);
        if (fromIdx > totalListCnt) {
            return List.of();
        }
        return foodItemList.subList(fromIdx, toIdx);
    }


    public Object getRecipeList() {
        return rr.getRecipeList();
    }

    // 식자재 리스트 각각 정렬 ASC, DESC 토글
    boolean flag = true;

    public List<FoodItemDto> getSortedFoodItemList(String id, Integer pageNum, Integer pageSize) {
        log.info("[식자재정렬] 서비스 진입");

        // 정렬 방향 설정
        String sort = flag ? "desc" : "asc";
        flag = !flag;

        log.info("ORDER BY {}", sort);

        // 정렬 파라미터 설정
        String param;
        switch (id) {
            case "fcode":
                param = "f_code";
                break;
            case "fcnum":
                param = "c_num";
                break;
            case "fcnum2":
                param = "c_num2";
                break;
            case "fprice":
                param = "f_price";
                break;
            case "fcount":
                param = "f_count";
                break;
            case "ftitle":
                param = "f_title";
                break;
            case "fdate":
                param = "f_date";
                break;
            case "fedate":
                param = "f_edate";
                break;
            default:
                param = "f_title"; // 기본값 설정
        }

        List<Object[]> fiList = fr.getSortedFoodItemList(param);
        List<FoodItemDto> foodItemList = new ArrayList<>();

        // Object[] 배열을 FoodItemDto 객체로 변환
        for (Object[] fi : fiList) {
            FoodItemDto foodItem = FoodItemDto.builder()
                    .f_num((int) fi[0])
                    .c_num(cr.getFoodItemListNaming((String) fi[1]))
                    .c_numName((String) fi[2])
                    .c_num2(cr.getFoodItemListNaming2((String) fi[3]))
                    .c_num2Name((String) fi[4])
                    .c_name((String) fi[5])
                    .total((String) fi[6])
                    .f_title(((String) fi[7]).length() > 5 ? ((String) fi[7]).substring(0, 5) + "..." : (String) fi[7])
                    .f_contents((String) fi[8])
                    .f_price((String) fi[9])
                    .f_count((String) fi[10])
                    .f_date((String) fi[11])
                    .f_date2((String) fi[12])
                    .f_edate((String) fi[13])
                    .f_edate2((String) fi[14])
                    .f_code((String) fi[15])
                    .f_origin((String) fi[16])
                    .f_save((String) fi[17])
                    .f_img((String) fi[18])
                    .f_views((String) fi[19])
                    .f_volume((String) fi[20])
                    .f_cal((String) fi[21])
                    .f_total((String) fi[22])
                    .i_num((String) fi[23])
                    .i_path((String) fi[24])
                    .i_sys_name((String) fi[25])
                    .i_original_name((String) fi[26])
                    .m_id((String) fi[27])
                    .i_filesize((String) fi[28])
                    .i_register_date((String) fi[29])
                    .build();

            foodItemList.add(foodItem);
        }

        int totalListCnt = foodItemList.size();
        int fromIdx = (pageNum - 1) * pageSize;
        int toIdx = Math.min(fromIdx + pageSize, totalListCnt);

        if (fromIdx > totalListCnt) {
            return List.of();
        }

        return foodItemList.subList(fromIdx, toIdx);
    }


    public Object getSortedRecipeList(String id) {
        log.info("[정렬] 레시피리스트 미구현");
        return null;
    }

    public List<?> getAllCategory(String name, String cg) {
        switch (String.valueOf(cg.charAt(0))) {
            case "1" -> {
                return cr.getFoodItemBigCg();
            }
            case "2" -> {
                return cr.getFoodItemMidCg(cg);
            }
            case "3" -> {
                return cr.getFoodItemSmallCg(cg);
            }
            case "4" -> {
                return cr.getRecipeBigCg();
            }
            case "5" -> {
                return cr.getRecipeMidCg(cg);
            }
            case "6" -> {
                return cr.getRecipeSmallCg(cg);
            }
            default -> {
                log.info("[삭제] 서비스 에러");
                return null;
            }
        }
    }

    public List<?> insertAllCg(String cgName, String cgNum) {
        log.info("[추가] 서비스 진입");
        log.info("[추가할 이름] : {}", cgName);
        log.info("[참조할 코드] : {}", cgNum);
        boolean result;
        if (cgNum.equals("fooditem")) {
            result = cr.addBigCg(cgName, cgNum);
        } else {
            result = cr.getMidSmCg(cgName, cgNum);
        }
        if (result) {
            switch (String.valueOf(cgNum.charAt(0))) {
                case "1":
                    return cr.getFoodItemMidCg(cgNum);
                case "2":
                    cgNum = "3";
                    break;
                case "4":
                    cgNum = "5";
                    break;
                case "5":
                    cgNum = "6";
                    break;
            }
            if (cgNum.equals("fooditem")) {
                cgNum = "1";
            } else if (cgNum.equals("recipe")) {
                cgNum = "2";
            }
            log.info(cgNum);
            return getAllCategory(cgName, cgNum);
        } else {
            return null;
        }

    }

    public List<?> deleteFoodItemList(ArrayList deleteKey, Integer pageNum, Integer pageSize) {
        log.info("[게시글 삭제] 서비스 진입");
        boolean result = ir.deleteFoodItemList(deleteKey);
        if (result) {
            return ir.getInvenAddList();
        } else {
            log.info("[게시글 삭제] 에러 발생");
            return null;
        }
    }

    public List<Object[]> getModalFIDetails(String trCode) {
        List<Object[]> details = fr.getModalFIDetails(trCode);

        for (Object[] fi : details) {
            // Object[] 배열에서 각 필드를 인덱스로 참조하여 필요한 필드를 설정합니다
            fi[18] = imgr.getFiImg(trCode); // f_img가 위치하는 인덱스
            fi[2] = cr.getFoodItemListNaming((String) fi[1]); // c_numName 설정 (c_num을 기반으로)
            fi[4] = cr.getFoodItemListNaming2((String) fi[3]); // c_num2Name 설정 (c_num2를 기반으로)
        }

        return details;
    }


    public List<FoodItemDto> getSearchListFI(Integer pageNum, Integer pageSize, String searchKeyword) {
        log.info("[식자재 검색] 진입");
        log.info("검색어 : {}", searchKeyword);

        List<Object[]> fsearchList = fr.getFooditemList();
        List<FoodItemDto> foodItemList = new ArrayList<>();

        // Object[] 배열을 FoodItemDto로 변환하면서 필요한 필드 설정
        for (Object[] fis : fsearchList) {
            FoodItemDto foodItem = FoodItemDto.builder()
                    .f_num((int) fis[0])
                    .c_num(cr.getFoodItemListNaming((String) fis[1]))
                    .c_numName((String) fis[2])
                    .c_num2(cr.getFoodItemListNaming2((String) fis[3]))
                    .c_num2Name((String) fis[4])
                    .c_name((String) fis[5])
                    .total((String) fis[6])
                    .f_title((String) fis[7])
                    .f_contents((String) fis[8])
                    .f_price((String) fis[9])
                    .f_count((String) fis[10])
                    .f_date((String) fis[11])
                    .f_edate((String) fis[13])
                    .f_code((String) fis[15])
                    .f_origin((String) fis[16])
                    .f_save((String) fis[17])
                    .f_img((String) fis[18])
                    .f_views((String) fis[19])
                    .f_volume((String) fis[20])
                    .f_cal((String) fis[21])
                    .i_path((String) fis[24])
                    .i_sys_name((String) fis[25])
                    .i_original_name((String) fis[26])
                    .build();

            foodItemList.add(foodItem);
        }

        // 검색어로 필터링
        List<FoodItemDto> filteredList = foodItemList.stream()
                .filter(fi ->
                        fi.getF_title().contains(searchKeyword) ||
                                fi.getC_num().contains(searchKeyword) ||
                                fi.getC_num2().contains(searchKeyword) ||
                                fi.getF_code().contains(searchKeyword) ||
                                fi.getF_price().contains(searchKeyword) ||
                                fi.getF_count().contains(searchKeyword) ||
                                fi.getF_date().contains(searchKeyword) ||
                                fi.getF_edate().contains(searchKeyword) ||
                                fi.getF_contents().contains(searchKeyword))
                .collect(Collectors.toList());

        // 페이징 처리
        int totalListCnt = filteredList.size();
        int fromIdx = (pageNum - 1) * pageSize;
        int toIdx = Math.min(fromIdx + pageSize, totalListCnt);

        if (fromIdx >= totalListCnt) {
            return List.of(); // 페이지 범위가 전체 리스트 크기를 초과하는 경우 빈 리스트 반환
        }

        return filteredList.subList(fromIdx, toIdx);
    }



    public List<Object[]> modalDetailsInfoUpdate(List<String> Cdata, List<String> Udata) {
        String c_cnum = Cdata.get(0);
        String c_cnum2 = Cdata.get(1);
        String c_ftitle = Cdata.get(2);
        String fnum = Cdata.get(3);

        String u_code = Udata.get(0);
        String u_cnum = Udata.get(1);
        String u_cnum2 = Udata.get(2);
        String u_price = Udata.get(3);
        String u_count = Udata.get(4);
        String u_origin = Udata.get(5);
        String u_save = Udata.get(6);
        String u_cal = Udata.get(7);

        // 파라미터를 Map에 넣어 MyBatis로 전달
        Map<String, Object> params = new HashMap<>();
        params.put("c_cnum", c_cnum);
        params.put("c_cnum2", c_cnum2);
        params.put("c_ftitle", c_ftitle);
        params.put("u_code", u_code);
        params.put("u_cnum", u_cnum);
        params.put("u_cnum2", u_cnum2);
        params.put("u_price", u_price);
        params.put("u_count", u_count);
        params.put("u_origin", u_origin);
        params.put("u_save", u_save);
        params.put("u_cal", u_cal);

        log.info("[식자재 수정] 파라미터 맵 : {}", params);

        boolean result = fr.updateAndGetModalDetailsInfo(u_code, u_cnum, u_cnum2, u_price, u_count, u_origin, u_save, u_cal, c_cnum, c_cnum2, c_ftitle);

        if (result) {
            return fr.getModalDetailsInfoUpdateBeforeList(fnum);
        } else {
            log.info("[상세모달] 업데이트 실패!");
            return null;
        }
    }


    public List<FoodItemDto> getCategory() {
        return cr.getCategory();
    }

    public List<FoodItemDto> getCategory2() {
        return cr.getCategory2();
    }

    public List<Object[]> permanentDelte(ArrayList deleteKey) {
        boolean result = fr.permanentDeleteFoodItem(deleteKey);
        if (result) {
            return iSer.getFoodItemList(1, 10);
        } else {
            log.info("[영구삭제] 서비스 에러");
            return null;
        }
    }
}
