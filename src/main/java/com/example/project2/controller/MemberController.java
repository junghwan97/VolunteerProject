package com.example.project2.controller;

import com.example.project2.domain.ApplyRecruit;
import com.example.project2.domain.DonationForm;
import com.example.project2.domain.Member;
import com.example.project2.service.KakaoPay;
import com.example.project2.service.MailService;
import com.example.project2.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.eclipse.tags.shaded.org.apache.xpath.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MailService mailService;

    @Autowired
    private KakaoPay kakaoPay;

    @GetMapping("signup")
    public void signupForm() {

    }

    @PostMapping("signup")
    public String signupProcess(Member member,
                                RedirectAttributes rttr,
                                @RequestParam("docu")MultipartFile docu) {

        try {
            memberService.signup(member, docu);
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

    @PostMapping("remove")
    public String removeProcess(Member member, HttpServletRequest request) throws Exception{
        Member oldMember = memberService.getInfo(member.getId());
        boolean ok = memberService.removeAccount(member, oldMember);
        if(ok){
            request.logout();
            return "redirect:/main/mainList";
        }else{
            return "redirect:/member/remove";
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

    @GetMapping("adminPage")
    public void empowerment(Model model, Authentication authentication){
        List<Member> member = memberService.getPreAuthority();
        model.addAttribute("preMember", member);

        if(authentication != null) {
            Member userInfo = memberService.getUserInfo(authentication.getName());
            model.addAttribute("member", userInfo);
        }
    }

    @PostMapping("giveAuthority/{id}")
    public String giveAuthority(@PathVariable("id") String id, RedirectAttributes rttr){

        Boolean ok = memberService.giveAuthority(id);

        if(ok){
            rttr.addFlashAttribute("message", "권한이 성공적으로 변경되었습니다.");
            return "redirect:/member/adminPage";
        }else {
            rttr.addFlashAttribute("message", "권한 변경되지 않았습니다. 다시 한번 확인해주세요!");
            return "redirect:/member/adminPage";
        }
    }

    @GetMapping("myPage")
//    public void myPage(@PathVariable("id") String id, Model model){ // 왜 @PathVariable("id")를 쓰면 500 오류가 나오는지?
    public void myPage(String id, Model model){
        Member member = memberService.getInfo(id);
        model.addAttribute("member", member);

    }
    @GetMapping("sponsored")
//    public void sponsored(@PathVariable("id") String id, Model model){
    public void sponsored(String id, Model model){
        Member member = memberService.getInfo(id);
        model.addAttribute("member", member);

        List<DonationForm> donationForm = kakaoPay.findMyDonation(id);
        model.addAttribute("donationForm", donationForm);
    }

    @GetMapping("applyRecruitPage")
    public void applyRecruitPage(String id, Model model){
        Member member = memberService.getInfo(id);
        model.addAttribute("member", member);
        System.out.println(member);
    }

    @PostMapping("applyRecruit/{id}")
    public void applyRecruit(@PathVariable("id") String id,
                               @RequestParam("name") String name,
                               @RequestParam("email") String email,
                               @RequestParam("phoneNum") String phoneNum,
                               @RequestParam("gender") String gender,
                               Model model){

        Boolean ok = memberService.applyRecruit(id, name, email, phoneNum, gender);
        ApplyRecruit applyRecruit = memberService.selectApplyRecruitById(id);
        model.addAttribute("applyRecruit", applyRecruit);

        Member member = memberService.selectMember(id);
        model.addAttribute("member", member);

//        if(ok){
//            return "redirect:/member/applyRecruit?id=" + id;
//        }
//
//        return null;

    }
}
