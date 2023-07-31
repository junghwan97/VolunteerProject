package com.example.project2.mapper;

import com.example.project2.domain.Comment;
import org.apache.ibatis.annotations.*;

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

    @Delete("""
            DELETE FROM Comment
            WHERE id = #{id}
            """)
    Integer deleteById(Integer id);

    @Select("""
            SELECT *
            FROM Comment
            WHERE id = #{id}
            """)
    Comment selectById(Integer id);


    @Update("""
            UPDATE Comment
            SET
                content = #{content}
            WHERE
                id = #{id}
            """)
    Integer update(Comment comment);

    @Select("""
            SELECT count(id)
            FROM Comment
            WHERE memberId = #{name};
            """)
    Integer countCommentById(String name);
}
