package com.icia.recipe.home.dao;

import com.icia.recipe.entity.FoodItem;
import com.icia.recipe.entity.Member;
import com.icia.recipe.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MemberDao {
    @Insert("insert into member values (#{m_id},#{m_pw},#{m_name},#{m_address},#{m_phone},default,default)")
    boolean join(Member member);

    @Select("select * from member where m_id=#{m_id}")
    Member check(String m_id);

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

    @Update("update member set m_pw=#{m_pw} where m_id=#{m_id}")
    boolean changepw(Member member);

    @Select("select m_id from member where m_id=#{m_id} and m_name=#{m_name} and m_phone=#{m_phone}")
    String passwordRecovery(Member member);

    @Select("select m_name from member where m_id=#{m_id}")
    String findId(String m_id);

    @Select("select m_id, m_name from member where m_name = #{mname} and m_phone = #{phone}")
    List<Member> getSearchIdPw(String mname, String phone);

    @Select("select * from member where m_id=#{id} and m_name=#{name} and m_phone=#{phone}")
    String getSearchPw(String id, String name, String phone);

    @Update("update member set m_pw=#{pw} where m_id=#{id} and m_phone=#{phone} and m_name=#{name}")
    boolean updateTempPw(String id, String name, String phone, String pw);

    @Select("select * from member where m_pw=#{pw}")
    String tempPwConfirm(String pw);

    @Update("update member set m_pw=#{newPw} where m_pw=#{pw}")
    boolean updateNewPw(String pw, String newPw);

    List<Order> selectOrder(Search sDto);

    List<OrderDto> selectOrderDetail(String num);

    int getorderCount(String id);

    List<FoodItem> getRanking();

    List<FooditemDto> getRanking50();

    boolean insertNotice(String title, String contents, String id);

    List<NoticeDto> getNoticeList();

    List<Member> checkId(String m_id);
}