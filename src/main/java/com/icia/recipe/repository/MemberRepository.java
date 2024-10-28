package com.icia.recipe.repository;

import com.icia.recipe.dto.mainDto.Member;
import com.icia.recipe.dto.manageDto.MemberDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberDto, Long> {

    // SELECT
    @Query(value = "SELECT m_id FROM member WHERE m_id = :m_id", nativeQuery = true)
    List<Member> checkId(@Param("m_id") String mId);

    @Query(value = "SELECT * FROM member", nativeQuery = true)
    List<MemberDto> getMemberList();

    @Query(value = "SELECT m_name FROM member WHERE m_id = :mId", nativeQuery = true)
    String getMemberName(@Param("mId") String mId);

    @Select("select * from member where m_id=#{m_id}")
    MemberDto check(String m_id);

    @Select("select * from member where m_id=#{m_id}")
    Member getMemberInfo(String username);

    @Select("select m_name from member where m_id=#{m_id}")
    Member getMemberInfoId(String username);

    @Select("select m_pw from member where m_id=#{m_id}")
    String getSecurityPw(String mId);

    @Select("select m_id from member where m_name=#{m_name} and m_phone=#{m_phone}")
    String searchid(Member member);

    @Select("select m_id from member where m_id=#{m_id} and m_name=#{m_name} and m_phone=#{m_phone}")
    String searchpw(Member member);

    @Select("select m_id from member where m_id=#{m_id} and m_name=#{m_name} and m_phone=#{m_phone}")
    String passwordRecovery(Member member);

    @Select("select m_name from member where m_id=#{m_id}")
    String findId(String m_id);

    @Select("select m_id, m_name from member where m_name = #{mname} and m_phone = #{phone}")
    List<Member> getSearchIdPw(String mname, String phone);

    @Select("select * from member where m_id=#{id} and m_name=#{name} and m_phone=#{phone}")
    String getSearchPw(String id, String name, String phone);

    @Select("select * from member where m_pw=#{pw}")
    String tempPwConfirm(String pw);



    // INSERT
    @Insert("insert into member values (#{m_id},#{m_pw},#{m_name},#{m_address},#{m_phone},default,default)")
    boolean join(Member member);


    // UPDATE
    @Update("update member set m_pw=#{m_pw} where m_id=#{m_id}")
    boolean changepw(Member member);

    @Update("update member set m_pw=#{pw} where m_id=#{id} and m_phone=#{phone} and m_name=#{name}")
    boolean updateTempPw(String id, String name, String phone, String pw);

    @Update("update member set m_pw=#{newPw} where m_pw=#{pw}")
    boolean updateNewPw(String pw, String newPw);


    // DELETE

}
