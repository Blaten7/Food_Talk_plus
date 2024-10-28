package com.icia.recipe.dto.manageDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    // 회원
    private String m_id;
    private String m_pw;
    private String m_name;
    private String m_address;
    private String m_phone;
    private String m_block;
    private String role;
}
