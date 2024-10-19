package com.icia.recipe.home.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class TradeDto {

    private int t_num;
    private String m_id;
    private String m_name;
    private String t_count;
    private String t_date;
    private int t_views;
    private String t_title;
    private String t_item;
    private int t_itemcount;
    private String t_unit;
    private String t_change;
    private String visible;
    private int t_order;
    private String tradesend;
    private int t_alertnum;
}
