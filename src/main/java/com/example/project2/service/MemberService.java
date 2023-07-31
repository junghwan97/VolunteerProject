package com.example.project2.service;

import com.example.project2.domain.ApplyRecruit;
import com.example.project2.domain.Member;
import com.example.project2.domain.Recruit;
import com.example.project2.mapper.CampaignLikeMapper;
import com.example.project2.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.List;
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

    @Autowired
    private S3Client s3;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    public boolean signup(Member member, MultipartFile docu) throws Exception {

        String plain = member.getPassword();
        member.setPassword(passwordEncoder.encode(plain));

        int user = memberMapper.signupInsert(member);
        memberMapper.insertAuthority(member);

        String objectKey = "member/" + member.getId() + "/" + docu.getOriginalFilename();

        PutObjectRequest por = PutObjectRequest.builder().key(objectKey).acl(ObjectCannedACL.PUBLIC_READ)
                .bucket(bucketName).build();
        RequestBody rb = RequestBody.fromInputStream(docu.getInputStream(), docu.getSize());

        s3.putObject(por, rb);
        // db에 관련 정보 저장(insert)
        System.out.println(member.getId());
        memberMapper.insertFileName(member.getId(), docu.getOriginalFilename());

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

    public List<Member> getPreAuthority() {
        List<Member> member = memberMapper.getPreAuthority();
        return member;
    }

    public Boolean giveAuthority(String id) {
        int cnt = memberMapper.giveAuthority(id);
        return cnt == 1;
    }

    public Boolean applyRecruit(String id, String name, String email, String phoneNum, String gender, String title, String participation, Integer recruitId) {
        int cnt = memberMapper.applyRecruit(id, name, email, phoneNum, gender, title, participation, recruitId);
        return cnt == 1;
    }

    public ApplyRecruit selectApplyRecruitById(String id) {
        ApplyRecruit applyRecruit = memberMapper.selectApplyRecruitById(id);
        return applyRecruit;
    }

    public Member selectMember(String id) {
       Member member = memberMapper.selectById(id);
       return member;
    }

    public List<ApplyRecruit> getPreApproval(Integer recruitId) {
//       List<ApplyRecruit> applyRecruit = memberMapper.getPreApproval(recruitId);
        return memberMapper.getPreApproval(recruitId);
    }

    public String getnickName(String id) {
       String nickName = memberMapper.getNickName(id);
       return nickName;
    }

    public Integer getRecruitId(String writer) {
        Integer recruitId = memberMapper.getRecruitId(writer);
        return recruitId;
    }

    public void approvalRecruit(String participation, Integer id) {

        int cnt = memberMapper.approvalRecruit(participation, id);
        System.out.println(participation);
        System.out.println(id);
        System.out.println(cnt);
    }

    public Integer countVolunteer() {
        return memberMapper.countVoulnteer();
    }
}
