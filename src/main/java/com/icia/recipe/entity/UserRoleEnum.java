package com.icia.recipe.entity;

import lombok.Getter;

@Getter
public enum UserRoleEnum {

    ROLE_USER("ROLE_USER"),  // 사용자 권한
    ROLE_ADMIN("ROLE_ADMIN");  // 관리자 권한

    private final String authority;

    UserRoleEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }
}
