package com.icia.recipe.home.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
@Accessors(chain=true)
public class InputListDto {
    private String BUYER_NAME;
    private String BUYER_EMAIL_1;
    private String BUYER_EMAIL_2;
    private String BUYER_PHONE_MOBILE_2;
    private String BUYER_PHONE_MOBILE_3;
    private String name;
    private String post;
    private String addr1;
    private String addr2;
    private String phone1;
    private String phone2;
    private Map<String,Object> data;

}
