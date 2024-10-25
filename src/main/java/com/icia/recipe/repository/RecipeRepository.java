package com.icia.recipe.repository;

import com.icia.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    // SELECT
    @Query(value = "select * from Recipe " +
            "order by r_num desc", nativeQuery = true)
    List<Recipe> getRecipeList();

    // INSERT


    // UPDATE


    // DELETE

}
