package com.example.project2.mapper;

import com.example.project2.domain.ApplyRecruit;
import com.example.project2.domain.Member;
import com.example.project2.domain.Recruit;
import org.apache.ibatis.annotations.*;

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
            INSERT INTO Member(id, password, name, gender, email, phoneNum, nickName, address, addressSggNm)
            VALUES(#{id}, #{password}, #{name}, #{gender}, #{email}, #{phoneNum}, #{nickName}, #{address}, #{addressSggNm})
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

    @Update("""
			<script>
			UPDATE Member m LEFT JOIN MemberAuthority ma ON m.id = ma.memberId
			SET
			 	<if test="password neq null and password neq ''">
			 		password = #{password},
			 	</if>
			 	m.name = #{name},
				m.phoneNum = #{phoneNum},
				m.nickName = #{nickName},
				m.address = #{address}
			WHERE id = #{id}
			</script>
			""")
    Integer modify(Member member);

    @Delete("""
            DELETE FROM Member
            WHERE id = #{id}
            """)
    Integer deleteMember(String id);

    @Delete("""
            DELETE FROM MemberAuthority
            WHERE memberId = #{id}
            """)
    void deleteAuthority(String id);

    @Insert("""
            INSERT INTO DocuForMember(memberId, fileName)
            VALUES (#{id}, #{originalFilename})
            """)
    void insertFileName(String id, String originalFilename);

    @Select("""
            SELECT *\s
            FROM Member m\s
            LEFT JOIN MemberAuthority ma ON m.id = ma.memberId
            LEFT JOIN DocuForMember dm ON m.id = dm.memberId
            WHERE dm.fileName IS NOT NULL
                AND ma.authority = 'preNeedVolunteer'  
            """)
    List<Member> getPreAuthority();

    @Update("""
            UPDATE MemberAuthority
            SET
                authority = 'needVolunteer'
            WHERE 
                memberId = #{id}
            """)
    Integer giveAuthority(String id);

    @Insert("""
            INSERT INTO ApplyRecruit(memberId, name, email, phoneNum, gender, title, participation, recruitId)
            VALUES(#{id}, #{name}, #{email}, #{phoneNum}, #{gender}, #{title}, #{participation}, #{recruitId})
            """)
    Integer applyRecruit(String id, String name, String email, String phoneNum, String gender, String title, String participation, Integer recruitId);

    @Select("""
            SELECT
                memberId, 
                name, 
                email,
                phoneNum,
                gender,
                title,
                participation,
                recruitId
            FROM ApplyRecruit
            WHERE memberId = #{id}
            """)
    ApplyRecruit selectApplyRecruitById(String id);

    @Select("""
            SELECT *
            FROM ApplyRecruit
            WHERE recruitId = #{recruitId}
            """)
    ApplyRecruit getPreApproval(Integer recruitId);

    @Select("""
            SELECT nickName
            FROM Member
            WHERE id = #{id}
            """)
    String getNickName(String id);

    @Select("""
            SELECT id
            FROM Recruit
            WHERE writer = #{writer}
            """)
    Integer getRecruitId(String writer);
}
