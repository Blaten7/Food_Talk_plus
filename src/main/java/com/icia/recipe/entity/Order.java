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
@Table(name = "order1")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "o_num")
    private Long order_num;

    @Column(name = "o_total", nullable = false)
    private int order_total;

    @Column(name = "o_date", columnDefinition = "DATE DEFAULT NOW()")
    private Date order_date;

    @Column(name = "o_name", nullable = false, length = 10)
    private String order_name;

    @Column(name = "o_address", nullable = false, length = 50)
    private String order_address;

    @Column(name = "o_phone", nullable = false, length = 13)
    private String order_phone;

    @Column(name = "o_count", nullable = false)
    private int order_count;

    @Column(name = "o_delivery", columnDefinition = "CHAR(1) DEFAULT 0", length = 1)
    private String order_delivery;

    @Column(name = "o_post", nullable = false, length = 20)
    private String order_post;

    @Column(name = "m_id", nullable = false, length = 20)
    private String memberId_FK_order;

    @ManyToMany
    @JoinTable(name = "membersOrder",
    joinColumns = @JoinColumn(name = "order_m_id", insertable = false, updatable = false),
    inverseJoinColumns = @JoinColumn(name = "member_m_id"))
    private List<Member> membersOrderList = new ArrayList<Member>();
}
