package com.example.project2.service;

import com.example.project2.domain.DonationForm;
import com.example.project2.domain.KakaoPayApprovalVO;
import com.example.project2.domain.KakaoPayReadyVO;
import com.example.project2.mapper.KakaoMapper;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
@Log
public class KakaoPay {

    private static final String HOST = "https://kapi.kakao.com";

    private KakaoPayReadyVO kakaoPayReadyVO;
    private KakaoPayApprovalVO kakaoPayApprovalVO;

    @Autowired
    private KakaoMapper kakaoMapper;




    public String kakaoPayReady(DonationForm donationForm1) {

        RestTemplate restTemplate = new RestTemplate();

        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "644343bceb67b1398005a1f335c6502b");
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
//        params.add("partner_order_id", donationForm.getPartner_order_id());
        params.add("partner_order_id", donationForm1.getPartner_order_id());
//        params.add("partner_user_id", "gorany");
        params.add("partner_user_id", donationForm1.getPartner_user_id());
//        params.add("item_name", "HI-FIVE 기부금");
        params.add("item_name", donationForm1.getItem_name());
        params.add("quantity", "2");
//        params.add("total_amount", "2100");
        params.add("total_amount", donationForm1.getTotal_amount());
        params.add("tax_free_amount", "100");
        params.add("approval_url", "http://localhost:8085/kakaoPaySuccess");
        params.add("cancel_url", "http://localhost:8085/kakaoPayCancel");
        params.add("fail_url", "http://localhost:8085/kakaoPaySuccessFail");

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        try {
            kakaoPayReadyVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), body, KakaoPayReadyVO.class);

            log.info("" + kakaoPayReadyVO);

            return kakaoPayReadyVO.getNext_redirect_pc_url();

        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "/pay";
    }
    public KakaoPayApprovalVO kakaoPayInfo(String pg_token, DonationForm donationForm1) {

        log.info("KakaoPayInfoVO............................................");
        log.info("-----------------------------");

        RestTemplate restTemplate = new RestTemplate();

        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "644343bceb67b1398005a1f335c6502b");
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("tid", kakaoPayReadyVO.getTid());
        params.add("partner_order_id", donationForm1.getPartner_order_id());
        params.add("partner_user_id", donationForm1.getPartner_user_id());
        params.add("pg_token", pg_token);
        params.add("total_amount", donationForm1.getTotal_amount());
//        params.add("total_amount", donationForm.getTotal_amount());

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        try {
            kakaoPayApprovalVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/approve"), body, KakaoPayApprovalVO.class);
            log.info("" + kakaoPayApprovalVO);

            return kakaoPayApprovalVO;

        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public DonationForm insertDonationInfo(Integer campaignId, DonationForm donationForm, String campaignName, String donor, String total_amount) {

//        String orderId = String.valueOf(++basicOrderId);
        Integer orderId = kakaoMapper.selectOrderId();
        donationForm.setCampaignId(campaignId);
        donationForm.setPartner_order_id(Integer.toString(++orderId));
        kakaoMapper.insertOrderId(donationForm.getPartner_order_id());
        donationForm.setPartner_user_id(donor);
        donationForm.setItem_name(campaignName);
        donationForm.setTotal_amount(total_amount);
        kakaoMapper.insertDonationInfo(donationForm);
        return donationForm;
    }

    public DonationForm selectDonation(String orderId) {

        return kakaoMapper.selectDonationByOrderId(orderId);
    }

    public List<DonationForm> findMyDonation(String id) {
        List<DonationForm> donationForm = kakaoMapper.findMyDonation(id);
       return donationForm;
    }

    public Integer findMyDonationMoney(String id) {
        Integer donation = 0;
        List<String> moneys = kakaoMapper.findMyDonationMoney(id);
        for(String money : moneys){
            donation += Integer.parseInt(money);
        }
        return donation;
    }
}
