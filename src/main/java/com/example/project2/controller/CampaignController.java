package com.example.project2.controller;

import com.example.project2.domain.Campaign;
import com.example.project2.service.CampaignService;
import com.example.project2.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("campaign")
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private MemberService memberService;

    @GetMapping("campaignList")
//    @PreAuthorize("isAuthenticated()")
    public void list(Model model) {

        List<Campaign> result = campaignService.campaignList();
        model.addAttribute("campaignList", result);
    }

    @GetMapping("/campaignId/{id}")
    public String list(@PathVariable("id") Integer id, Model model) {
        Campaign campaign = campaignService.getCampaign(id);
        model.addAttribute("campaign", campaign);
        return "campaign/getCampaign";
    }

    @GetMapping("addCampaign")
    @PreAuthorize("hasAuthority('admin')")
    public void addCampaignForm() {

    }

    @PostMapping("addCampaign")
    public String addCampaignProcess(Campaign campaign,
                                     Authentication authentication,
                                     @RequestParam("files")MultipartFile[] files,
                                     @RequestParam("repFile")MultipartFile repFile,
                                     RedirectAttributes rttr) throws Exception {
        campaign.setWriter(memberService.getNickName(authentication.getName()));

        boolean ok = campaignService.addCampaign(campaign, files, repFile);
        if (ok) {
            rttr.addFlashAttribute("message", campaign.getId() + "번 게시물이 등록되었습니다.");
            return "redirect:/campaign/campaignId/" + campaign.getId();
        } else {
            rttr.addFlashAttribute("message", campaign.getId() + "번 게시물 등록중 문제가 발생하였습니다.");
            return "redirect:/campaign/addCampaign";
        }
    }

    @GetMapping("/modify/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public String modifyCampaignForm(@PathVariable("id") Integer id, Model model) {
        Campaign campaign = campaignService.getCampaign(id);
        model.addAttribute("campaign", campaign);
        return "campaign/modifyCampaign";
    }

    @PostMapping("/modify/{id}")
    public String modifyCampaignProcess(Campaign campaign,
                                        @RequestParam(value = "files", required = false) MultipartFile[] addFiles,
                                        @RequestParam(value = "modifyFiles", required = false) List<String> modifyFileNames) throws Exception {
        boolean ok = campaignService.modifyCampaign(campaign, modifyFileNames, addFiles);
        if (ok) {
            return "redirect:/campaign/campaignId/" + campaign.getId();
        } else {
            return "redirect:/campaign/modify/" + campaign.getId();
        }
    }

    @PostMapping("/remove/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public String removeCampaign(@PathVariable("id") Integer id) {
        boolean ok = campaignService.removeCampaign(id);
        if (ok) {
            return "redirect:/campaign/campaignList";
        } else {
            return "redirect:/campaign/remove/" + id;
        }
    }
}
