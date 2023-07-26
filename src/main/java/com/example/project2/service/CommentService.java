package com.example.project2.service;

import com.example.project2.domain.Comment;
import com.example.project2.mapper.CommentMapper;
import com.example.project2.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;


    public List<Comment> list(Integer campaignId,
                              Authentication authentication) {

        List<Comment> comments = commentMapper.selectAllByCampaignId(campaignId);
        if(authentication != null){
//            List<Comment> commentWithEditable =
//                    comments.stream()
//                            .map(c -> {
//                                c.setEditable(c.getMemberId().equals(authentication.getName()));
//                                return c;
//                            })
//                            .toList();
            for(Comment comment : comments){
                comment.setEditable(comment.getMemberId().equals(authentication.getName()));
            }
        }

        return commentMapper.selectAllByCampaignId(campaignId);
    }

    public Map<String, Object> add(Comment comment, Authentication authentication) {

        comment.setMemberId(authentication.getName());

        var res = new HashMap<String, Object>();
        int cnt = commentMapper.insert(comment);

        if(cnt == 1){
            res.put("message", "댓글이 등록되었습니다.");
        }else{
            res.put("message", "댓글이 등록되지 않았습니다.");
        }
        return res;
    }

    public Map<String, Object> remove(Integer id) {
        var res = new HashMap<String, Object>();
        int cnt = commentMapper.deleteById(id);

        if(cnt == 1){
            res.put("message", "댓글이 삭제되었습니다.");
        }else{
            res.put("message", "댓글이 삭제되지 않았습니다.");
        }
        return res;
    }

    public Comment get(Integer id) {
        return commentMapper.selectById(id);
    }

    public Map<String, Object> update(Comment comment) {
        var res = new HashMap<String, Object>();
        int cnt = commentMapper.update(comment);

        if(cnt == 1){
            res.put("message", "댓글이 수정되었습니다.");
        }else{
            res.put("message", "댓글이 수정되지 않았습니다.");
        }
        return res;
    }
}
