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

    @GetMapping("modify")
    public void modifyForm(String id, Model model){
        Member member = memberService.getInfo(id);
        model.addAttribute("member", member);
    }

    @PostMapping("modify")
    public String modifyProcess(Member member, String oldPassword){
        boolean ok = memberService.modifyMemberInfo(member, oldPassword);
        if(ok){
            return "redirect:/member/myPage?id=" + member.getId();
        }
        else{
            return "redirect:/member/modify?id=" + member.getId();

        }
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

    @GetMapping("myPage")
//    public void myPage(@PathVariable("id") String id, Model model){ // 왜 @PathVariable("id")를 쓰면 500 오류가 나오는지?
    public void myPage(String id, Model model){
        Member member = memberService.getInfo(id);
        model.addAttribute("member", member);

    }
}
