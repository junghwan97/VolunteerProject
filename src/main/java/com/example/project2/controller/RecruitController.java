package com.example.project2.controller;

import com.example.project2.domain.Member;
import com.example.project2.domain.Recruit;
import com.example.project2.service.MemberService;
import com.example.project2.service.RecruitService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("recruit")
public class RecruitController {

    @Autowired
    private RecruitService recruitService;

    @Autowired
    private MemberService memberService;

    @GetMapping("recruitList")
    public void recruitList(Model model,
                            Authentication authentication,
                            @RequestParam(value = "page", defaultValue = "1") Integer page,
                            @RequestParam(value = "search", defaultValue = "") String search,
                            @RequestParam(value = "type", required = false) String type){
//       List<Recruit> recruitList = recruitService.getRecruitList(page, search,type);
       Map<String, Object> recruitList = recruitService.getRecruitList(page, search,type);
       model.addAttribute("recruitList", recruitList);

        if (authentication != null) {
            Member userInfo = memberService.getUserInfo(authentication.getName());
            model.addAttribute("member", userInfo);
        }
    }

    @GetMapping("/recruitId/{id}")
    public String getRecruit(@PathVariable("id") String id, Model model, Authentication authentication){
        if (authentication != null) {
            Member userInfo = memberService.getUserInfo(authentication.getName());
            model.addAttribute("member", userInfo);
        }
       Recruit recruit = recruitService.getRecruit(id);
       model.addAttribute("recruit", recruit);
       return "recruit/getRecruit";
    }

    @GetMapping("addRecruit")
    @PreAuthorize("hasAuthority('needVolunteer') or hasAuthority('admin')")
    public void addRecruitForm(Model model, Authentication authentication){
        if (authentication != null) {
            Member userInfo = memberService.getUserInfo(authentication.getName());
            model.addAttribute("member", userInfo);
        }
    }

    @PostMapping("addRecruit")
    public String addRecruitProcess(Recruit recruit,
                                    Authentication authentication,
                                    @RequestParam("files") MultipartFile[] files,
                                    RedirectAttributes rttr) throws Exception{

        recruit.setWriter(memberService.getNickName(authentication.getName()));
        boolean ok = recruitService.addRecruit(recruit, files);
        if(ok){
            rttr.addFlashAttribute("message","모집 공고가 등록되었습니다.");
            return "redirect:/recruit/recruitList";
        }else{
            rttr.addFlashAttribute("message","모집 공고 등록중 문제가 발생하였습니다.");
            return "redirect:/recruit/addRecruit";
        }
    }

    @GetMapping("/modify/{id}")
    public String modifyRecruitForm(@PathVariable("id") String id,
                                    Authentication authentication,
                                    Model model){
        if (authentication != null) {
            Member userInfo = memberService.getUserInfo(authentication.getName());
            model.addAttribute("member", userInfo);
        }
        Recruit recruit = recruitService.getRecruit(id);
        model.addAttribute("recruit", recruit);
        return "recruit/modifyRecruit";
    }

    @PostMapping("modify/{id}")
    public String modifyRecruitProcess(Recruit recruit,
                                       @RequestParam(value = "files", required = false) MultipartFile[] addFiles,
                                       @RequestParam(value = "modifyFiles", required = false) List<String> modifyFileNames) throws Exception{
        boolean ok = recruitService.modifyRecruit(recruit, modifyFileNames, addFiles);
        if(ok){
            return "redirect:/recruit/recruitId/" + recruit.getId();
        }else{
            return "redirect:/recruit/modify/" + recruit.getId();
        }
    }

    @PostMapping("remove/{id}")
    public String removeRecruit(@PathVariable("id") String id){
        boolean ok = recruitService.removeRecruit(id);
            if(ok){
                return "redirect:/recruit/recruitList";
        }else{
                return "redirect:/recruit/remove" + id;
            }
    }
}
