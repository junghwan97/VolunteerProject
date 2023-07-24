package com.example.project2.mapper;

import com.example.project2.domain.Recruit;
import org.apache.ibatis.annotations.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper
public interface RecruitMapper {
    @Select("""
            SELECT *
            FROM Recruit
            """)
    List<Recruit> getAllRecruit();

    @Select("""
            SELECT 
                r.id,
                r.title,
                r.body,
                r.writer,
                r.inserted,
                r.vStartDate,
                r.vEndDate,
                r.vStartTime,
                r.vEndTime,
                r.vField,
                r.vPlace,
                fr.fileName,
                r.addressSggNm,
                r.tel
            FROM Recruit r LEFT JOIN FileNamesForRecruit fr ON r.id = fr.recruitId
            WHERE r.id = #{id}
            """)
    @ResultMap("recruitResultMap")
    Recruit getRecruitById(String id);

    @Insert("""
            INSERT INTO Recruit(title, writer, body, vStartDate, vEndDate, vStartTime, vEndTime, vField, vPlace, addressSggNm, tel)
            VALUES(#{title}, #{writer}, #{body}, #{vStartDate}, #{vEndDate}, #{vStartTime}, #{vEndTime}, #{vField}, #{vPlace}, #{addressSggNm}, #{tel})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer addRecruit(Recruit recruit);

    @Insert("""
            INSERT INTO FileNamesForRecruit(recruitId, fileName)
            VALUES(#{id}, #{originalFilename})
            """)
    void insertFileName(Integer id, String originalFilename);

    @Update("""
            UPDATE Recruit
            SET 
               title = #{title},
               body = #{body},
               vStartDate = #{vStartDate},
               vEndDate = #{vEndDate},
               vStartTime = #{vStartTime},
               vEndTime = #{vEndTime}
            WHERE 
                id = #{id}
            """)
    Integer modifyRecruit(Recruit recruit);

    @Delete("""
            DELETE FROM FileNamesForRecruit
            WHERE recruitId = #{id}
                AND fileName = #{fileName}
            """)
    void deleteFileNameByRecruitIdAndFileName(Integer id, String fileName);

    @Delete("""
            DELETE FROM Recruit
            WHERE id = #{id}
            """)
    Integer removeRecruit(String id);

    @Select("""
            SELECT fileName
            FROM FileNamesForRecruit
            WHERE recruitId = #{id}
            """)
    List<String> selectFileNamesByRecruitId(String id);

    @Delete("""
            DELETE FROM FileNamesForRecruit
            WHERE recruitId = #{id}
            """)
    void deleteFileNameByRecruitId(String id);

    @Select("""
			<script>
			<bind name="pattern" value="'%' + search + '%'" />
			SELECT COUNT(*)
			FROM Recruit
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
			FROM Recruit
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
    List<Recruit> selectAllPaging(Integer startIndex, Integer rowPerPage, String search, String type);
}
