package com.example.project2.controller;

import com.example.project2.domain.Member;
import com.example.project2.service.MailService;
import com.example.project2.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MailService mailService;

    @GetMapping("signup")
    public void signupForm() {

    }

    @PostMapping("signup")
    public String signupProcess(Member member, RedirectAttributes rttr) {

        try {
            memberService.signup(member);
            rttr.addFlashAttribute("message", "회원가입이 완료되었습니다");
            return "redirect:/member/login";
        } catch (Exception e) {
            e.printStackTrace();
            rttr.addFlashAttribute("member", member);
            rttr.addFlashAttribute("message", "회원가입 중 문제가 발생하였습니다.");
            return "redirect:/member/signup";
        }
    }

    @GetMapping("jusoPopup")
    public void jusoForm() {

    }

    @PostMapping("jusoPopup")
    public void jusoProcess() {

    }

    @GetMapping("login")
    public void login() {

    }

    @GetMapping("checkId/{id}")
    @ResponseBody
    public Map<String, Object> checkId(@PathVariable("id") String id) {
        return memberService.checkId(id);
    }

    @GetMapping("checkPhoneNum/{phoneNum}")
    @ResponseBody
    public Map<String, Object> checkPhoneNum(@PathVariable("phoneNum") String phoneNum) {

        return memberService.checkPhoneNum(phoneNum);

    }

    @GetMapping("checkNickName/{nickName}")
    @ResponseBody
    public Map<String, Object> checkNickName(@PathVariable("nickName") String nickName) {

        return memberService.checkNickName(nickName);

    }


    @PostMapping("mail")
    public void mail(String email, HttpSession session) {
        mailService.sendMail(email, session);
    }

    @PostMapping("mailCheck")
    @ResponseBody
    public Map<String, Object> checkMail(Model model, Integer enteredCode, HttpSession session) {
        Boolean ok = mailService.compareNum(enteredCode, session);

        model.addAttribute("authentication", ok);
        return Map.of("authentication", ok);

    }
}
