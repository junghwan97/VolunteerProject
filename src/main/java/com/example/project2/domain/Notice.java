package com.example.project2.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Notice {

    private Integer id;
    private String title;
    private String writer;
    private String body;
    private LocalDateTime inserted;
    private String fileName;
}
