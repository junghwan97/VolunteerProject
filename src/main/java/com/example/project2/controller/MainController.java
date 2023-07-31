package com.example.project2.controller;

import com.example.project2.domain.Campaign;
import com.example.project2.domain.DonationForm;
import com.example.project2.domain.Member;
import com.example.project2.domain.Notice;
import com.example.project2.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("main")
public class MainController {

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private KakaoPay kakaoPay;

    @Autowired
    private CommentService commentService;

    @GetMapping("mainList")
    public void getMainList(Model model,
                            Authentication authentication,
                            @RequestParam(value = "search", defaultValue = "") String search,
                            @RequestParam(value = "type", required = false) String type) {
//        List<Campaign> campaignList =  campaignService.getCampaignList();
        Map<String, Object> campaignList = campaignService.getCampaignList(search, type);
        model.addAllAttributes(campaignList);

        if (authentication != null) {
            Integer donation = kakaoPay.findMyDonationMoney(authentication.getName());
            model.addAttribute("allDonation", donation);
        }

        List<Notice> noticeList = noticeService.noticeList();
        model.addAttribute("notice", noticeList);

        if (authentication != null) {
            Member userInfo = memberService.getUserInfo(authentication.getName());
            model.addAttribute("member", userInfo);
        }

        Integer SumOfDonation = kakaoPay.selectTotal_Amount();
        model.addAttribute("allDonationMoney", SumOfDonation);

        Integer countDonation = kakaoPay.countDonation();
        model.addAttribute("countDonation",countDonation);

        if(authentication != null){
            Integer countDonaTime = kakaoPay.countDonaTime(authentication.getName());
            model.addAttribute("countDonaTime", countDonaTime);
        }
        if(authentication != null){
            Integer countComment = commentService.countComment(authentication.getName());
            model.addAttribute("countComment", countComment);
        }

        Integer countVolunteer = memberService.countVolunteer();
        model.addAttribute("countVolunteer", countVolunteer);

//        int allDonation = kakaoPay.findMyDonationMoneyByCampaignId();
//        int targetAmount = kakaoPay.findCampaignTarget(id);
//        double percent = Math.round(((double) allDonation / targetAmount) * 100 * 100) / 100;
    }
}
