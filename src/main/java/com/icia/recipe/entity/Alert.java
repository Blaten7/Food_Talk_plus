package com.icia.recipe.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@Table(name = "alert")
public class Alert implements Serializable {

    @Id
    @Column(name = "t_num", unique = true, columnDefinition = "INT UNSIGNED")
    private Long t_num;

    @Column(name = "m_id", length = 50, insertable = false, updatable = false)
    private String alert_id;

    @Column(name = "tradesend", length = 50)
    private String tradesend;

    @Column(name = "t_item", length = 20)
    private String t_item;

    @Column(name = "t_itemcount")
    private int t_itemcount;

    @Column(name = "t_unit", length = 20)
    private String t_unit;

    @Column(name = "t_change", length = 20)
    private String t_change;

    @Column(name = "t_alertnum", unique = true, columnDefinition = "TINYINT DEFAULT '1'")
    private Long t_alertnum;

    @Column(name = "alertcol", length = 45)
    private String alertcol;

}
