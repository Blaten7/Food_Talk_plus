package com.icia.recipe.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "category")
public class Category implements Serializable {

    @Id
    @Column(name = "c_num", nullable = false)
    private String bigCgNum;

    @Column(name = "c_num2", nullable = true, columnDefinition = "VARCHAR(10) DEFAULT NULL")
    private String midCgNum;

    @Column(name = "c_name", nullable = false)
    private String category_name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "c_num2", insertable = false, updatable = false)
    private Category parentCategory; // 명확한 이름 사용

    @OneToMany(mappedBy = "bigCgNum")
    private List<FoodItem> foodItems = new ArrayList<FoodItem>();

    @ManyToMany(mappedBy = "tradeCg")
    private List<Trade> tradeList = new ArrayList<>();

}
