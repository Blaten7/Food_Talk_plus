package com.icia.recipe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "category")
public class Category {

    @Id
    @Column(name = "c_num", nullable = false)
    private String bigCgNum;

    @Column(name = "c_num2", nullable = true, columnDefinition = "VARCHAR(10) DEFAULT NULL")
    private String midCgNum;

    @Column(name = "c_name", nullable = false)
    private String category_name;

}
