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
public class BoardDto {
    // 레시피
    private int r_num;
    private String m_id;
    private String r_name; // 레시피 제목
    private String r_contents;
    private String r_date;
    private String r_count;
    private String r_delete;

    // 카테고리
    private String c_num;
    private String c_num2;
    private String c_name;

}
