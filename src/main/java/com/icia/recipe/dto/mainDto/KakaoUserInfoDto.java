package com.icia.recipe.dto.mainDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoUserInfoDto {
    private String nickname;
    private String email;
    private Long id;

    public KakaoUserInfoDto(Long id, String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
        this.id = id;
    }
}