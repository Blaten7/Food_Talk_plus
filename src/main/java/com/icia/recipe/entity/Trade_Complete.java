package com.icia.recipe.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@Table(name = "tradecomplete")
public class Trade_Complete implements Serializable {

    @Id
    @Column(name = "tc_num", nullable = false, unique = true)
    private Long trade_complete_num;

    @Column(name = "t_num", nullable = false, unique = true)
    private int trade_num;

    @Column(name = "tc_check", columnDefinition = "CHAR(1) DEFAULT 0", length = 1)
    private String check;

    @OneToOne
    @JoinColumn(name = "m_id", insertable = false, updatable = false)
    private Member memberId;
}
