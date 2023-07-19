package com.example.project2.service;

import com.example.project2.domain.Recruit;
import com.example.project2.mapper.RecruitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.List;

@Service
public class RecruitService {

    @Autowired
    private RecruitMapper recruitMapper;

    @Autowired
    private S3Client s3;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    public List<Recruit> getRecruitList() {
        List<Recruit> recruitList = recruitMapper.getAllRecruit();
        return recruitList;
    }

    public Recruit getRecruit(String id) {
        Recruit recruit = recruitMapper.getRecruitById(id);
        System.out.println(recruit);
        return recruit;
    }

    public Boolean addRecruit(Recruit recruit, MultipartFile[] files) throws Exception {
        int cnt = recruitMapper.addRecruit(recruit);
        for (MultipartFile file : files) {
            if (file.getSize() > 0) {
                String objectKey = "recruit/" + recruit.getId() + "/" + file.getOriginalFilename();

                PutObjectRequest por = PutObjectRequest.builder().key(objectKey).acl(ObjectCannedACL.PUBLIC_READ)
                        .bucket(bucketName).build();
                RequestBody rb = RequestBody.fromInputStream(file.getInputStream(), file.getSize());

                s3.putObject(por, rb);
                // db에 관련 정보 저장(insert)
                recruitMapper.insertFileName(recruit.getId(), file.getOriginalFilename());

            }
        }
        return cnt == 1;
    }
}
