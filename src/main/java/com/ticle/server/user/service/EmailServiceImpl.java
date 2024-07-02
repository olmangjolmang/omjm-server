package com.ticle.server.user.service;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    JavaMailSender emailSender;

    public static final String authNum = createCode();

    private MimeMessage createMessage(String to)throws Exception{
        System.out.println("보내는 대상: " + to);
        System.out.println("인증 번호: " + authNum);
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO,to);
        message.setSubject("[ticle] 회원가입 이메일 인증");

        String msgg = "";
        msgg+= "<div style='margin:100px;'>";
        msgg+= "<h1> 안녕하세요 ticle입니다. </h1>";
        msgg+= "<br>";
        msgg+= "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
        msgg+= "<br>";
        msgg+= "<p>감사합니다!<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "CODE : <strong>";
        msgg+= authNum+"</strong><div><br/> ";
        msgg+= "</div>";

        message.setText(msgg,"utf-8","html");
        message.setFrom(new InternetAddress("eunjae8924@gmail.com","ticle"));

        return message;
    }

    public static String createCode(){
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for(int i=0;i<8;i++){
            int index = rnd.nextInt(3);

            switch(index){
                case 0 -> key.append((char)((int)rnd.nextInt(26) + 97));
                case 1 -> key.append((char)((int)rnd.nextInt(26) + 65));
                case 2 -> key.append(rnd.nextInt(9));
            }
        }
        return key.toString();
    }
    @Override
    public String sendSimpleMessage(String to)throws Exception{
        MimeMessage message = createMessage(to);
        try{
            emailSender.send(message);
        }catch (MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return authNum;
    }
}
