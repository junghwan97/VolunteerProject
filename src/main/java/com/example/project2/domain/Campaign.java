package com.example.project2.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Campaign {

    private Integer id;
    private String title;
    private String writer;
    private String body;
    private LocalDateTime inserted;
    private List<String> fileName;
    private String repFileName;
    private Boolean liked;
    private Integer likeCount;
}
