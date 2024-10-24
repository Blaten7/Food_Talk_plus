package com.icia.recipe.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "tradecomplete")
public class Trade_Complete {

    @Id
    @Column(name = "tc_num", nullable = false, unique = true)
    private int trade_complete_num;

    @Column(name = "m_id", nullable = false, length = 20)
    private String memberId_FK_tradeComplete;

    @Column(name = "t_num", nullable = false, unique = true)
    private int trade_num;

    @Column(name = "tc_check", columnDefinition = "CHAR(1) DEFAULT 0", length = 1)
    private String check;

    @OneToOne
    @JoinColumn(name = "m_id")
    private Member memberId;
}
