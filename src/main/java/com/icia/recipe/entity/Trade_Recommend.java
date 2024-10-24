package com.icia.recipe.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@Table(name = "traderecommend")
public class Trade_Recommend implements Serializable {

    @EmbeddedId
    private TradeRecommend_EmbeddableKey tradeRecommendKey;
}
