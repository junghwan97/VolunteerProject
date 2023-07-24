package com.example.project2.service;

import com.example.project2.domain.Recruit;
import com.example.project2.mapper.RecruitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecruitService {

    @Autowired
    private RecruitMapper recruitMapper;

    @Autowired
    private S3Client s3;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

//    public List<Recruit> getRecruitList(Integer page, String search, String type) {
    public Map<String, Object> getRecruitList(Integer page, String search, String type) {
//        List<Recruit> recruitList = recruitMapper.getAllRecruit();
//        return recruitList;

        // 페이지 당 행의 수
        Integer rowPerPage = 10;
        // 쿼리 LIMIT 절에 사용할 시작 인덱스
        Integer startIndex = (page - 1) * rowPerPage;

        // 페이지네이션이 필요한 정보
        // 전체 레코드 수
        Integer numOfRecords = recruitMapper.countAll(search, type);

        // 맨처음 페이지
        Integer firstPageNum = 1;
        // 마지막 페이지 번호
        Integer lastPageNum = (numOfRecords - 1) / rowPerPage + 1;

        // 페이지네이션 왼쪽번호
        Integer leftPageNum = page - 5;
        // 1보다 작을 수 없음
        leftPageNum = Math.max(leftPageNum, 1);

        // 페이지네이션 오른쪽번호
        Integer rightPageNum = leftPageNum + 9;
        // 마지막페이지보다 클 수 없음
        rightPageNum = Math.min(rightPageNum, lastPageNum);

        // 현재 페이지
        Integer currentPageNum = page;

        Map<String, Object> pageInfo = new HashMap<>();
        pageInfo.put("rightPageNum", rightPageNum);
        pageInfo.put("leftPageNum", leftPageNum);
        pageInfo.put("currentPageNum", page);
        pageInfo.put("firstPageNum", firstPageNum);
        pageInfo.put("lastPageNum", lastPageNum);

        // 게시물 목록
        List<Recruit> list = recruitMapper.selectAllPaging(startIndex, rowPerPage, search, type);
        return Map.of("pageInfo", pageInfo, "recruitList", list);
    }

    public Recruit getRecruit(String id) {
        Recruit recruit = recruitMapper.getRecruitById(id);
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

    public boolean modifyRecruit(Recruit recruit, List<String> modifyFileNames, MultipartFile[] addFiles) throws Exception {

        // FileName 테이블 삭제
        if (modifyFileNames != null && !modifyFileNames.isEmpty()) {
            for (String fileName : modifyFileNames) {
                // s3에서 파일(객체) 삭제
                String objectKey = "recruit/" + recruit.getId() + "/" + fileName;
                DeleteObjectRequest dor = DeleteObjectRequest.builder().bucket(bucketName).key(objectKey).build();
                s3.deleteObject(dor);
                // 테이블에서 삭제
                recruitMapper.deleteFileNameByRecruitIdAndFileName(recruit.getId(), fileName);
            }
        }

        // 새 파일 추가
        for (MultipartFile newFile : addFiles) {
            if (newFile.getSize() > 0) {
                // 테이블에 파일명 추가
                recruitMapper.insertFileName(recruit.getId(), newFile.getOriginalFilename());

                // s3에 파일(객체) 업로드
                String objectKey = "recruit/" + recruit.getId() + "/" + newFile.getOriginalFilename();
                PutObjectRequest por = PutObjectRequest.builder().acl(ObjectCannedACL.PUBLIC_READ).bucket(bucketName)
                        .key(objectKey).build();

                RequestBody rb = RequestBody.fromInputStream(newFile.getInputStream(), newFile.getSize());
                s3.putObject(por, rb);
            }
        }

        int cnt = recruitMapper.modifyRecruit(recruit);
        return cnt == 1;
    }

    public boolean removeRecruit(String id) {

        // 파일명 조회
        List<String> fileNames = recruitMapper.selectFileNamesByRecruitId(id);

        // FileNames 테이블의 파일명 데이터 지우기
        recruitMapper.deleteFileNameByRecruitId(id);

        // s3 bucket의 파일 지우기
        for (String fileName : fileNames) {
            String objectKey = "recruit/" + id + "/" + fileName;
            DeleteObjectRequest dor = DeleteObjectRequest.builder().bucket(bucketName).key(objectKey).build();
            s3.deleteObject(dor);
        }

        int cnt = recruitMapper.removeRecruit(id);
        return cnt == 1;
    }
}
