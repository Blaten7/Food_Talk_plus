package com.icia.recipe.repository;

import com.icia.recipe.entity.InvenAdd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvenAddRepository extends JpaRepository<InvenAdd, Long> {

    // SELECT


    // INSERT


    // UPDATE


    // DELETE
    @Modifying
    @Query(value = "delete from InvenAdd " +
            "where inven_add_num in :deleteKey")
    void deleteFoodItemlist(
            @Param("deleteKey") List<String> deleteKey
    );

}
