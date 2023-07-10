package com.example.project2.service;

import com.example.project2.domain.Member;
import com.example.project2.mapper.CampaignLikeMapper;
import com.example.project2.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CampaignLikeMapper campaignLikeMapper;

    public boolean signup(Member member) {

        String plain = member.getPassword();
        member.setPassword(passwordEncoder.encode(plain));

        int user = memberMapper.signupInsert(member);
        memberMapper.insertAuthority(member);
        return user == 1;
    }


    public Map<String, Object> checkId(String id) {
        Member member = memberMapper.selectById(id);
        return Map.of("available", member == null);
    }

    public Map<String, Object> checkPhoneNum(String phoneNum) {

        Member member = memberMapper.selectByPhoneNum(phoneNum);
        return Map.of("available", member == null);
    }

    public Map<String, Object> checkNickName(String nickName) {
        Member member = memberMapper.selectByNickName(nickName);
        return Map.of("available", member == null);
    }

    public String getNickName(String name) {
        String nickName = memberMapper.selectByName(name);
        return nickName;
    }

    public Member getInfo(String id) {
        Member member = memberMapper.getInfo(id);
        return member;
    }

    public Member getUserInfo(String name) {
        Member userInfo = memberMapper.getUserInfo(name);
        return userInfo;
    }

    public boolean modifyMemberInfo(Member member, String oldPassword) {
        // 비밀번호를 바꾸기 위해 입력했다면
        if (!member.getPassword().isBlank()) {

            // 입력된 비밀번호를 암호화
            String plain = member.getPassword();
            member.setPassword(passwordEncoder.encode(plain));
        }
        Member oldMember = memberMapper.selectById(member.getId());
        int cnt = 0;

        if (passwordEncoder.matches(oldPassword, oldMember.getPassword())) {
            // 기존 비밀번호와 같으면
            cnt = memberMapper.modify(member);
//         System.out.println(member);
//            if (member.getAuthority() != null) {
//                memberMapper.updateAuthority(member);
//            }

        }
        return cnt == 1;
    }

    public boolean removeAccount(Member member, Member oldMember) {
        int cnt = 0;
        if (passwordEncoder.matches(member.getPassword(), oldMember.getPassword())) {
            // 암호가 같다면

            // 이 회원이 작성한 게시물 row 삭제


            // 이 회원이 좋아요한 레코드 삭제
            campaignLikeMapper.deleteByMemberId(member.getId()); // 자유게시판


            // 회원 테이블 삭제
            memberMapper.deleteAuthority(member.getId());
            cnt = memberMapper.deleteMember(member.getId());
        }
        return cnt == 1;
    }
}
