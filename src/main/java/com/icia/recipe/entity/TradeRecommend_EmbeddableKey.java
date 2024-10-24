package com.icia.recipe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class TradeRecommend_EmbeddableKey implements Serializable {

    @Column(name = "m_id", nullable = false, length = 20)
    private String memberId_FK_tradeRecommend;

    @Column(name = "t_num", nullable = false, unique = true)
    private int tradeNum_FK_tradeRecommend;

}
