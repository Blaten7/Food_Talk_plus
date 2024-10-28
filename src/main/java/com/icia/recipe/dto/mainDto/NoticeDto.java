package com.icia.recipe.dto.mainDto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeDto {
    private String m_id;
    private String title;
    private String contents;
}
