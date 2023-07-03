package com.example.project2.service;

import com.example.project2.domain.Campaign;
import com.example.project2.mapper.CampaignMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampaignService {

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

    public boolean addCampaign(Campaign campaign) {
        int cnt = campaignMapper.addCampaign(campaign);
        return cnt == 1;
    }
}
