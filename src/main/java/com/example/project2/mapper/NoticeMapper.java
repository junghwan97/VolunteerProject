package com.example.project2.mapper;

import com.example.project2.domain.Notice;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoticeMapper {
    @Select("""
            SELECT *
            FROM Notice
            """)
    List<Notice> getNoticeList();

    @Select("""
            SELECT *
            FROM Notice
            WHERE id = #{id}
            """)
    Notice getNotice(Integer id);

    @Insert("""
            INSERT INTO Notice(title, body, writer)
            VALUES (#{title}, #{body}, #{writer})
            """)
    Integer addNotice(Notice notice);

    @Update("""
            UPDATE Notice
            SET
                title = #{title},
                body = #{body}
            WHERE 
                id = #{id}
            """)
    Integer modifyNotice(Notice notice);

    @Delete("""
            DELETE FROM Notice
            WHERE id = #{id}
            """)
    Integer removeNotice(Integer id);
}
