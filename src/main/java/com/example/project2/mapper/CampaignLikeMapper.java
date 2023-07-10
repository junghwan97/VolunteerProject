package com.example.project2.mapper;

import com.example.project2.domain.Like;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CampaignLikeMapper {

    @Delete("""
            DELETE FROM CampaignLike
            WHERE campaignId = #{campaignId}
              AND memberId = #{memberId}
            """)
    Integer delete(Like like);

    @Insert("""
            INSERT INTO CampaignLike
            VALUES (#{campaignId}, #{memberId})
            """)
    Integer insert(Like like);

    @Select("""
            SELECT COUNT(*)
            FROM CampaignLike
            WHERE campaignId = #{campaignId}   
            """)
    Integer countByBoardId(Integer campaignId);

    @Select("""
            SELECT *
            FROM CampaignLike
            WHERE campaignId = #{id}
             AND  memberId = #{name}
            """)
    Like select(Integer id, String name);

    @Delete("""
            DELETE FROM CampaignLike
            WHERE memberId = #{id}
            """)
    void deleteByMemberId(String id);
}
