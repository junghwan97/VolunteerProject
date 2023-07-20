package com.example.project2.controller;

import com.example.project2.domain.Member;
import com.example.project2.domain.Notice;
import com.example.project2.service.MemberService;
import com.example.project2.service.NoticeService;
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
@RequestMapping("notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private MemberService memberService;

    @GetMapping("noticeList")
    public void list(Model model, Authentication authentication){

        List<Notice> result = noticeService.noticeList();
        model.addAttribute("noticeList", result);

        if(authentication != null) {
            Member userInfo = memberService.getUserInfo(authentication.getName());
            model.addAttribute("member", userInfo);
        }
    }

    @GetMapping("/noticeId/{id}")
    public String getNotice(@PathVariable("id") Integer id, Model model){
        Notice notice = noticeService.getNotice(id);
        model.addAttribute("notice", notice);
        return "notice/getNotice";
    }

    @GetMapping("addNotice")
    @PreAuthorize("hasAuthority('admin')")
    public void addNoticeForm(){

    }

    @PostMapping("addNotice")
    public String addNoticeProcess(Notice notice,
                                   Authentication authentication,
                                   @RequestParam("docu") MultipartFile docu,
                                   RedirectAttributes rttr) throws Exception{
        notice.setWriter(memberService.getNickName(authentication.getName()));

        boolean ok = noticeService.addNotice(notice, docu);
        if(ok){
            return "redirect:/notice/noticeList";
        }else{
            return "redirect:/notice/addNotice";
        }
    }

    @GetMapping("/modify/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public String modifyNoticeForm(@PathVariable("id") Integer id, Model model, Authentication authentication){
        Notice notice = noticeService.getNotice(id);
        model.addAttribute("notice", notice);

        if(authentication != null) {
            Member userInfo = memberService.getUserInfo(authentication.getName());
            model.addAttribute("member", userInfo);
        }
        return "notice/modifyNotice";
    }

    @PostMapping("/modify/{id}")
    public String modifyNoticeProcess(@RequestParam("addDocu") MultipartFile addDocu,
                                      @RequestParam(value = "modifyFiles", required = false) List<String> modifyFileNames,
                                      Notice notice) throws Exception{
        boolean ok = noticeService.modifyNotice(notice, addDocu, modifyFileNames);
        if(ok){
            return "redirect:/notice/noticeId/" + notice.getId();
        }else{
            return "redirect:/notice/" + notice.getId();
        }
    }

    @PostMapping("/remove/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public String removeNotice(@PathVariable("id") Integer id) {
        boolean ok = noticeService.removeNotice(id);
        if (ok) {
            return "redirect:/notice/noticeList";
        } else {
            return "redirect:/notice/noticeId/" + id;
        }
    }
}
