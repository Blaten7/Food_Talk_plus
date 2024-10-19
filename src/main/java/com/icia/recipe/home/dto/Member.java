package com.icia.recipe.home.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class Member {
    private String m_id;
    private String m_pw;
    private String m_name;
    private String m_address;
    private String m_phone;
    private String m_block;
    private String role;
    private String authCode;
}
