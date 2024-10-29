package com.icia.recipe.repository;

import com.icia.recipe.entity.Img;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    @Query(value = "SELECT * FROM img i JOIN fooditem f ON i.f_num = f.f_num " +
            "JOIN category c ON c.c_num = f.c_num WHERE c.c_num = :bigCgNum " +
            "AND f.f_title = :title AND f.f_code = :code AND c.c_name = :cgName",
            nativeQuery = true)
    List<Img> getImg(@Param("bigCgNum") String bigCgNum,
                     @Param("title") String title,
                     @Param("code") String code,
                     @Param("cgName") String cgName);

    @Query(value = "SELECT i_sys_name FROM img WHERE f_num = :code", nativeQuery = true)
    String getFiImg(@Param("code") String code);

    // INSERT

    @Query(value = "SELECT CAST(MAX(CAST(f_num AS UNSIGNED)) AS CHAR) FROM fooditem", nativeQuery = true)
    String getMaxFNum();

    @Modifying
    @Query(value = "INSERT INTO img (i_path, i_sys_name, i_original_name, f_num, m_id, i_filesize) " +
            "VALUES (:iPath, :iSysName, :iOriginalName, :maxNum, :mId, :iFileSize)", nativeQuery = true)
    boolean insertFoodItemImg(@Param("iPath") String iPath,
                           @Param("iSysName") String iSysName,
                           @Param("iOriginalName") String iOriginalName,
                           @Param("maxNum") String maxNum,
                           @Param("mId") String mId,
                           @Param("iFileSize") String iFileSize);





    // UPDATE


    // DELETE
}
