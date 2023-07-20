package com.example.project2.domain;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
public class Recruit {
    private Integer id;
    private String title;
    private String writer;
    private String body;
    private LocalDateTime inserted;
    private List<String> fileName;
    private LocalDate vStartDate;
    private LocalDate vEndDate;
    private LocalTime vStartTime;
    private LocalTime vEndTime;
    private String vField;
    private String vPlace;

}
