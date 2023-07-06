package com.example.project2.service;

import com.example.project2.domain.Campaign;
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

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CampaignService {

    @Autowired
    private S3Client s3;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Autowired
    private CampaignMapper campaignMapper;

    public List<Campaign> campaignList() {
        List<Campaign> list = campaignMapper.getList();

        return list;
    }

    public Campaign getCampaign(Integer id) {
        Campaign campaign = campaignMapper.selectById(id);
        return campaign;
    }

    public boolean addCampaign(Campaign campaign, MultipartFile[] files) throws Exception {
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

                // 파일 저장(파일 시스템에)
                // 폴더 만들기
//				String folder = "C:\\sutdy\\upload\\" + board.getId();
//				File targetFolder = new File(folder);
//				if (!targetFolder.exists()) {
//					targetFolder.mkdirs();
//				}
//
//				String path = "C:\\sutdy\\upload\\" + board.getId() + "\\" + file.getOriginalFilename();
//				File target = new File(path);
//				file.transferTo(target);
//
            }
        }
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
