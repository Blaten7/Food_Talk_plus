package com.icia.recipe.repository;

import com.icia.recipe.entity.Notice;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends CrudRepository<Notice, Integer> {

    // SELECT
    @Query(value = "SELECT * FROM notice", nativeQuery = true)
    List<Notice> getNoticeList();


    // INSERT
    @Modifying
    @Query(value = "INSERT INTO notice (title, contents, m_id) VALUES (:title, :contents, :id)", nativeQuery = true)
    void insertNotice(@Param("title") String title,
                      @Param("contents") String contents,
                      @Param("id") String id);



    // UPDATE


    // DELETE
}
