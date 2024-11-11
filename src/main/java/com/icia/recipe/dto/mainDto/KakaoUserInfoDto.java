package com.icia.recipe.dto.mainDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoUserInfoDto {
    private String nickname;

    public KakaoUserInfoDto(String nickname) {
        this.nickname = nickname;
    }
}