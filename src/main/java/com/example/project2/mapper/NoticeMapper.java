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
            SELECT 
                n.id,
                n.title,
                n.body,
                n.writer,
                n.inserted,
                dfn.fileName
            FROM Notice n LEFT JOIN DocuForNotice dfn ON n.id = dfn.noticeId
            WHERE n.id = #{id}
            """)
    @ResultMap("noticeResultMap")
    Notice getNotice(Integer id);

    @Insert("""
            INSERT INTO Notice(title, body, writer)
            VALUES (#{title}, #{body}, #{writer})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
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

    @Insert("""
            INSERT INTO DocuForNotice(noticeId, FileName)
            VALUES (#{id}, #{originalFilename})
            """)
    Integer insertFileName(Integer id, String originalFilename);

    @Delete("""
            DELETE FROM DocuForNotice
            WHERE noticeId = #{id}
                AND fileName = #{fileName}
            """)
    void deleteFileNameByNoticeIdAndFileName(Integer id, String fileName);
}
