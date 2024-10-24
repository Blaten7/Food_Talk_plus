package com.icia.recipe.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "trade")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "t_num", nullable = false, unique = true)
    private Integer trade_num;

    @Column(name = "c_num", length = 10, columnDefinition = "VARCHAR(10) DEFAULT NULL")
    private String categoryNum_FK_trade;

    @Column(name = "m_id", nullable = false, length = 20)
    private String memberId_FK_trade;

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

    @ManyToMany
    @JoinTable(name = "tradeCategory",
    joinColumns = @JoinColumn(name = "c_num"),
    inverseJoinColumns = @JoinColumn(name = "c_num"))
    private List<Category> tradeCg = new ArrayList<Category>();

    @OneToMany
    @JoinTable(name = "tradeMember",
    joinColumns = @JoinColumn(name = "m_id"),
    inverseJoinColumns = @JoinColumn(name = "m_id"))
    private List<Member> memberList = new ArrayList<>();
}
