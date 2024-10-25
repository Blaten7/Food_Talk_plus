package com.icia.recipe.repository;

import com.icia.recipe.entity.Order_Status_Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusRepository extends JpaRepository<Order_Status_Log, Long> {

    // SELECT


    // INSERT


    // UPDATE


    // DELETE
}
