package com.example.project2.security;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;

import com.example.project2.domain.*;
import com.example.project2.mapper.*;

@Component
public class CustomSecurityChecker {

    @Autowired
    private CampaignMapper campaignMapper;

    @Autowired
    private CommentMapper commentMapper;

    public boolean checkCommentWriter(Authentication authentication,
                                      Integer commentId) {
        Comment comment = commentMapper.selectById(commentId);

        return comment.getMemberId().equals(authentication.getName());
    }

//    public boolean checkCampaignWriter(Authentication authentication, Integer boardId) {
//        Campaign campaign = campaignMapper.selectById(boardId);
//
//        String username = authentication.getName();
//        String writer = campaign.getWriter();
//
//        return username.equals(writer);
//    }
}
