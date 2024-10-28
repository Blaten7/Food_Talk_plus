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
public class ImgDto2 {
    // 이미지
    private int i_num; // 오토인크리먼트 파일 번호
    private long i_path; // 경로
    private long i_original_name; // 원래 파일 이름
    private long i_sys_name; // DB시스템상 파일 이름 
    private int f_num; // 식자재 상품코드
    private int r_num; // 레시피 참조번호
    private String m_id;
    private String i_filesize;
    private String i_register_date;

}