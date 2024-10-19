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
public class InputOrderDto {
    private String f_num;
    private String name;
    private String price;
    List<ItemListDto> itemList;
    List<InputListDto> inputList;
    private Map<String,Object> data;
}
