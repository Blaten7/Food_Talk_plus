package com.icia.recipe.repository;

import com.icia.recipe.entity.Trade_Recommend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRecommendRepository extends JpaRepository<Trade_Recommend, Long> {
}
