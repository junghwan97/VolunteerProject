package com.example.project2.service;

import com.example.project2.domain.Member;
import com.example.project2.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean signup(Member member) {

        String plain = member.getPassword();
        member.setPassword(passwordEncoder.encode(plain));

        int user =  memberMapper.signupInsert(member);
                    memberMapper.insertAuthority(member);
        return user == 1;
    }


    public Map<String, Object> checkId(String id) {
        Member member = memberMapper.selectById(id);
        return Map.of("available", member==null );
    }

    public Map<String, Object> checkPhoneNum(String phoneNum) {

        Member member = memberMapper.selectByPhoneNum(phoneNum);
        return Map.of("available", member==null);
    }

    public Map<String, Object> checkNickName(String nickName) {
        Member member = memberMapper.selectByNickName(nickName);
        return Map.of("available", member==null);
    }

    public String getNickName(String name) {
        String nickName = memberMapper.selectByName(name);
        return nickName;
    }

    public Member getInfo(String id) {
        Member member = memberMapper.getInfo(id);
        return member;
    }
}
