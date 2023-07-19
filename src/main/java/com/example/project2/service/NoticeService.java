package com.example.project2.service;

import com.example.project2.domain.Notice;
import com.example.project2.mapper.NoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class NoticeService {

    @Autowired
    private S3Client s3;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Autowired
    private NoticeMapper noticeMapper;

    public List<Notice> noticeList() {
        List<Notice> list = noticeMapper.getNoticeList();
        return list;
    }

    public Notice getNotice(Integer id) {
        Notice notice = noticeMapper.getNotice(id);
        return notice;
    }

    public boolean addNotice(Notice notice, MultipartFile docu) throws Exception {
        int cnt = noticeMapper.addNotice(notice);

                String objectKey = "notice/" + notice.getId() + "/" + docu.getOriginalFilename();

                PutObjectRequest por = PutObjectRequest.builder().key(objectKey).acl(ObjectCannedACL.PUBLIC_READ)
                        .bucket(bucketName).build();
                RequestBody rb = RequestBody.fromInputStream(docu.getInputStream(), docu.getSize());

                s3.putObject(por, rb);
                // db에 관련 정보 저장(insert)
                noticeMapper.insertFileName(notice.getId(), docu.getOriginalFilename());



        return cnt == 1;
    }

    public boolean modifyNotice(Notice notice, MultipartFile addDocu, List<String> modifyFileNames) throws Exception {

        // FileName 테이블 삭제
        if (modifyFileNames != null && !modifyFileNames.isEmpty()) {
            for (String fileName : modifyFileNames) {
                // s3에서 파일(객체) 삭제
                String objectKey = "notice/" + notice.getId() + "/" + fileName;
                DeleteObjectRequest dor = DeleteObjectRequest.builder().bucket(bucketName).key(objectKey).build();
                s3.deleteObject(dor);
                // 테이블에서 삭제
                noticeMapper.deleteFileNameByNoticeIdAndFileName(notice.getId(), fileName);
            }
        }

        // 새 파일 추가
        // 테이블에 파일명 추가
        noticeMapper.insertFileName(notice.getId(), addDocu.getOriginalFilename());

        // s3에 파일(객체) 업로드
        String objectKey = "notice/" + notice.getId() + "/" + addDocu.getOriginalFilename();
        PutObjectRequest por = PutObjectRequest.builder().acl(ObjectCannedACL.PUBLIC_READ).bucket(bucketName)
                .key(objectKey).build();

        RequestBody rb = RequestBody.fromInputStream(addDocu.getInputStream(), addDocu.getSize());
        s3.putObject(por, rb);

        int cnt = noticeMapper.modifyNotice(notice);
        return cnt == 1;
    }

    public boolean removeNotice(Integer id) {
        int cnt = noticeMapper.removeNotice(id);
        return cnt == 1;
    }
}
