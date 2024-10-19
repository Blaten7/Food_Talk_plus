package com.icia.recipe.home.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
@Accessors(chain=true)
public class OrderDto {
    private String o_total;
    private String o_count;
    private String o_num;
    List<ImgDto> iList;
    List<FooditemDto> fList;
    List<ItemListDto> oList;
    private Map<String,Object> data;
    private String o_delivery;

}
