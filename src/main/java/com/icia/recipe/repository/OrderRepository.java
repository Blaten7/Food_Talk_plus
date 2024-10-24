package com.icia.recipe.repository;

import com.icia.recipe.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    void executeGettodayprofitcount();

    void executeGetweekprofitcount();

    void executeGetmonthlyprofit();
}
