package com.icia.recipe.security;

import com.icia.recipe.dto.mainDto.Member;
import com.icia.recipe.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
@Slf4j
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    MemberRepository mr;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        Object[] result = mr.getMemberInfo(username);

        // 각 행이 Object 배열일 경우
        for (Object row : result) {
            System.out.println(Arrays.toString((Object[]) row));
        }
        if(result.length < 1){
            //로그인 실패시 예외를 로그인 실패 핸들러에 던짐
            throw new UsernameNotFoundException(username+" 사용자를 찾을 수 없습니다.");
        }
        Object[] row = (Object[]) result[0]; // 첫 번째 행 추출
        String m_id = (String) row[0];
        String m_pw = (String) row[1];
        String m_name = (String) row[2];
        String role = (String) row[3];
        System.out.println(m_id+" "+m_pw+" "+m_name+" "+role);
        return User.builder()
                .username(m_id)
                .password(m_pw)
                .roles(role)
                .build();
        //User클래스: UserDetails의 구현체
        //필수:아이디,비밀번호, 권한, 선택: disabled(t/f(로그인안됨)), accountLocked(t/f(로그인안됨)),accountExpired(t/f)
    }
}
