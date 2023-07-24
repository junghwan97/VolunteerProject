package com.example.project2.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {

    private Integer id;
    private Integer campaignId;
    private String content;
    private String memberId;
    private LocalDateTime inserted;

}
