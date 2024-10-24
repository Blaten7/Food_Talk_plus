package com.icia.recipe.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "recipe")
public class Recipe implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "r_num", nullable = false, unique = true)
    private Long recipe_num;

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

    @ManyToMany
    @JoinTable(name = "membersRecipe",
    joinColumns = @JoinColumn(name = "recipe_m_id", insertable = false, updatable = false),
    inverseJoinColumns = @JoinColumn(name = "member_m_id"))
    private List<Member> membersRecipe;

}
