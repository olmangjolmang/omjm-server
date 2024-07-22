package com.ticle.server.user.service;

import com.ticle.server.post.domain.Post;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Service
public class EmailService {

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

    public void sendEmail(String email, Post topPost) {
        try {
            MimeMessage message = emailSender.createMimeMessage();

            message.addRecipients(Message.RecipientType.TO, email);
            message.setSubject("[ticle] 구독 서비스 아티클 알림");

            String content = getContent(topPost);

            message.setText(content, "UTF-8", "html");
            message.setFrom(new InternetAddress("omjmticle@gmail.com", "ticle"));

            emailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException s) {
            throw new IllegalArgumentException();
        }
    }

    private static String getContent(Post latestPost) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String content = "<div style='margin:20px; padding:20px; background-color:#f9f9f9; border:1px solid #ddd; border-radius:10px;'>"
                + "<div style='text-align:center;'>"
                + "<h1 style='color:#333;'>새로운 아티클을 만나보세요!</h1>"
                + "</div>"
                + "<div style='text-align:center; font-family:Verdana, sans-serif; color:#333;'>"
                + "<h2 style='font-size:24px;'>" + latestPost.getTitle() + "</h2>"
                + "<p style='font-size:14px; color:#666;'>" + latestPost.getAuthor() + " | " + simpleDateFormat.format(latestPost.getCreatedDate()) + "</p>"
                + "<p style='font-size:16px; margin-top:20px;'>" + latestPost.getContent() + "</p>";

        if (latestPost.getImage() != null) {
            content += "<div style='margin-top:20px;'>"
                    + "<img src='" + latestPost.getImage().getImageUrl() + "' style='width:100%; max-width:500px; border-radius:10px;' alt='Article Image'>"
                    + "</div>";
        }

        content += "<div style='margin-top:30px; text-align:center;'>"
                + "<p style='font-size:16px;'>"
                + "다양하고 재미있는 글과 인사이트를 얻고 싶다면 <span style='font-weight:bold; color:#007BFF;'>티클</span>을 다시 방문해주세요!"
                + "</p>"
                + "</div>"
                + "</div>";

        return content;
    }

}