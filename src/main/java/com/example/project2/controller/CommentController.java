package com.example.project2.controller;

import com.example.project2.domain.Comment;
import com.example.project2.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("list")
    @ResponseBody
    public List<Comment> list(@RequestParam("campaign") Integer campaignId,
                              Authentication authentication){

//        return List.of("댓1", "댓2", "댓3");
        return commentService.list(campaignId, authentication);
    }

    @PostMapping("add")
//    @PreAuthorize("isAuthenticated()")
//    @ResponseBody
    public ResponseEntity<Map<String, Object>> add(@RequestBody Comment comment,
                                                   Authentication authentication){

        if(authentication == null){
            Map<String, Object> res = Map.of("message", "로그인 후 댓글을 작성해주세요!");
            return ResponseEntity.status(401).body(res);
        }else {
            Map<String, Object> res = commentService.add(comment, authentication);
            return ResponseEntity.ok().body(res);
        }
    }

//    @RequestMapping(path="id/{id}", method=RequestMethod.DELETE)
    @DeleteMapping("id/{id}")
    @ResponseBody
    @PreAuthorize("authenticated and @customSecurityChecker.checkCommentWriter(authentication, #id)")
    public ResponseEntity<Map<String, Object>> remove(@PathVariable("id") Integer id){
        Map<String, Object> res = commentService.remove(id);
        return ResponseEntity.ok().body(res);
    }

    @GetMapping("id/{id}")
    @ResponseBody
    public Comment get(@PathVariable("id") Integer id)  {
        return commentService.get(id);
    }

    @PutMapping("update")
    @ResponseBody
    @PreAuthorize("authenticated and @customSecurityChecker.checkCommentWriter(authentication, #comment.id)")
    public ResponseEntity<Map<String, Object>> update(@RequestBody Comment comment){
        Map<String, Object> res = commentService.update(comment);

        return ResponseEntity.ok().body(res);
    }
}
