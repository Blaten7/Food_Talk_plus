package com.icia.recipe.management.dto;

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
public class FoodItemDto {
    private int f_num;// 글번호 오토 인크리먼트
    private String c_num;// 카테고리 대분류 참조번호
    private String c_numName;// 카테고리 대분류 참조번호
    private String c_num2; // 카테고리 중분류 참조번호
    private String c_num2Name; // 카테고리 중분류 참조번호
    private String c_name;
    private String total;
    private String f_title; // 식자재 제목
    private String f_contents; // 식자재 내용
    private String f_price; // 식자재 가격
    private String f_count; // 재고량
    private String f_date; // 식자재 등록일
    private String f_date2;
    private String f_edate; // 식자재 유통기한
    private String f_edate2;
    private String f_code; // 상품코드
    private String f_origin; // 상품 원산지
    private String f_save; // 상품 보관 방법
    private String f_img; // 식자재 이미지 불러올때 임시로 여기에 저장함
    private String f_views;
    private String f_volume;
    private String f_cal;
    private String f_total;
    private String i_num;
    private String i_path;
    private String i_sys_name;
    private String i_original_name;
    private String m_id;
    private String i_filesize;
    private String i_register_date;

}
