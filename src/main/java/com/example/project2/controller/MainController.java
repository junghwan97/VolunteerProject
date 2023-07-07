package com.example.project2.controller;

import com.example.project2.domain.Campaign;
import com.example.project2.domain.Member;
import com.example.project2.service.CampaignService;
import com.example.project2.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

    @Autowired
    private MemberService memberService;

    @GetMapping("mainList")
    public void getMainList(Model model, Authentication authentication){
        List<Campaign> campaignList =  campaignService.getCampaignList();
        model.addAttribute("campaign", campaignList);

        if(authentication != null){
            Member userInfo = memberService.getUserInfo(authentication.getName());
            System.out.println(userInfo);
            model.addAttribute("user", userInfo);
        }
    }

}
