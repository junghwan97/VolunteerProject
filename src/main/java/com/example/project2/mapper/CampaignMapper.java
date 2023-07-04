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
            SELECT * 
            FROM Campaign
            WHERE id = #{id}
            """)
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
}
