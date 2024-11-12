package com.icia.recipe.repository;

import com.icia.recipe.dto.mainDto.Member;
import com.icia.recipe.dto.manageDto.MemberDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<com.icia.recipe.entity.Member, Long> {

    // SELECT
    @Query(value = "SELECT m_id, m_name, m_phone FROM member WHERE m_id = :m_id", nativeQuery = true)
    List<Object[]> checkId(@Param("m_id") String m_id);

    @Query(value = "SELECT * FROM member", nativeQuery = true)
    List<Object[]> getMemberList();

    @Query(value = "SELECT m_name FROM member WHERE m_id = :mId", nativeQuery = true)
    String getMemberName(@Param("mId") String mId);

    @Query(value = "select * from member where m_id= :m_id", nativeQuery = true)
    com.icia.recipe.dto.mainDto.Member check(@Param("m_id") String m_id);

    @Query(value = "SELECT m_id, m_pw, m_name, role FROM member WHERE m_id = :username", nativeQuery = true)
    Object[] getMemberInfo(@Param("username") String username);

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
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO member (m_id, m_pw, m_name, m_address, m_phone) " +
            "VALUES (:#{#member.m_id}, :#{#member.m_pw}, :#{#member.m_name}, :#{#member.m_address}, :#{#member.m_phone})",
            nativeQuery = true)
    int join(@Param("member") com.icia.recipe.dto.mainDto.Member member);


    // UPDATE
    @Query(value = "update member set m_pw= :#{#member.m_pw} where m_id= :#{#member.m_id}", nativeQuery = true)
    boolean changepw(@Param("member") MemberDto member);

    @Query(value = "update member set m_pw= :pw where m_id= :id and m_phone= :phone and m_name= :name", nativeQuery = true)
    boolean updateTempPw(@Param("id") String id, @Param("name") String name, @Param("phone") String phone, @Param("pw") String pw);

    @Query(value = "update member set m_pw= :newPw where m_pw= :pw", nativeQuery = true)
    boolean updateNewPw(@Param("pw") String pw, @Param("newPw") String newPw);

    @Query(value = "select * from member where m_id=:kakaoId", nativeQuery = true)
    Optional<Object> findByKakaoId(Long kakaoId);

    @Query(value = "select * from member where m_id=:kakaoEmail", nativeQuery = true)
    Optional<Object> findByEmail(String kakaoEmail);

    @Query(value = "insert into member (m_name, m_pw, m_id, role) values (:#{#kakaoUser[0]}, :#{#kakaoUser[1]}, :#{#kakaoUser[2]}, :#{#kakaoUser[3]})", nativeQuery = true)
    void kakaoUserAdd(@Param("kakaoUser")Object kakaoUser);
}
