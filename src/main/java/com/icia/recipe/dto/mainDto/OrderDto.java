package com.icia.recipe.dto.mainDto;

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
    private String o_total; //0
    private String o_count; //1
    private String o_num; // 2
    List<ImgDto> iList; //3
    List<FooditemDto> fList; // 4
    List<ItemListDto> oList; // 5
    private Map<String,Object> data; // 6
    private String o_delivery; // 7

}
