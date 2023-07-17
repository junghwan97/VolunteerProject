package com.example.project2.controller;

import com.example.project2.domain.Campaign;
import com.example.project2.domain.DonationForm;
import com.example.project2.service.CampaignService;
import com.example.project2.service.KakaoPay;
import com.example.project2.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Log
@Controller
public class SampleController {

    @Autowired
    private CampaignService campaignService;

    @Setter(onMethod_ = @Autowired)
    private KakaoPay kakaopay;


    @GetMapping("/kakaoPay")
    public String kakaoPayGet(Model model,
                              Authentication authentication,
                              @RequestParam("campaignId") String campaignId,
                              @RequestParam("campaignName") String campaignName) {
        model.addAttribute("campaignId", campaignId);
        model.addAttribute("campaignName", campaignName);
        model.addAttribute("donor", authentication.getName()) ;
        return "kakaoPay";
    }

    @PostMapping("/kakaoPay")
    public String kakaoPay(@RequestParam("campaignName") String campaignName,
                           @RequestParam("donor") String donor,
                           @RequestParam("total_amount") String total_amount,
                           DonationForm donationForm,
                           Model model,
                           HttpSession session,
                           @RequestParam("campaignId") Integer campaignId){
        log.info("kakaoPay post............................................");
        DonationForm donationForm1 = kakaopay.insertDonationInfo(campaignId,donationForm,campaignName, donor, total_amount);
        model.addAttribute("donation", donationForm1);
        String orderId = donationForm1.getPartner_order_id();

        session.setAttribute("orderId", orderId);
        return "redirect:" + kakaopay.kakaoPayReady(donationForm1);

    }

    @GetMapping("/kakaoPaySuccess")
    public void kakaoPaySuccess(@RequestParam("pg_token") String pg_token,
                                Model model,
                                HttpSession session) {
        log.info("kakaoPaySuccess get............................................");
        log.info("kakaoPaySuccess pg_token : " + pg_token);
        String orderId = (String) session.getAttribute("orderId");

        DonationForm donationForm1 = kakaopay.selectDonation(orderId);

        model.addAttribute("info", kakaopay.kakaoPayInfo(pg_token, donationForm1));
    }

}
