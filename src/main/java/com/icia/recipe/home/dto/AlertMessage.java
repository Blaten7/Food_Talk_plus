package com.icia.recipe.home.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlertMessage {
    public enum MessageType{
        SEND,accept,refuse,EMPTY
    }
    private MessageType type;
    private String tradesend;
    private String m_id;
    private int t_num;
    private String t_title;
    private String t_item;
    private int t_itemcount;
    private String t_unit;
    private String message;
    private String f_title;
    private String f_count;
    private String t_change;
}
