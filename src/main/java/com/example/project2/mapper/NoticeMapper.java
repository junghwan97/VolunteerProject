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

    @Select("""
			<script>
			<bind name="pattern" value="'%' + search + '%'" />
			SELECT COUNT(*)
			FROM Notice
			<where>
			
			<if test="type == 'all'">
				title LIKE #{pattern}	
			 OR body LIKE #{pattern}
		   	 OR writer LIKE #{pattern}
			</if>
			
			<if test="type == 'title'">
				title LIKE #{pattern}	
			</if>						
			
			<if test="type == 'body'">
			 OR body LIKE #{pattern}
			</if>
			
			<if test="type == 'writer'">
		   	 OR writer LIKE #{pattern}			
			</if>
		   	</where> 
		   	 </script>
			""")
    Integer countAll(String search, String type);

    @Select("""
			<script>
			<bind name="pattern" value="'%' + search + '%'" />
			SELECT *											
			FROM Notice
			<where>
				<if test="(type eq 'all') or (type eq 'title')">
				   title  LIKE #{pattern}
				</if>
				<if test="(type eq 'all') or (type eq 'body')">
				OR body   LIKE #{pattern}
				</if>
				<if test="(type eq 'all') or (type eq 'writer')">
				OR writer LIKE #{pattern}
				</if>
			</where>
			ORDER BY id DESC
			LIMIT #{startIndex}, #{rowPerPage}
			</script>
			""")
    List<Notice> selectAllPaging(Integer startIndex, Integer rowPerPage, String search, String type);
}
