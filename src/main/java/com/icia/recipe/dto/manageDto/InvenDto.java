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
public class InvenDto {
    private String iv_company;
    private String iv_name;
    private String iv_price;
    private String iv_vat;
    private String iv_count;
    private String iv_total;
    private String iv_current;
    private String iv_priceSum;
    private int iv_num;
}
