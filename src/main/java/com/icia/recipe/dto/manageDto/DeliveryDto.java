package com.icia.recipe.dto.manageDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDto {
    private int o_num;
    private String m_id;
    private int o_total;
    private String o_date;
    private String o_name;
    private String o_address;
    private String o_phone;
    private int o_count;
    private int o_delivery;
    private int o_post;

}
