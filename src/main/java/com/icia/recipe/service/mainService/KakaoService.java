package com.icia.recipe.service.mainService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icia.recipe.dto.mainDto.KakaoUserInfoDto;
import com.icia.recipe.entity.Member;
import com.icia.recipe.entity.UserRoleEnum;
import com.icia.recipe.jwt.JwtUtil;
import com.icia.recipe.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Slf4j(topic = "KAKAO Login")
@Service
@RequiredArgsConstructor
public class KakaoService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository mr;
    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;
    UserRoleEnum userRoleEnum;
    Member mb;

    public String kakaoLogin(String code) throws JsonProcessingException {
        System.out.println("카카오 인가코드 : "+code);
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getToken(code);

        // 2. 토큰으로 카카오 API 호출 : "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
        KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);
        if (kakaoUserInfo != null) {
            System.out.println("카카오 서비스 유저 정보 :"+ kakaoUserInfo);
            Object kakaoMember = registerKakaoUserIfNeeded(kakaoUserInfo);
            System.out.println("카카오 멤버 : "+kakaoMember);
            return null;
//            return jwtUtil.createToken(kakaoMember[0], UserRoleEnum.valueOf(kakaoMember.getMember_role()));
        } else {
            System.out.println("카카오 서비스 유저 정보 Null");
            return null;
        }

    }

    private String getToken(String code) throws JsonProcessingException {
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("https://kauth.kakao.com")
                .path("/oauth/token")
                .encode()
                .build()
                .toUri();

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "036062a59ad86612a6fc748ed359a84c");
        body.add("redirect_uri", "http://localhost:8081/api/user/kakao/callback");
        body.add("code", code);

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(body);

        // HTTP 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
                requestEntity,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        String accessToken = jsonNode.get("access_token").asText();

        // 액세스 토큰 출력
        System.out.println("발급받은 액세스 토큰: " + accessToken);

        // 7. 액세스 토큰 유효성 확인
        validateAccessToken(accessToken);

        // 8. 유효한 액세스 토큰 반환
        return accessToken;
    }

    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        System.out.println("카카오 access토큰 : "+accessToken);
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("https://kapi.kakao.com")
                .path("/v2/user/me")
                .encode()
                .build()
                .toUri();

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(new LinkedMultiValueMap<>());

        // HTTP 요청 보내기
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    requestEntity,
                    String.class
            );

            // 정상 응답 처리
            JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
            Long id = jsonNode.get("id").asLong();
            String nickname = jsonNode.get("properties")
                    .get("nickname").asText();
            String email = jsonNode.get("kakao_account")
                    .get("email").asText();
            System.out.println("아이디 : "+id+", 닉네임 : "+ nickname + ", 이메일 : "+email);
            return new KakaoUserInfoDto(id, nickname, email);

        } catch (HttpClientErrorException e) {
            // 에러 응답 처리
            System.out.println("HTTP 요청 실패 : "+ e.getMessage());

            // 에러 본문(JSON) 파싱
            String errorBody = e.getResponseBodyAsString();
            JsonNode errorJson = new ObjectMapper().readTree(errorBody);
            String errorMsg = errorJson.get("msg").asText();
            System.out.println("에러 메시지 : "+ errorMsg);
            return null;
        }
    }

    private void validateAccessToken(String accessToken) throws JsonProcessingException {
        // 유효성 검증 URL 구성
        URI uri = UriComponentsBuilder
                .fromUriString("https://kapi.kakao.com")
                .path("/v1/user/access_token_info")
                .encode()
                .build()
                .toUri();

        // 요청 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        // 요청 엔티티 생성
        RequestEntity<Void> requestEntity = RequestEntity
                .get(uri)
                .headers(headers)
                .build();

        try {
            // 유효성 검증 요청
            ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
            System.out.println("액세스 토큰 유효성 확인 성공: " + response.getBody());
        } catch (HttpClientErrorException e) {
            // 유효성 검증 실패 처리
            System.out.println("액세스 토큰 유효성 확인 실패: " + e.getResponseBodyAsString());
            throw new RuntimeException("유효하지 않은 액세스 토큰: " + e.getResponseBodyAsString());
        }
    }

    private Object registerKakaoUserIfNeeded(KakaoUserInfoDto kakaoUserInfo) {
        // DB 에 중복된 Kakao Id 가 있는지 확인
        Long kakaoId = kakaoUserInfo.getId();
        Object kakaoUser = mr.findByKakaoId(kakaoId).orElse(null);
        if (kakaoUser == null) {
            System.out.println("신규가입임 ㅇㅇ");
            // 카카오 사용자 email 동일한 email 가진 회원이 있는지 확인
            String kakaoEmail = kakaoUserInfo.getEmail();
            Object sameEmailUser = mr.findByEmail(kakaoEmail).orElse(null);
            if (sameEmailUser != null) {
                // 기존 회원정보에 카카오 Id 추가
                kakaoUser = mb.kakaoIdUpdate(kakaoId);
            } else {
                // 신규 회원가입
                // password: random UUID
                String password = UUID.randomUUID().toString();
                String encodedPassword = passwordEncoder.encode(password);

                // email: kakao email
                String email = kakaoUserInfo.getEmail();

                kakaoUser = new Member(kakaoUserInfo.getNickname(), encodedPassword, email, UserRoleEnum.ROLE_USER, kakaoId);
            }

            mr.kakaoUserAdd(kakaoUser);
        }
        return kakaoUser;
    }


}