package com.icia.recipe.repository;

import com.icia.recipe.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Modifying
    void saveInsertnotice();

    List<Member> findBySelectorder();

    List<Member> findBySelectorderdetail();

    void executeGetordercount();

    void executeGetranking();

    void executeGetranking50();

    void executeGetnoticelist();

    void executeCheckid();
}
