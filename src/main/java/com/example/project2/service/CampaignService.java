package com.example.project2.service;

import com.example.project2.domain.Campaign;
import com.example.project2.domain.Notice;
import com.example.project2.mapper.CampaignMapper;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class CampaignService {

    @Autowired
    private S3Client s3;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Autowired
    private CampaignMapper campaignMapper;

    public Map<String, Object> campaignList(Integer page, String search, String type) {
//        List<Campaign> list = campaignMapper.getList();
//        return list;

        // 페이지 당 행의 수
        Integer rowPerPage = 5;
        // 쿼리 LIMIT 절에 사용할 시작 인덱스
        Integer startIndex = (page - 1) * rowPerPage;

        // 페이지네이션이 필요한 정보
        // 전체 레코드 수
        Integer numOfRecords = campaignMapper.countAll(search, type);

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
        List<Campaign> list = campaignMapper.selectAllPaging(startIndex, rowPerPage, search, type);
        return Map.of("pageInfo", pageInfo, "campaignList", list);

    }

    public Campaign getCampaign(Integer id) {
        Campaign campaign = campaignMapper.selectById(id);
        return campaign;
    }

    public boolean addCampaign(Campaign campaign, MultipartFile[] files, MultipartFile repFile) throws Exception {
        int cnt = campaignMapper.addCampaign(campaign);
        for (MultipartFile file : files) {
            if (file.getSize() > 0) {
                String objectKey = "campaign/" + campaign.getId() + "/" + file.getOriginalFilename();

                PutObjectRequest por = PutObjectRequest.builder().key(objectKey).acl(ObjectCannedACL.PUBLIC_READ)
                        .bucket(bucketName).build();
                RequestBody rb = RequestBody.fromInputStream(file.getInputStream(), file.getSize());

                s3.putObject(por, rb);
                // db에 관련 정보 저장(insert)
                campaignMapper.insertFileName(campaign.getId(), file.getOriginalFilename());

            }
        }

                String objectKey1 = "campaign/" + campaign.getId() + "/" + repFile.getOriginalFilename();

                PutObjectRequest por1 = PutObjectRequest.builder().key(objectKey1).acl(ObjectCannedACL.PUBLIC_READ)
                        .bucket(bucketName).build();
                RequestBody rb1 = RequestBody.fromInputStream(repFile.getInputStream(), repFile.getSize());

                s3.putObject(por1, rb1);
                // db에 관련 정보 저장(insert)
                campaignMapper.insertRepFileName(campaign.getId(), repFile.getOriginalFilename());



        return cnt == 1;
    }

    public boolean modifyCampaign(Campaign campaign, List<String> modifyFileNames, MultipartFile[] addFiles) throws Exception {

        // FileName 테이블 삭제
        if (modifyFileNames != null && !modifyFileNames.isEmpty()) {
            for (String fileName : modifyFileNames) {
                // s3에서 파일(객체) 삭제
                String objectKey = "campaign/" + campaign.getId() + "/" + fileName;
                DeleteObjectRequest dor = DeleteObjectRequest.builder().bucket(bucketName).key(objectKey).build();
                s3.deleteObject(dor);
                // 테이블에서 삭제
                campaignMapper.deleteFileNameByCampaignIdAndFileName(campaign.getId(), fileName);
            }
        }

        // 새 파일 추가
        for (MultipartFile newFile : addFiles) {
            if (newFile.getSize() > 0) {
                // 테이블에 파일명 추가
                campaignMapper.insertFileName(campaign.getId(), newFile.getOriginalFilename());

                // s3에 파일(객체) 업로드
                String objectKey = "campaign/" + campaign.getId() + "/" + newFile.getOriginalFilename();
                PutObjectRequest por = PutObjectRequest.builder().acl(ObjectCannedACL.PUBLIC_READ).bucket(bucketName)
                        .key(objectKey).build();

                RequestBody rb = RequestBody.fromInputStream(newFile.getInputStream(), newFile.getSize());
                s3.putObject(por, rb);
            }
        }

        int cnt = campaignMapper.modifyCampaign(campaign);
        return cnt == 1;
    }

    public boolean removeCampaign(Integer id) {

        // 파일명 조회
        List<String> fileNames = campaignMapper.selectFileNamesByCampaignId(id);

        // FileName 테이블의 데이터 지우기
        campaignMapper.deleteFileNameByCampaignId(id);
        campaignMapper.deleteRepFileNameByCampaignId(id);

        // s3 bucket의 파일 지우기
        for (String fileName : fileNames) {
            String objectKey = "campaign/" + id + "/" + fileName;
            DeleteObjectRequest dor = DeleteObjectRequest.builder().bucket(bucketName).key(objectKey).build();
            s3.deleteObject(dor);
        }

        int cnt = campaignMapper.removeCampaign(id);
        return cnt == 1;
    }

    public List<Campaign> getCampaignList() {
        List<Campaign> campaignList = campaignMapper.getCampaignList();
        return campaignList;
    }
}
