package com.example.project2.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Notice {

    private Integer id;
    private String title;
    private String writer;
    private String body;
    private LocalDateTime inserted;
}
