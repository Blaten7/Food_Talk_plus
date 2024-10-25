package com.icia.recipe.repository;

import com.icia.recipe.entity.Order_Status_Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusRepository extends JpaRepository<Order_Status_Log, Long> {

    // SELECT
    @Query(value = "SELECT COUNT(*) FROM order1_status_log WHERE new_status = 2 " +
            "AND updated_at BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 1 DAY) " +
            "AND old_status != 2",
            nativeQuery = true)
    Integer getTodayOrderDelivery();


    // INSERT


    // UPDATE


    // DELETE
}
