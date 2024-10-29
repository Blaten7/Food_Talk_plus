package com.icia.recipe.dto.mainDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Accessors(chain=true)
public class FooditemDto {
    // 상품 테이블
    private int f_num;
    private String f_title;
    private String f_contents;
    private String  f_price;
    private String f_count;
    private String f_date;
    private String f_edate;
    private int f_views;
    private String f_volume;
    private String f_cal;
    private String f_save;
    private String e_date;
    private String f_code;
    private String f_origin;
    // 카테고리 테이블
    List<CtgDto> cList = new ArrayList<>();
    // 이미지 테이블
    List<ImgDto> iList = new ArrayList<>();

    public FooditemDto(int f_num, String f_title, String f_contents, String f_price, String f_count, String f_date, String f_edate, int f_views, String f_volume, String f_cal, String f_save, String e_date, String f_code, String f_origin) {
        this.f_num = f_num;
        this.f_title = f_title;
        this.f_contents = f_contents;
        this.f_price = f_price;
        this.f_count = f_count;
        this.f_date = f_date;
        this.f_edate = f_edate;
        this.f_views = f_views;
        this.f_volume = f_volume;
        this.f_cal = f_cal;
        this.f_save = f_save;
        this.e_date = e_date;
        this.f_code = f_code;
        this.f_origin = f_origin;
    }
}
