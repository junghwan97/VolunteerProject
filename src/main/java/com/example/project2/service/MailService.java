package com.example.project2.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Random;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @GetMapping("sendMail")
    public void sendMail(String email, HttpSession session) {

        ArrayList<String> toUserList = new ArrayList<>();
        toUserList.add(email);
        int toUsersize = toUserList.size();
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo((String[]) toUserList.toArray(new String[toUsersize]));

        Random random = new Random();
        int randomNumber = random.nextInt(900000) + 100000;

        simpleMailMessage.setSubject("안녕하세요! HI-FIVE 인증메일입니다!");
        simpleMailMessage.setText("안녕하세요! HI-FIVE 인증메일 보내드립니다.\n" + "=======================\n" + "인증번호 : " + randomNumber + "\n=======================" +"\n 항상 저희 HI-FIVE에 관심을 가져주셔서 감사합니다!");

        javaMailSender.send(simpleMailMessage);
        session.setAttribute("authenticatedNum", randomNumber);


    }

    public Boolean compareNum(Integer enteredCode, HttpSession session) {
        Object num = session.getAttribute("authenticatedNum");
        if (enteredCode.equals(num)) {

            return true;
        } else {
            return false;
        }
    }
}
