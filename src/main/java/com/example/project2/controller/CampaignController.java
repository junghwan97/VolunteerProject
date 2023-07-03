package com.example.project2.controller;

import com.example.project2.domain.Campaign;
import com.example.project2.service.CampaignService;
import com.example.project2.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("campaign")
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private MemberService memberService;

    @GetMapping("list")
    @PreAuthorize("isAuthenticated()")
    public void list(Model model){

        List<Campaign> result = campaignService.campaignList();
        model.addAttribute("campaignList", result);
    }

    @GetMapping("/campaignId/{id}")
    public String list(@PathVariable("id") Integer id, Model model){
        Campaign campaign = campaignService.getCampaign(id);
        model.addAttribute("campaign", campaign);
        return "campaign/getCampaign";
    }

    @GetMapping("addCampaign")
    public void addCampaignForm(){

    }

    @PostMapping("addCampaign")
    public String addCampaignProcess(Campaign campaign, Authentication authentication) throws Exception{
        campaign.setWriter(memberService.getNickName(authentication.getName()));

        boolean ok = campaignService.addCampaign(campaign);
        if(ok){
            return "redirect:/campaign/campaignId" + campaign.getId();
        }else{
            return "redirect:/campaign/addCampaign";
        }
    }

    @GetMapping("/modify/{id}")
    public String modifyCampaignForm(@PathVariable("id") Integer id, Model model){
        Campaign campaign = campaignService.getCampaign(id);
        model.addAttribute("campaign", campaign);
        return "campaign/modifyCampaign";
    }

    @PostMapping("/modify/{id}")
    public String modifyCampaignProcess(Campaign campaign){
        boolean ok = campaignService.modifyCampaign(campaign);
        if(ok){
            return "redirect:/campaign/campaignId/" + campaign.getId();
        }else {
            return "redirect:/campaign/modify/" + campaign.getId();
        }
    }
}
