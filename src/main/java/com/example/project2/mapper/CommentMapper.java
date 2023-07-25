package com.example.project2.mapper;

import com.example.project2.domain.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Select("""
            SELECT *
            FROM Comment
            WHERE campaignId = #{campaignId}
            ORDER BY id
            """)
    List<Comment> selectAllByCampaignId(Integer campaignId);


    @Insert("""
            INSERT INTO Comment(campaignId, content, memberId)
            VALUES(#{campaignId}, #{content}, #{memberId})
            """)
    Integer insert(Comment comment);
}
