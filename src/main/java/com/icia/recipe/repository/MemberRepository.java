package com.icia.recipe.repository;

import com.icia.recipe.dto.manageDto.MemberDto;
import com.icia.recipe.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    // SELECT
    @Query(value = "SELECT m_id, m_name, m_phone FROM member WHERE m_id = :m_id", nativeQuery = true)
    List<Object[]> checkId(@Param("m_id") String m_id);

    @Query(value = "SELECT * FROM member", nativeQuery = true)
    List<Object[]> getMemberList();

    @Query(value = "SELECT m_name FROM member WHERE m_id = :mId", nativeQuery = true)
    String getMemberName(@Param("mId") String mId);

    @Query(value = "select * from member where m_id= :m_id", nativeQuery = true)
    MemberDto check(@Param("m_id") String m_id);

    @Query(value = "select * from member where m_id= :m_id", nativeQuery = true)
    MemberDto getMemberInfo(@Param("m_id") String m_id);

    @Query(value = "select m_name from member where m_id= :m_id", nativeQuery = true)
    MemberDto getMemberInfoId(@Param("m_id") String m_id);

    @Query(value = "select m_pw from member where m_id= :m_id", nativeQuery = true)
    String getSecurityPw(@Param("m_id") String m_id);

    @Query(value = "select m_id from member where m_name = :#{#member.m_name} and m_phone = :#{#member.m_phone}", nativeQuery = true)
    String searchid(@Param("member") MemberDto member);

    @Query(value = "select m_id from member where m_id= :#{#member.m_id} and m_name= :#{#member.m_name} and m_phone= :#{#member.m_phone}", nativeQuery = true)
    String searchpw(@Param("member") MemberDto member);

    @Query(value = "select m_id from member where m_id= :#{#member.m_id} and m_name= :#{#member.m_name} and m_phone= :#{#member.m_phone}", nativeQuery = true)
    String passwordRecovery(@Param("member") MemberDto member);

    @Query(value = "select m_name from member where m_id= :m_id", nativeQuery = true)
    String findId(@Param("m_id") String m_id);

    @Query(value = "select m_id, m_name from member where m_name = :mname and m_phone = :phone", nativeQuery = true)
    List<MemberDto> getSearchIdPw(@Param("mname") String mname, @Param("phone") String phone);

    @Query(value = "select * from member where m_id= :id and m_name= :name and m_phone= :phone", nativeQuery = true)
    String getSearchPw(@Param("id") String id, @Param("name") String name, @Param("phone") String phone);

    @Query(value = "select * from member where m_pw= :pw", nativeQuery = true)
    String tempPwConfirm(@Param("pw") String pw);

    // INSERT
    @Query(value = "insert into member values (:m_id, :m_pw, :m_name, :m_address, :m_phone, default, default)", nativeQuery = true)
    boolean join(@Param("m_id") String m_id, @Param("m_pw") String m_pw, @Param("m_name") String m_name,
                 @Param("m_address") String m_address, @Param("m_phone") String m_phone);

    // UPDATE
    @Query(value = "update member set m_pw= :#{#member.m_pw} where m_id= :#{#member.m_id}", nativeQuery = true)
    boolean changepw(@Param("member") MemberDto member);

    @Query(value = "update member set m_pw= :pw where m_id= :id and m_phone= :phone and m_name= :name", nativeQuery = true)
    boolean updateTempPw(@Param("id") String id, @Param("name") String name, @Param("phone") String phone, @Param("pw") String pw);

    @Query(value = "update member set m_pw= :newPw where m_pw= :pw", nativeQuery = true)
    boolean updateNewPw(@Param("pw") String pw, @Param("newPw") String newPw);
}
