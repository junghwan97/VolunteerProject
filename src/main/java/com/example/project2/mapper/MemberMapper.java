package com.example.project2.mapper;

import com.example.project2.domain.Member;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MemberMapper {


    @Select("""
            SELECT *
            FROM Member m LEFT JOIN MemberAuthority ma ON m.id = ma.memberId
            WHERE m.id = #{id}
            """)
    @ResultMap("memberMap")
    Member selectById(String id);

    @Select("""
            SELECT *
            FROM Member
            WHERE phoneNum = #{phoneNum}
            """)
    Member selectByPhoneNum(String phoneNum);

    @Select("""
            SELECT *
            FROM Member
            WHERE nickName = #{nickName}
            """)
    Member selectByNickName(String nickName);

    @Insert("""
            INSERT INTO Member(id, password, name, gender, email, phoneNum, nickName, address)
            VALUES(#{id}, #{password}, #{name}, #{gender}, #{email}, #{phoneNum}, #{nickName}, #{address})
            """)
    Integer signupInsert(Member member);

    @Insert("""
            INSERT INTO MemberAuthority
            VALUES(#{id}, #{authority})
            """)
    Integer insertAuthority(Member member);

    @Select("""
            SELECT nickName
            FROM Member
            WHERE id = #{name}
            """)
    String selectByName(String name);

    @Select("""
            SELECT *
            FROM Member m LEFT JOIN MemberAuthority ma ON m.id = ma.memberId
            WHERE id = #{id}
            """)
    Member getInfo(String id);

    @Select("""
            SELECT *
            FROM Member
            WHERE id = #{name}
            """)
    Member getUserInfo(String name);
}
