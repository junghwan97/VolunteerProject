package com.example.project2.controller;

import com.example.project2.domain.Campaign;
import com.example.project2.domain.Like;
import com.example.project2.domain.Member;
import com.example.project2.service.CampaignService;
import com.example.project2.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("campaign")
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private MemberService memberService;

    @GetMapping("campaignList")
//    @PreAuthorize("isAuthenticated()")
    public void list(Model model,
                     Authentication authentication,
                     @RequestParam(value = "page", defaultValue = "1") Integer page,
                     @RequestParam(value = "search", defaultValue = "") String search,
                     @RequestParam(value = "type", required = false) String type) {

//        List<Campaign> result = campaignService.campaignList();
        Map<String, Object> result = campaignService.campaignList(page, search, type);
        model.addAllAttributes(result);

        if (authentication != null) {
            Member userInfo = memberService.getUserInfo(authentication.getName());
            model.addAttribute("member", userInfo);
        }
    }

    @GetMapping("/campaignId/{id}")
    public String list(@PathVariable("id") Integer id, Model model, Authentication authentication) {
        Campaign campaign = campaignService.getCampaign(id, authentication);
        model.addAttribute("campaign", campaign);
        if (authentication != null) {
            Member userInfo = memberService.getUserInfo(authentication.getName());
            model.addAttribute("member", userInfo);
        }
            return "campaign/getCampaign";
    }
    @GetMapping("addCampaign")
    @PreAuthorize("hasAuthority('admin')")
    public void addCampaignForm(Authentication authentication, Model model) {

        if(authentication != null) {
            Member userInfo = memberService.getUserInfo(authentication.getName());
            model.addAttribute("member", userInfo);
        }
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
    public String modifyCampaignForm(@PathVariable("id") Integer id, Model model, Authentication authentication) {
        Campaign campaign = campaignService.getCampaign(id);
        model.addAttribute("campaign", campaign);
        if(authentication != null) {
            Member userInfo = memberService.getUserInfo(authentication.getName());
            model.addAttribute("member", userInfo);
        }
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

    @PostMapping("/like")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> like(@RequestBody Like like, Authentication authentication){
        if (authentication == null) {
            return ResponseEntity.status(403)
                    .body(Map.of("message", "로그인 후 좋아요 클릭해주세요!"));
        } else {
            return ResponseEntity.ok()
                    .body(campaignService.like(like, authentication));
        }
    }
}
