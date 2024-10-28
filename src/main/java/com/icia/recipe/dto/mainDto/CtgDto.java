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
@Builder
@Accessors(chain=true)
public class CtgDto {
    private String c_num;
    private String c_name;
    List<CtgDto> list = new ArrayList<>();
    private String c_ai;
}
