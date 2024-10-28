package com.icia.recipe.dto.mainDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchDto {
	private String colName;
	private String keyword;
	private Integer pageNum;
	private Integer listCnt; //10, 패이지당 글의 갯수
	private Integer startIdx;// 1page: 0~ 2page:10~
	private Map<String, String> data;
}
