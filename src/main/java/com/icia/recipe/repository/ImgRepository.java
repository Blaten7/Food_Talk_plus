package com.icia.recipe.repository;

import com.icia.recipe.entity.Img;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public interface ImgRepository extends JpaRepository<Img, Long> {

    // SELECT
    @Query(value = "select i_sys_name " +
            "from Img " +
            "where f_num = :code", nativeQuery = true)
    List<String> getFiImgSysName(
            @Param("code") String code
    );

    @Query(value = "select i_path " +
            "from Img " +
            "where f_num = :code", nativeQuery = true)
    List<String> getFiImgPath(
            @Param("code") String code
    );

    @Query(value = "select * from FoodItem f " +
            "join Category c " +
            "on f._c_num = c.c_num " +
            "where f_num =  :fNum", nativeQuery = true)
    List<Img> getFiImg();

    // INSERT


    // UPDATE


    // DELETE
}
