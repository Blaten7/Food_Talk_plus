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
    @Query(value = "SELECT m_id FROM member WHERE m_id = :m_id", nativeQuery = true)
    List<MemberDto> checkId(@Param("m_id") String mId);

    @Query(value = "SELECT * FROM member", nativeQuery = true)
    List<MemberDto> getMemberList();

    @Query(value = "SELECT m_name FROM member WHERE m_id = :mId", nativeQuery = true)
    String getMemberName(@Param("mId") String mId);

    @Query(value = "select * from member where m_id=#{m_id}", nativeQuery = true)
    MemberDto check(String m_id);

    @Query(value = "select * from member where m_id=#{m_id}", nativeQuery = true)
    MemberDto getMemberInfo(String username);

    @Query(value = "select m_name from member where m_id=#{m_id}", nativeQuery = true)
    MemberDto getMemberInfoId(String username);

    @Query(value = "select m_pw from member where m_id=#{m_id}", nativeQuery = true)
    String getSecurityPw(String mId);

    @Query(value = "select m_id from member where m_name=#{m_name} and m_phone=#{m_phone}", nativeQuery = true)
    String searchid(MemberDto member);

    @Query(value = "select m_id from member where m_id=#{m_id} and m_name=#{m_name} and m_phone=#{m_phone}", nativeQuery = true)
    String searchpw(MemberDto member);

    @Query(value = "select m_id from member where m_id=#{m_id} and m_name=#{m_name} and m_phone=#{m_phone}", nativeQuery = true)
    String passwordRecovery(MemberDto member);

    @Query(value = "select m_name from member where m_id=#{m_id}", nativeQuery = true)
    String findId(String m_id);

    @Query(value = "select m_id, m_name from member where m_name = #{mname} and m_phone = #{phone}", nativeQuery = true)
    List<MemberDto> getSearchIdPw(String mname, String phone);

    @Query(value = "select * from member where m_id=#{id} and m_name=#{name} and m_phone=#{phone}", nativeQuery = true)
    String getSearchPw(String id, String name, String phone);

    @Query(value = "select * from member where m_pw=#{pw}", nativeQuery = true)
    String tempPwConfirm(String pw);



    // INSERT
    @Query(value = "insert into member values (m_id, m_pw, m_name, m_address, m_phone, default, default)", nativeQuery = true)
    boolean join(Member member);


    // UPDATE
    @Query(value = "update member set m_pw=#{m_pw} where m_id=#{m_id}", nativeQuery = true)
    boolean changepw(MemberDto member);

    @Query(value = "update member set m_pw=#{pw} where m_id=#{id} and m_phone=#{phone} and m_name=#{name}", nativeQuery = true)
    boolean updateTempPw(String id, String name, String phone, String pw);

    @Query(value = "update member set m_pw=#{newPw} where m_pw=#{pw}", nativeQuery = true)
    boolean updateNewPw(String pw, String newPw);


    // DELETE

}
