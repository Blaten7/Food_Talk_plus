package com.icia.recipe.repository;

import com.icia.recipe.entity.Alert;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {

}
