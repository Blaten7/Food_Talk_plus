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
public class CtgDto {
    private String c_num;
    private String c_name;
    private String c_ai;
    List<CtgDto> list = new ArrayList<>();
}
