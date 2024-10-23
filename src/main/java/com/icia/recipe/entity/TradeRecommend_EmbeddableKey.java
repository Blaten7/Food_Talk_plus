package com.icia.recipe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
public class TradeRecommend_EmbeddableKey implements Serializable {

    @Column(name = "m_id", nullable = false, length = 20)
    private String memberId_FK_tradeRecommend;

    @Column(name = "t_num", nullable = false, unique = true)
    private int tradeNum_FK_tradeRecommend;

    // 기본 생성자
    public TradeRecommend_EmbeddableKey() {
    }

    // 생성자
    public TradeRecommend_EmbeddableKey(String memberId_FK_tradeRecommend, int tradeNum_FK_tradeRecommend) {
        this.memberId_FK_tradeRecommend = memberId_FK_tradeRecommend;
        this.tradeNum_FK_tradeRecommend = tradeNum_FK_tradeRecommend;

    }

    // equals()와 hashCode() 반드시 구현
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TradeRecommend_EmbeddableKey that = (TradeRecommend_EmbeddableKey) o;
        return Objects.equals(memberId_FK_tradeRecommend, that.memberId_FK_tradeRecommend) && Objects.equals(tradeNum_FK_tradeRecommend, that.tradeNum_FK_tradeRecommend);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId_FK_tradeRecommend, tradeNum_FK_tradeRecommend);
    }
}
