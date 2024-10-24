package com.icia.recipe.repository;

import com.icia.recipe.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Modifying
    void saveInsertcartlist();

    @Modifying
    void updateUpdatefooditemcount();

    @Modifying
    void deleteDeletecart();

    List<Cart> findBySelectcart();

    List<Cart> findBySelectcartcount();

    @Modifying
    void saveInsertorder();

    @Modifying
    void saveInsertorderdetail();

    @Modifying
    void deleteDeletecartname();
}
