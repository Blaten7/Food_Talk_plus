package com.icia.recipe.repository;

import com.icia.recipe.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    // SELECT
    @Query(value = "SELECT m_id FROM member WHERE m_id = :m_id", nativeQuery = true)
    Optional<Member> checkId(@Param("m_id") String mId);

    @Query(value = "SELECT * FROM member", nativeQuery = true)
    List<Member> getMemberList();

    @Query(value = "SELECT m_name FROM member WHERE m_id = :mId", nativeQuery = true)
    Optional<String> getMemberName(@Param("mId") String mId);


    // INSERT


    // UPDATE


    // DELETE

}
