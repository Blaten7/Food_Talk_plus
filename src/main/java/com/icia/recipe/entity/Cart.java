package com.icia.recipe.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.icia.recipe.entity.FoodItem;

@Entity
@Data
@NoArgsConstructor
@Table(name = "cart")
public class Cart implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ca_num", nullable = false, unique = true)
    private Long cart_num;

    @Column(name = "f_num", unique = true)
    private int f_num;

    @Column(name = "ca_count", length = 10, columnDefinition = "VARCHAR(10) DEFAULT NULL")
    private String ca_count;

    @Column(name = "ca_date", columnDefinition = "DATE DEFAULT NOW()")
    private Date ca_date;

    @OneToOne
    @JoinColumn(name = "m_id", nullable = false, foreignKey = @ForeignKey(name = "cart_ibfk_1"), insertable = false, updatable = false)
    private Member member;

    @ManyToMany
    @JoinTable(name = "memberCart", // 중간 테이블
    joinColumns = @JoinColumn(name = "cart_f_num", insertable = false, updatable = false), // 현재 클래스에서 조인할 컬럼명 설정
    inverseJoinColumns = @JoinColumn(name = "fooditem_f_num")) // 반대 클래스에서 조인할 컬림명 설정
    private List<FoodItem> foodList = new ArrayList<>();

}
