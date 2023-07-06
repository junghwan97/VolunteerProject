package com.example.project2.controller;

import com.example.project2.domain.Campaign;
import com.example.project2.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("main")
public class MainController {

    @Autowired
    private CampaignService campaignService;

    @GetMapping("mainList")
    public void getMainList(Model model){
        List<Campaign> campaignList =  campaignService.getCampaignList();
        model.addAttribute("campaign", campaignList);
    }

}
