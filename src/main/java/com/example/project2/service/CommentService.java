package com.example.project2.service;

import com.example.project2.domain.Comment;
import com.example.project2.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    public List<Comment> list(Integer campaignId) {
        return commentMapper.selectAllByCampaignId(campaignId);
    }

    public void add(Comment comment) {
        comment.setMemberId("user1");
        commentMapper.insert(comment);
    }
}
