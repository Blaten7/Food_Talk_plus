package com.icia.recipe.service.manageService;

import com.icia.recipe.management.dao.BoardDao;
import com.icia.recipe.management.dao.InvenDao;
import com.icia.recipe.management.dto.BoardDto;
import com.icia.recipe.management.dto.FoodItemDto;
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
    private BoardDao bDao;

    @Autowired
    private InvenDao iDao;

    @Autowired
    private InvenService iSer;

    public static final int PAGECOUNT = 2;

    // 식자재 대분류 값 가져오기
    public List<BoardDto> getFoodItemBigCg() {
        return bDao.getFoodItemBigCg();
    }

    // 레시피 대분류 값 가져오기
    public List<BoardDto> getRecipeBigCg() {
        return bDao.getRecipeBigCg();
    }

    // 식자재 중분류 값 가져오기
    public List<BoardDto> getFoodItemMidCg(String cg) {

        return bDao.getFoodItemMidCg(cg);
    }

    // 레시피 중분류 값 가져오기
    public List<BoardDto> getRecipeMidCg(String cgNum) {
        return bDao.getRecipeMidCg(cgNum);
    }

    // 식자재 소분류 값 가져오기
    public List<BoardDto> getFoodItemSmCg(String cg) {
        return bDao.getFoodItemSmCg(cg);
    }

    // 레시피 소분류 값 가져오기
    public List<BoardDto> getRecipeSmCg(String cgNum) {
        return bDao.getRecipeSmCg(cgNum);
    }

    public List<?> deleteCategory(String name, String cg) {
        boolean result = bDao.deleteCategory(name, cg);
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
        String fiBigCg = bDao.getBigCgNum(request.getParameter("fiBigCg"));
        String fiMidCg = bDao.getMidCgNum(request.getParameter("fiMidCg"));
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
        boolean update = bDao.insertFoodItem(fiCode, fiExDate, fiCounts, fiBigCg, fiMidCg, fiPrice, fiContents, fiTitle, fiVolume, fiOrigin, fiCal, fiSave);

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
                boolean isInserted = bDao.insertFoodItemImg(fiMap);
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
        List<FoodItemDto> fiList = bDao.getFoodItemList2();
        for (FoodItemDto fi : fiList) {
            fi.setC_num(bDao.getFoodItemListNaming(fi.getC_num()));
            fi.setC_num2(bDao.getFoodItemListNaming2(fi.getC_num2()));
            if (fi.getC_num().length() > 6) {
                fi.setC_num(fi.getC_num().substring(0, 6) + "...");
            }
            if (fi.getF_title().length() > 5) {
                fi.setF_title(fi.getF_title().substring(0, 5) + "...");
            }
        }
        int totalListCnt = fiList.size();
        int fromIdx = (pageNum - 1) * pageSize;
        int toIdx = Math.min(fromIdx + pageSize, totalListCnt);
        if (fromIdx > totalListCnt) {
            return List.of();
        }
        return fiList.subList(fromIdx, toIdx);
    }

    public Object getRecipeList() {
        return bDao.getRecipeList();
    }

    // 식자재 리스트 각각 정렬 ASC, DESC 토글
    boolean flag = true;

    public List<FoodItemDto> getSortedFoodItemList(String id, Integer pageNum, Integer pageSize) {
        log.info("[식자재정렬] 서비스 진입");
        String sort = "";
        if (flag) {
            sort = "desc";
            flag = false;
        } else {
            sort = "asc";
            flag = true;
        }
        log.info("ORDER BY {}", sort);
        String param = "";
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

        }
        List<FoodItemDto> fiList = bDao.getSortedFoodItemList(param, sort);
        for (FoodItemDto fi : fiList) {
            fi.setC_num(bDao.getFoodItemListNaming(fi.getC_num()));
            fi.setC_num2(bDao.getFoodItemListNaming2(fi.getC_num2()));
            if (fi.getF_title().length() >=5) {
                fi.setF_title(fi.getF_title().substring(0, 5) + "...");
            }
        }
        int totalListCnt = fiList.size();
        Integer fromIdx = (pageNum - 1) * pageSize;
        Integer toIdx = Math.min(fromIdx + pageSize, totalListCnt);
        if (fromIdx > totalListCnt) {
            return List.of();
        }
        return fiList.subList(fromIdx, toIdx);
    }

    public Object getSortedRecipeList(String id) {
        log.info("[정렬] 레시피리스트 미구현");
        return null;
    }

    public List<?> getAllCategory(String name, String cg) {
        switch (String.valueOf(cg.charAt(0))) {
            case "1" -> {
                return bDao.getFoodItemBigCg();
            }
            case "2" -> {
                return bDao.getFoodItemMidCg(cg);
            }
            case "3" -> {
                return bDao.getFoodItemSmCg(cg);
            }
            case "4" -> {
                return bDao.getRecipeBigCg();
            }
            case "5" -> {
                return bDao.getRecipeMidCg(cg);
            }
            case "6" -> {
                return bDao.getRecipeSmCg(cg);
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
            result = bDao.addBigCg(cgName, cgNum);
        } else {
            result = bDao.getMidSmCg(cgName, cgNum);
        }
        if (result) {
            switch (String.valueOf(cgNum.charAt(0))) {
                case "1":
                    return bDao.getFoodItemMidCg(cgNum);
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
        boolean result = bDao.deleteFoodItemList(deleteKey);
        if (result) {
            return iDao.getInvenAddList();
        } else {
            log.info("[게시글 삭제] 에러 발생");
            return null;
        }
    }

    public List<FoodItemDto> getModalFIDetails(String trCode) {
        List<FoodItemDto> details = bDao.getModalFIDetails(trCode);
        for (FoodItemDto fi : details) {
            fi.setF_img(bDao.getFiImg(trCode));
            fi.setC_numName(bDao.getFoodItemListNaming(fi.getC_num()));
            fi.setC_num2Name(bDao.getFoodItemListNaming2(fi.getC_num2()));
        }
        return details;
    }

    public List<FoodItemDto> getSearchListFI(Integer pageNum, Integer pageSize, String searchKeyword) {
        log.info("[식자재 검색] 진입");
        log.info("검색어 : {}", searchKeyword);

        List<FoodItemDto> fsearchList = bDao.getFoodItemList();
        for (FoodItemDto fis : fsearchList) {
            fis.setC_num(bDao.getFoodItemListNaming(fis.getC_num()));
            fis.setC_num2(bDao.getFoodItemListNaming2(fis.getC_num2()));
        }

        List<FoodItemDto> filteredList = fsearchList.stream()
                .filter(fis ->
                        fis.getF_title().contains(searchKeyword) ||
                                fis.getC_num().contains(searchKeyword) ||
                                fis.getC_num2().contains(searchKeyword) ||
                                fis.getF_code().contains(searchKeyword) ||
                                fis.getF_price().contains(searchKeyword) ||
                                fis.getF_count().contains(searchKeyword) ||
                                fis.getF_date().contains(searchKeyword) ||
                                fis.getF_edate().contains(searchKeyword) ||
                                fis.getF_contents().contains(searchKeyword))
                .collect(Collectors.toList());

        int totalListCnt = filteredList.size();
        int fromIdx = (pageNum - 1) * pageSize;
        int toIdx = Math.min(fromIdx + pageSize, totalListCnt);

        if (fromIdx >= totalListCnt) {
            return List.of(); // 페이지 범위가 전체 리스트 크기를 초과하는 경우 빈 리스트 반환
        }

        return filteredList.subList(fromIdx, toIdx);
    }


    public List<FoodItemDto> modalDetailsInfoUpdate(List<String> Cdata, List<String> Udata) {
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

        boolean result = bDao.updateAndGetModalDetailsInfo(params);

        if (result) {
            return bDao.getModalDetailsInfoUpdateBeforeList(fnum);
        } else {
            log.info("[상세모달] 업데이트 실패!");
            return null;
        }
    }


    public List<FoodItemDto> getCategory() {
        List <FoodItemDto> cList = bDao.getCategory();
        return cList;
    }

    public List<FoodItemDto> getCategory2() {
        List <FoodItemDto> cList = bDao.getCategory2();
        return cList;
    }

    public List<FoodItemDto> permanentDelte(ArrayList deleteKey) {
        boolean result = bDao.permanentDeleteFoodItem(deleteKey);
        if (result) {
            return iSer.getFoodItemList(1, 10);
        } else {
            log.info("[영구삭제] 서비스 에러");
            return null;
        }
    }
}
