package com.icia.recipe.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.naming.Name;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ca_num", nullable = false, unique = true)
    private int cart_num;

    @Column(name = "f_num", unique = true)
    private int f_num;

    @Column(name = "ca_count", length = 10, columnDefinition = "VARCHAR(10) DEFAULT NULL")
    private String ca_count;

    @Column(name = "ca_date", columnDefinition = "DATE DEFAULT NOW()")
    private Date ca_date;

    @OneToOne
    @JoinColumn(name = "m_id", nullable = false, foreignKey = @ForeignKey(name = "cart_ibfk_1"))
    private Member member;

    @ManyToMany
    @JoinTable(name = "memberCart", // 중간 테이블
    joinColumns = @JoinColumn(name = "f_num"), // 현재 클래스에서 조인할 컬럼명 설정
    inverseJoinColumns = @JoinColumn(name = "f_num")) // 반대 클래스에서 조인할 컬림명 설정
    private List<FoodItem> foodList = new ArrayList<>();

}
