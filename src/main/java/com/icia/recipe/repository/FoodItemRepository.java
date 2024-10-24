package com.icia.recipe.repository;

import com.icia.recipe.entity.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {

    void executeViewsplus();

    void executeSearchfooditem();

    void executeSearchctg();

    void executeSearchfooddetail();

    void executeSearchfooddetailinfo();

    void executeGetfooditemcount();
}
