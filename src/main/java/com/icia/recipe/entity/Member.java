package com.icia.recipe.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mybatis.spring.annotation.MapperScan;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "member")
public class Member {

    @Id
    @Column(name = "m_id", nullable = false, length = 50)
    private String member_id;

    @Column(name = "m_pw", columnDefinition = "VARCHAR(150) DEFAULT NULL", length = 150)
    private String member_pw;

    @Column(name = "m_name", nullable = false, length = 10)
    private String member_name;

    @Column(name = "m_address", nullable = false, length = 50)
    private String member_address;

    @Column(name = "m_phone", nullable = false, length = 13)
    private String member_phone;

    @Column(name = "m_block", length = 1, columnDefinition = "CHAR(1) DEFAULT 0")
    private String member_block;

    @Column(name = "role", length = 5, columnDefinition = "VARCHAR(5) DEFAULT 'USER'")
    private String member_role;

    @OneToMany(mappedBy = "member")
    private List<Notice> alarmList = new ArrayList<>();

    @ManyToMany(mappedBy = "membersOrderList")
    private List<Order> orderList = new ArrayList<>();

    @ManyToMany(mappedBy = "membersRecipe")
    private List<Recipe> recipeList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "m_id")
    private Trade tradeList;

}
