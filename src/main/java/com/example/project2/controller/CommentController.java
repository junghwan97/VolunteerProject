package com.example.project2.controller;

import com.example.project2.domain.Comment;
import com.example.project2.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("list")
    @ResponseBody
    public List<Comment> list(@RequestParam("campaign") Integer campaignId){

//        return List.of("댓1", "댓2", "댓3");
        return commentService.list(campaignId);
    }

    @PostMapping("add")
    @ResponseBody
    public String add(@RequestBody Comment comment){
        commentService.add(comment);
        return "ok";
    }
}
