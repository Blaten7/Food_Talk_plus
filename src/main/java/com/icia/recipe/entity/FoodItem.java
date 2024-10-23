package com.icia.recipe.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Entity
@Table(name = "fooditem")
@NoArgsConstructor
public class FoodItem {

    @Id
    @Column(name = "f_num", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fooditem_num;

    @Column(name = "c_num", nullable = false, length = 20)
    private String bigCgNum;

    @Column(name = "c_num2", nullable = true, length = 20)
    private String midCgNum;

    @Column(name = "f_title", nullable = false)
    private String foodItem_Title;

    @Column(name = "f_contents")
    private String foodItem_Contents;

    @Column(name = "f_price")
    private int foodItem_price;

    @Column(name = "f_count")
    private String foodItem_Count;

    @Column(name = "f_date")
    private Date foodItem_Date;

    @Column(name = "f_edate")
    private Date foodItem_Expire_Date;

    @Column(name = "f_views")
    private int foodItem_Views;

    @Column(name = "f_code", nullable = true)
    private String foodItem_Code;

    @Column(name = "f_volume")
    private String foodItem_Volume;

    @Column(name = "f_origin")
    private String foodItem_Origin;

    @Column(name = "f_cal")
    private String foodItem_Cal;

    @Column(name = "f_save")
    private String foodItem_Save;

    @Column(columnDefinition = "TINYINT DEFAULT '1'")
    private int status;

}
