package com.icia.recipe.dto.mainDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
@Accessors(chain=true)
public class ImgDto {
    private String i_num;
    private String i_name;
    private String i_path;
    private String i_original_name;
    private String i_sys_name;
}
