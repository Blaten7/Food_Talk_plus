package com.icia.recipe.repository;

import com.icia.recipe.entity.Category;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // SELECT
    @Query(value = "SELECT * FROM Category", nativeQuery = true)
    List<Category> getCgName();

    @Query(value = "select * from Category where left(c_num, 1) = '1'", nativeQuery = true)
    List<Category> getFoodItemBigCg();

    @Query(value = "select * from category where left(c_num, 1) = '4'", nativeQuery = true)
    List<Category> getRecipeBigCg();

    @Query(value = "select * from Category where c_num2 = :fiMcg", nativeQuery = true)
    List<Category> getFoodItemMidCg(@Param("fiMcg") String fiMcg);

    @Query(value = "select * from Category where c_num2 = :rMcg", nativeQuery = true)
    List<Category> getRecipeMidCg(@Param("rMcg") String rMcg);

    @Query(value = "select * from Category where c_num2 = :fiScg", nativeQuery = true)
    List<Category> getFoodItemSmallCg(@Param("fiScg") String fiScg);

    @Query(value = "select * from Category where c_num = :rScg", nativeQuery = true)
    List<Category> getRecipeSmallCg(@Param("rScg") String rScg);

    @Query(value = "select c_name from category where c_num = :cNum", nativeQuery = true)
    String getFoodItemListNaming(@Param("cNum") String cNum);

    @Query(value = "select c_name from category where c_num = :cNum", nativeQuery = true)
    String getFoodItemListNaming2(@Param("cNum") String cNum);


    // INSERT 배고프당..


    // UPDATE


    // DELETE

    @Modifying
    @Query(value = "DELETE FROM Category WHERE C_NAME = :name",
            nativeQuery = true)
    void deleteCategoryByName(
            @Param("name") String name
    );
}
