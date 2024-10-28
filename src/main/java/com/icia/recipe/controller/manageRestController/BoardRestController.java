package com.icia.recipe.controller.manageRestController;

import com.icia.recipe.entity.Category;
import com.icia.recipe.management.dto.BoardDto;
import com.icia.recipe.management.dto.FoodItemDto;
import com.icia.recipe.repository.CategoryRepository;
import com.icia.recipe.repository.RecipeRepository;
import com.icia.recipe.service.manageService.BoardService;
import com.icia.recipe.service.manageService.InvenService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController //@ResponseBody생략 가능
public class BoardRestController {
    @Autowired
    private BoardService bSer;

    @Autowired
    private InvenService iSer;

    @Autowired
    CategoryRepository cr;
    @Autowired
    RecipeRepository rr;

    // 카테고리 관리. select option에 따라 대분류 div에 해당 값 가져오기
    @Secured("ROLE_ADMIN")
    @GetMapping("/bigcategory")
    public List<Category> getCategory(@RequestParam("cg") String cg) {
        if (cg.equals("fooditem")) {
            // 식자재 대분류 카테고리 가져오기
            return cr.getFoodItemBigCg();
        } else if (cg.equals("recipe")) {
            // 레시피 대분류 카테고리 가져오기
            return cr.getRecipeBigCg();
        }

        return null;
    }

    // 카테고리 관리. 대분류 선택시 그 대분류에 포함된 중분류 값 가져오기
    // 중분류 카테고리 매핑
    @Secured("ROLE_ADMIN")
    @GetMapping("/midcategory")
    public List<Category> getMiddleCategory(@RequestParam("cg") String cg) {
        if (String.valueOf(cg.charAt(0)).equals("1")) {
            // 선택한 대분류코드에 속하는 중분류 가져오기 식자재만.
            return cr.getFoodItemMidCg(cg);
        } else if (String.valueOf(cg.charAt(1)).equals("4")) {
            return null;
        }
        log.info("마지막 리턴");
        return null;
    }

    // 소분류 카테고리 매핑
    @Secured("ROLE_ADMIN")
    @GetMapping("/smallcategory")
    public List<Category> getSmallCategory(@RequestParam("cg") String cg) {
        if (String.valueOf(cg.charAt(0)).equals("2")) {
            List<Category> getSmallCg = cr.getFoodItemSmallCg(cg);
            log.info("소분류카테고리 리스트 :{}가지고 복귀", getSmallCg);
            return getSmallCg;
        }
        return null;
    }

    // 게시판 관리. 레시피 또는 물물교환 탭 선택에 따른 테이블 값 변경
    @GetMapping("/boardlist")
    @Secured("ROLE_ADMIN")
    public Object getCategoryBigCg(@RequestParam("tab") String tab,
                                   @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        // 다른데서 쓴것처럼 List<List<?>> 써서 resp 안에 다른 리스트 첨부해서 가져가서 까기
        List<List<?>> combinedList = new ArrayList<>();
        List<FoodItemDto> fList;
        List<FoodItemDto> cList = bSer.getCategory();
        List<FoodItemDto> cList2 = bSer.getCategory2();
        log.info("[홈페이지]");
        log.info(String.valueOf(pageNum));
        if (tab.equals("recipe")) {
            return bSer.getRecipeList();
        } else if (tab.equals("fooditem")) {
            log.info("목록");
            fList = bSer.getFoodItemList(pageNum, pageSize);
            combinedList.add(fList);
            combinedList.add(cList);
            combinedList.add(cList2);
            return combinedList;
        } else {
            return null;
        }
    }

    // 카테고리 통합 추가
    @PostMapping("/insert/category")
    @Secured("ROLE_ADMIN")
    public List<?> insertCategory(@RequestParam("cgName") String cgName, @RequestParam("cgNum") String cgNum) {
        log.info("[추가] 컨트롤러 진입");
        return bSer.insertAllCg(cgName, cgNum);
    }

    // 모달 컨트롤러
    @Secured("ROLE_ADMIN")
    @PostMapping("/fooditem/modalinput")
    public List<FoodItemDto> fooditemmodalinput(MultipartHttpServletRequest request, HttpSession session,
                                                @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) throws IOException {
        log.info("[모달] 컨트롤러 진입");
        if (request != null) {
            boolean result = bSer.insertFoodItem(request, session);
            if (result) {
                return bSer.getFoodItemList(pageNum, pageSize);
            }
        } else {
            log.info("[모달] formData null");
            return null;
        }
        return null;
    }

    // 게시글 상세보기
    @Secured("ROLE_ADMIN")
    @GetMapping("/boardlist/modaldetails")
    public List<?> modalDetailsView(@RequestParam("trClass") String trClass, @RequestParam("trCode") String trCode) {
        log.info("[상세] 진입");
        if (trClass.equals("fooditem")) {
            return bSer.getModalFIDetails(trCode);
        } else if (trClass.equals("recipe")) {
            return null;
        } else {
            log.info("[상세] 클래스 에러");
            return null;
        }
    }

    // 식자재 리스트 검색
    @Secured("ROLE_ADMIN")
    @GetMapping("/boardlist/search")
    public List<?> boardListSearch(@RequestParam("tab") String tab, @RequestParam("pageNum") Integer pageNum,
                                   @RequestParam("pageSize") Integer pageSize, @RequestParam("searchKeyword") String searchKeyword) {
        switch (tab) {
            case "fooditem":
                return bSer.getSearchListFI(pageNum, pageSize, searchKeyword);
            case "recipe":
                return null;
            case "invenM":
                return iSer.getSearchListInven(pageNum, pageSize, searchKeyword, tab);
            case "invenO":
                return iSer.getSearchListInven(pageNum, pageSize, searchKeyword, tab);
            case "invenE":
                return iSer.getSearchListInven(pageNum, pageSize, searchKeyword, tab);
            case "invenD":
                return iSer.getSearchListInven(pageNum, pageSize, searchKeyword, tab);
            default:
                return null;
        }

    }

    // 보드 리스트 각 항목별 정렬
    @GetMapping("/boardlist/sort")
    @Secured("ROLE_ADMIN")
    public Object boardlistsort(@RequestParam("id") String id,
                                @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        if (String.valueOf(id.charAt(0)).equals("f")) {
            log.info("[식자재정렬] 컨트롤러 진입");
            return bSer.getSortedFoodItemList(id, pageNum, pageSize);
        } else if (String.valueOf(id.charAt(0)).equals("r")) {
            log.info("[레시피정렬] 컨트롤러 진입");
            return bSer.getSortedRecipeList(id);
        } else {
            log.info("[정렬] id값 오류");
            return null;
        }
    }

    @PostMapping("/boardlist/modalinfo/update")
    @Secured("ROLE_ADMIN")
    public List<FoodItemDto> modalDetailsInfoUpdate(@RequestParam("Cdata") List Cdata, @RequestParam("Udata") List Udata) {
        log.info("[식자재 수정] 컨트롤러 진입");
        return bSer.modalDetailsInfoUpdate(Cdata, Udata);
    }

    @GetMapping("/modal/update/after/list")
    @Secured("ROLE_ADMIN")
    public List<FoodItemDto> modalDetailsUpdateAfterList(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        return bSer.getFoodItemList(pageNum, pageSize);
    }


}
