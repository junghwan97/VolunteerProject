package com.example.project2.mapper;

import com.example.project2.domain.Campaign;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CampaignMapper {

    @Select("""
            SELECT *
            FROM Campaign
            """)
    List<Campaign> getList();

    @Select("""
            SELECT\s
                c.id,
                c.title,
                c.body,
                c.inserted,
                c.writer,
                f.fileName,
                rf.repFileName
            FROM Campaign c
            LEFT JOIN FileNames f ON c.id = f.campaignId
            LEFT JOIN RepresentFileName rf ON c.id = rf.campaignid
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
            SELECT\s
                c.id,
                c.title,
                c.body,
                c.inserted,
                c.writer,
                f.fileName,
                rf.repFileName
            FROM Campaign c
            LEFT JOIN FileNames f ON c.id = f.campaignId
            LEFT JOIN RepresentFileName rf ON c.id = rf.campaignid;
            """)
    @ResultMap("campaignResultMap")
    List<Campaign> getCampaignList();

    @Insert("""
            INSERT INTO RepresentFileName(campaignId, repFileName)
            VALUES (#{campaignId}, #{originalFilename})
            """)
    Integer insertRepFileName(Integer campaignId, String originalFilename);
}
