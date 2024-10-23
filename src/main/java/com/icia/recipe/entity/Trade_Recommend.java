package com.icia.recipe.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Data
@NoArgsConstructor
@Table(name = "traderecommend")
public class Trade_Recommend {

    @EmbeddedId
    private TradeRecommend_EmbeddableKey tradeRecommendKey;
}
