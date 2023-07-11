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

    public boolean modifyNotice(Notice notice) {
        int cnt = noticeMapper.modifyNotice(notice);
        return cnt == 1;
    }

    public boolean removeNotice(Integer id) {
        int cnt = noticeMapper.removeNotice(id);
        return cnt == 1;
    }
}
