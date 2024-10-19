package com.icia.recipe.config;

import com.icia.recipe.home.dao.CartDao;
import com.icia.recipe.home.dto.CartDto;
import com.icia.recipe.home.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.security.Principal;

@Component
@Slf4j
public class DataChangeInterceptor implements HandlerInterceptor {
    @Autowired
    MemberService mSer;
    @Autowired
    CartDao cDao;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 사용자 정보 가져오기
        Principal principal = request.getUserPrincipal();

        if (principal != null) {
            String m_id = principal.getName();
            String m_name = mSer.findId(m_id);
            String cartCount = cDao.selectCartCount(m_id);
            // 세션 가져오기
            HttpSession session = request.getSession();
            session.setAttribute("m_name", m_name);
            session.setAttribute("login", m_id);
            session.setAttribute("cartCount", cartCount);
        }

        return true;
    }
}
