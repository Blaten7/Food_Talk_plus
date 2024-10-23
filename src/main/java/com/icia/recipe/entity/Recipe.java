package com.icia.recipe.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "r_num", nullable = false, unique = true)
    private Integer recipe_num;

    @Column(name = "m_id", nullable = false, length = 20)
    private String memberId_FK_recipe;

    @Column(name = "r_title", length = 20, columnDefinition = "VARCHAR(20) DEFAULT NULL")
    private String recipe_title;

    @Column(name = "r_contents", nullable = false, length = 50)
    private String recipe_contents;

    @Column(name = "r_date", columnDefinition = "DATE DEFAULT NOW()")
    private Date recipe_date;

    @Column(name = "r_views", length = 5, columnDefinition = "VARCHAR(5) DEFAULT 0")
    private String recipe_views;

    @Column(name = "r_delete", columnDefinition = "CHAR(1) DEFAULT 0")
    private String recipe_delete;
}
