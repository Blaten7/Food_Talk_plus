package com.icia.recipe.repository;

import com.icia.recipe.entity.Category;
import com.icia.recipe.entity.FoodItem;
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

    @Query(value = "select * from Category where left(c_num, 1) = '2'", nativeQuery = true)
    List<Category> getCategory();

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

    @Query(value = "select c_num from category " +
            "where c_name = : fiBigCg", nativeQuery = true)
    List<FoodItem> getBigCg(@Param("fiBigCg") String fiBigCg);

    @Query(value = "select c_num from category " +
            "where c_name = : fiBigCg", nativeQuery = true)
    List<FoodItem> getMidCg(@Param("fiMidCg") String fiMidCg);

    @Query(value = "SELECT c1.c_num AS c1_num, c1.c_name AS c1_name, " +
            "c2.c_num AS c2_num, c2.c_num2 AS c2_num2, c2.c_name AS c2_name, " +
            "c3.c_num AS c3_num, c3.c_num2 AS c3_num2, c3.c_name AS c3_name " +
            "FROM category c1 " +
            "LEFT JOIN category c2 ON c1.c_num = c2.c_num2 " +
            "LEFT JOIN category c3 ON c2.c_num = c3.c_num2 " +
            "WHERE LEFT(c1.c_num, 1) = 1",
            nativeQuery = true)
    List<Category> searchCtg();




    // INSERT
    @Query(value = "SELECT CAST(MAX(CAST(c_num AS UNSIGNED)) + 1 AS CHAR) " +
            "FROM category " +
            "WHERE LEFT(c_num, 1) = :prefix",
            nativeQuery = true)
    String getNextCategoryNum(@Param("prefix") String prefix);

    @Modifying
    @Query(value = "INSERT INTO category (c_num, c_num2, c_name) VALUES (:maxNum, :cgNum, :cgName)",
            nativeQuery = true)
    void addMidCg(@Param("maxNum") String maxNum, @Param("cgNum") String cgNum, @Param("cgName") String cgName);

    @Modifying
    @Query(value = "INSERT INTO category (c_num, c_name) VALUES (:maxNum, :cgName)",
            nativeQuery = true)
    void addBigCg(@Param("maxNum") String maxNum, @Param("cgName") String cgName);


    // UPDATE


    // DELETE

    @Modifying
    @Query(value = "DELETE FROM Category WHERE C_NAME = :name",
            nativeQuery = true)
    void deleteCategoryByName(
            @Param("name") String name
    );
}
