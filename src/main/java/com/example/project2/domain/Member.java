package com.example.project2.domain;

import lombok.Data;

@Data
public class Member {
    private String id;
    private String password;
    private String name;
    private String gender;
    private String email;
    private String phoneNum;
    private String nickName;
    private String address;
    private String authority;
    private String addressSggNm;
}
