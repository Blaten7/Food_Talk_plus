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
public class TradeItemDto {
    private int t_num;
    private String t_title;
    private String t_item;
    private int t_itemcount;
    private String t_unit;
    private String t_change;
}
