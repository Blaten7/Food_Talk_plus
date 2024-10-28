package com.icia.recipe.dto.mainDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Repository;

import java.util.List;
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
@Accessors(chain=true)
@Repository
public class CartDto {
    private String ca_num;
    private int ca_count;
    private String ca_date;

    private String c_name;

    private List<FooditemDto> fList;
    private List<ImgDto> iList;
}
