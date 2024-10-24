package com.icia.recipe.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "trade")
public class Trade implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "t_num", nullable = false, unique = true)
    private Long trade_num;

    @Column(name = "t_count", columnDefinition = "VARCHAR(20) DEFAULT 0", length = 20)
    private String trade_count;

    @Column(name = "t_date", columnDefinition = "DATE DEFAULT NOW()")
    private Date trade_date;

    @Column(name = "t_views", columnDefinition = "SMALLINT DEFAULT 0")
    private Integer trade_views;

    @Column(name = "t_title", columnDefinition = "VARCHAR(50) DEFAULT NULL", length = 50)
    private String trade_title;

    @Column(name = "visible", nullable = false, columnDefinition = "CHAR(1) DEFAULT 1")
    private String trade_visible;

    @Column(name = "c_num", length = 10, columnDefinition = "VARCHAR(10) DEFAULT NULL")
    private String c_num;

    @ManyToMany
    @JoinTable(
            name = "tradeCategory",  // 중간 테이블 이름
            joinColumns = @JoinColumn(name = "category_c_num", referencedColumnName = "c_num", insertable = false, updatable = false),  // 중간 테이블의 category_c_num이 Category의 c_num을 참조
            inverseJoinColumns = @JoinColumn(name = "trade_c_num", referencedColumnName = "c_num", insertable = false, updatable = false)  // 중간 테이블의 trade_c_num이 Trade의 c_num을 참조
    )
    private List<Category> tradeCg = new ArrayList<>();


    //    @ManyToMany
//    @JoinTable(name = "tradeCategory",
//    joinColumns = @JoinColumn(name = "c_num", insertable = false, updatable = false),
//    inverseJoinColumns = @JoinColumn(name = "c_num", insertable = false, updatable = false))
//    private List<Category> tradeCg = new ArrayList<Category>();


    @OneToMany
    @JoinTable(name = "tradeMember",
            joinColumns = @JoinColumn(name = "trade_m_id", insertable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "member_m_id"))
    private List<Member> memberList = new ArrayList<>();
}
