package com.example.project2.mapper;

import com.example.project2.domain.Campaign;
import com.example.project2.domain.Notice;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CampaignMapper {

    @Select("""
			<script>
			<bind name="pattern" value="'%' + search + '%'" />
			SELECT COUNT(*)
            FROM Campaign c
            LEFT JOIN FileNames f ON c.id = f.campaignId
            LEFT JOIN RepresentFileName rf ON c.id = rf.campaignid
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
			SELECT\s
			   c.id,
			   c.title,
			   c.body,
			   c.inserted,
			   c.writer,
			   f.fileName,
			   rf.repFileName,
			   cl.campaignId,
			   cl.memberId,
			   (SELECT COUNT(*)\s
			   				FROM CampaignLike\s
			   				WHERE campaignId = c.id) likeCount
			   FROM Campaign c
			   LEFT JOIN FileNames f ON c.id = f.campaignId
			   LEFT JOIN RepresentFileName rf ON c.id = rf.campaignid
			   LEFT JOIN CampaignLike cl on c.id = cl.campaignId
			WHERE c.id = #{id}                        
			""")
    @ResultMap("campaignResultMap")
    Campaign selectById(Integer id);

    @Insert("""
            INSERT INTO Campaign(title, body, writer)
            VALUES (#{title}, #{body}, #{writer})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer addCampaign(Campaign campaign);

    @Update("""
            UPDATE Campaign
            SET
                title = #{title},
                body = #{body}
            WHERE 
                id = #{id}
            """)
    Integer modifyCampaign(Campaign campaign);

    @Delete("""
            DELETE FROM Campaign
            WHERE id = #{id}
            """)
    Integer removeCampaign(Integer id);

    @Insert("""
            INSERT INTO FileNames(campaignId, fileName)
            VALUES (#{campaignId}, #{fileName})
            """)
    Integer insertFileName(Integer campaignId, String fileName);

    @Delete("""
            DELETE FROM FileNames
            WHERE campaignId = #{campaignId}
                AND fileName = #{fileName}
            """)
    void deleteFileNameByCampaignIdAndFileName(Integer campaignId, String fileName);

    @Select("""
            SELECT fileName
            FROM FileNames
            WHERE campaignId = #{id}
            """)
    List<String> selectFileNamesByCampaignId(Integer id);

    @Delete("""
            DELETE FROM FileNames
            WHERE campaignId = #{id}
            """)
    Integer deleteFileNameByCampaignId(Integer id);

    @Select("""
			<script>
			<bind name="pattern" value="'%' + search + '%'" />
			SELECT c.id,
                c.title,
                c.body,
                c.inserted,
                c.writer,             
                f.fileName,
                rf.repFileName,
                (SELECT COUNT(*)\s
			   				FROM CampaignLike\s
			   				WHERE campaignId = c.id) likeCount
            FROM Campaign c
            LEFT JOIN FileNames f ON c.id = f.campaignId
            LEFT JOIN RepresentFileName rf ON c.id = rf.campaignid
            LEFT JOIN CampaignLike cl on c.id = cl.campaignId
			<where>
				<if test="(type eq 'all') or (type eq 'title')">
				   c.title  LIKE #{pattern}
				</if>
				<if test="(type eq 'all') or (type eq 'body')">
				OR c.body   LIKE #{pattern}
				</if>
				<if test="(type eq 'all') or (type eq 'writer')">
				OR c.writer LIKE #{pattern}
				</if>
			</where>
			ORDER BY id DESC
			</script>
			""")
	@ResultMap("campaignResultMap")
    List<Campaign> getCampaignList(String search, String type);

    @Insert("""
            INSERT INTO RepresentFileName(campaignId, repFileName)
            VALUES (#{campaignId}, #{originalFilename})
            """)
    Integer insertRepFileName(Integer campaignId, String originalFilename);

    @Select("""
			<script>
			<bind name="pattern" value="'%' + search + '%'" />
			SELECT c.id,
                c.title,
                c.body,
                c.inserted,
                c.writer,
                f.fileName,
                rf.repFileName
            FROM Campaign c
            LEFT JOIN FileNames f ON c.id = f.campaignId
            LEFT JOIN RepresentFileName rf ON c.id = rf.campaignid
			<where>
				<if test="(type eq 'all') or (type eq 'title')">
				   c.title  LIKE #{pattern}
				</if>
				<if test="(type eq 'all') or (type eq 'body')">
				OR c.body   LIKE #{pattern}
				</if>
				<if test="(type eq 'all') or (type eq 'writer')">
				OR c.writer LIKE #{pattern}
				</if>
			</where>
			ORDER BY id DESC
			LIMIT #{startIndex}, #{rowPerPage}
			</script>
			""")
	@ResultMap("campaignResultMap")
    List<Campaign> selectAllPaging(Integer startIndex, Integer rowPerPage, String search, String type);


	@Delete("""
            DELETE FROM RepresentFileName
            WHERE campaignId = #{id}
            """)
	void deleteRepFileNameByCampaignId(Integer id);

	@Select("""
            SELECT repFileName
            FROM RepresentFileName
            WHERE campaignId = #{id}
            """)
	String selectRepFileNameByCampaignId(Integer id);
}
