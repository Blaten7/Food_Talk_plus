package com.icia.recipe.controller.mainController;

import com.icia.recipe.dto.mainDto.Member;
import com.icia.recipe.dto.mainDto.NoticeDto;
import com.icia.recipe.dto.mainDto.SearchDto;
import com.icia.recipe.dto.manageDto.MemberDto;
import com.icia.recipe.jwt.JwtUtil;
import com.icia.recipe.service.mainService.MemberService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class MemberController {

    @Autowired
    MemberService mSer;

    @Autowired
    JwtUtil jwtUtil;

    @PreAuthorize("isAnonymous()")
    @GetMapping("/joinfrm")
    public String join() {
        return "main/member/memberjoin";
    }

    @GetMapping("/agreefrm")
    public String agree() {
        return "main/member/caulseAgree";
    }

    @GetMapping("/member/join")
    public String join(Model model) {
        return "main/member/join";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute Member member) {
        System.out.println(member);
        boolean result = mSer.join(member);
        System.out.println(result);
        if (result) {
            log.info("회원가입 성공");
            return "main/member/login";
        } else {
            log.info("회원가입 실패");
            return "main/member/join";
        }
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/loginfrm")
    public String loginfrm() {
        return "main/member/login";
    }

    @GetMapping("searchfrm")
    public String searchfrm() {
        return "main/member/searchidpw";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/member/login")
    public String login() {
        return "main/member/login";
    }

    @GetMapping("/member/login/error")
    public String loginError(Model model) {
        model.addAttribute("msg", "로그인 실패");
        return "main/member/login";
    }

    @PostMapping("/changepw")
    public String changepw(@ModelAttribute MemberDto member, HttpSession session) {
        session.removeAttribute("authCode");
        boolean result = mSer.changepw(member);
        if (result) {
            log.info("비밀번호 변경 성공");
            return "redirect:/login";
        } else {
            log.info("비밀번호 변경 실패");
            return "main/member/searchidpw";
        }
    }
    @GetMapping("/customer/center")
    public String customerCenter(Model model) {
        List<NoticeDto> nList = mSer.getNoticeList();
        model.addAttribute("nList", nList);
        return "main/customerservice/notice";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/customer/question")
    public String question() {
        return "main/customerservice/question";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/customer/sound")
    public String customerSound() {
        return "main/customerservice/customerSound";
    }

    @GetMapping("/customer/problem")
    public String problem() {
        return "main/customerservice/problem";
    }

    @GetMapping("/cancle")
    public String cancle(){
        return "index";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/member/mypage")
    public String mypage(Principal principal, Model model, SearchDto sDto) {
        String id = principal.getName();
        log.info("pageNum:{}", sDto.getPageNum());
        log.info("sDto:{}",sDto);
        mSer.selectOrder(id, model, sDto);
        String pageHtml = mSer.getPaging(id,sDto);
        model.addAttribute("pageHtml", pageHtml);
        return "main/member/mypage";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/customer/center/noticeWrite")
    public String noticeWrite(Principal principal, Model model) {
        String id = principal.getName();
        model.addAttribute("id", id);
        return "main/customerservice/noticeWrite";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/customer/center/noticeWrite/done")
    public String noticeWriteDone(HttpServletRequest request, Model model) {
        String title = request.getParameter("noticeTitle");
        String contents = request.getParameter("noticeContents");
        String id = request.getParameter("currentID");
        boolean result = mSer.insertNotice(title, contents, id);
        if (result) {
            List<NoticeDto> nList = mSer.getNoticeList();
            model.addAttribute("nList", nList);
            return "main/customerservice/notice";
        } else {
            log.info("[공지 입력] 에러");
            return null;
        }
    }
    @GetMapping("/api/user-info")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
        // 쿠키에서 Authorization 값 추출
        String token = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("Authorization")) {
                    token = cookie.getValue();
                }
            }
        }
        // 토큰 확인
        if (token == null || !token.startsWith("Bearer")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid token");
        }

        String jwt = token.replace("Bearer%20", "");

        if (!jwtUtil.validateToken(jwt)) { // 토큰 유효성 검사
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
        }

        Claims claims = jwtUtil.getUserInfoFromToken(jwt); // 토큰에서 정보 추출
        String role = (String) claims.get(JwtUtil.AUTHORIZATION_KEY);

        return ResponseEntity.ok(Map.of("role", role)); // role 반환
    }




    @GetMapping("/delivery/info")
    public String delivery() {
        return "main/member/mypage";
    }
}
