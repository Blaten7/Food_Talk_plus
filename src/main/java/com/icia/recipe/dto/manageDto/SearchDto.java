package com.icia.recipe.dto.manageDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchDto {
    private String keyword;
    private Integer pageNum;
    private Integer listCnt;
    private Integer startIdx;
}
