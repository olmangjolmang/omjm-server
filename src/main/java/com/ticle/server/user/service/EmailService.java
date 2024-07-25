package com.ticle.server.user.service;

import com.ticle.server.global.domain.S3Info;
import com.ticle.server.home.domain.Subscription;
import com.ticle.server.home.domain.type.Day;
import com.ticle.server.home.repository.SubscriptionRepository;
import com.ticle.server.post.domain.Post;
import com.ticle.server.post.exception.PostNotFoundException;
import com.ticle.server.post.repository.PostRepository;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import static com.ticle.server.post.exception.errorcode.PostErrorCode.POST_NOT_FOUND;

@Service
@RequiredArgsConstructor
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

        String content = "<div style=\"width: 792px; height: 981px; padding-top: 0.01px; padding-bottom: 203.74px; background: white; flex-direction: column; justify-content: flex-start; align-items: center; gap: 0.50px; display: inline-flex\">"
                + "<img src=\"./newsLetter.jpg\" width=\"792px\">"
                + "<div style=\"align-self: stretch; height: 516.74px; flex-direction: column; justify-content: flex-start; align-items: center; gap: 53px; display: inline-flex\">"
                + "<div style=\"align-self: stretch; height: 361.74px; flex-direction: column; justify-content: flex-start; align-items: center; gap: 36px; display: flex\">"
                + "<div style=\"height: 72px; flex-direction: column; justify-content: flex-start; align-items: center; gap: 12px; display: flex\">"
                + "<div style=\"align-self: stretch; text-align: center; color: black; font-size: 24px; font-family: Pretendard; font-weight: 600; line-height: 36px; word-wrap: break-word\">" + latestPost.getTitle() + "</div>"
                + "<div style=\"justify-content: flex-start; align-items: flex-start; gap: 16px; display: inline-flex\">"
                + "<div style=\"color: black; font-size: 16px; font-family: Pretendard; font-weight: 600; line-height: 24px; word-wrap: break-word\">" + latestPost.getAuthor() + "</div>"
                + "<div style=\"color: #7F7F86; font-size: 16px; font-family: Pretendard; font-weight: 600; line-height: 24px; word-wrap: break-word\">" + simpleDateFormat.format(latestPost.getCreatedDate()) + "</div>"
                + "</div>"
                + "</div>";

        if (latestPost.getImage() != null) {
            content += "<img style=\"width: 400px; height: 253.74px; border-radius: 24px\" src=\"" + latestPost.getImage().getImageUrl() + "\" />";
        } else {
            content += "<img style=\"width: 400px; height: 253.74px; border-radius: 24px\" src=\"https://via.placeholder.com/400x254\" />";
        }

        content += "</div>"
                + "<div style=\"height: 102px; flex-direction: column; justify-content: flex-start; align-items: center; gap: 12px; display: flex\">"
                + "<div style=\"align-self: stretch; text-align: center; color: black; font-size: 16px; font-family: Pretendard; font-weight: 600; line-height: 24px; word-wrap: break-word\">자세히 알아보고 싶다면?</div>"
                + "<div style=\"align-self: stretch; height: 66px; padding-left: 176px; padding-right: 176px; padding-top: 21px; padding-bottom: 21px; background: #463EFB; border-radius: 50px; overflow: hidden; justify-content: center; align-items: center; gap: 10px; display: inline-flex\">"
                + "<div style=\"color: white; font-size: 20px; font-family: Pretendard; font-weight: 600; word-wrap: break-word\">아티클 바로가기</div>"
                + "</div>"
                + "</div>"
                + "</div>"
                + "</div>";

        return content;
    }


    public void sendEmail2(String email, Post topPost) {
        try {
            MimeMessage message = emailSender.createMimeMessage();

            message.addRecipients(Message.RecipientType.TO, email);
            message.setSubject("[ticle] 구독 서비스 아티클 알림");

            String content = getContent2(topPost);

            message.setContent(content, "text/html; charset=UTF-8");
            message.setFrom(new InternetAddress("omjmticle@gmail.com", "ticle"));

            emailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException s) {
            throw new IllegalArgumentException();
        }
    }
    private static String getContent2(Post latestPost) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String content = "<div style=\"width: 792px; height: 981px; padding-top: 0.01px; padding-bottom: 203.74px; background: white; flex-direction: column; justify-content: flex-start; align-items: center; gap: 0.50px; display: inline-flex\">"
                + "<img src=\"" + "https://ticle.s3.ap-northeast-2.amazonaws.com/MAIL/news.jpg"+ "\" width=\"792px\">"
                + "<div style=\"align-self: stretch; height: 516.74px; flex-direction: column; justify-content: flex-start; align-items: center; gap: 53px; display: inline-flex\">"
                + "<div style=\"align-self: stretch; height: 361.74px; flex-direction: column; justify-content: flex-start; align-items: center; gap: 36px; display: flex\">"
                + "<div style=\"width: 500px; height: 72px; flex-direction: column; justify-content: flex-start; align-items: center; gap: 12px; display: flex\">"
                + "<div style=\"align-self: stretch; text-align: center; color: black; font-size: 20px; font-family: Pretendard; font-weight: 600; line-height: 36px; word-wrap: break-word\">" + latestPost.getTitle() + "</div>"
                + "<div style=\"justify-content: flex-start; align-items: flex-start; gap: 16px; display: inline-flex\">"
                + "<div style=\"color: black; font-size: 14px; font-family: Pretendard; font-weight: 600; line-height: 24px; word-wrap: break-word\">" + latestPost.getAuthor() + " | </div>"
                + "<div style=\"color: #7F7F86; font-size: 12px; font-family: Pretendard; font-weight: 600; line-height: 24px; word-wrap: break-word\">" + simpleDateFormat.format(latestPost.getCreatedDate()) + "</div>"
                + "</div>"
                + "</div>";

        if (latestPost.getImage() != null) {
            content += "<img style=\"width: 400px; height: 253.74px; border-radius: 24px\" src=\"" + latestPost.getImage().getImageUrl() + "\" />";
        } else {
            content += "<img style=\"width: 400px; height: 253.74px; border-radius: 24px\" src=\"https://via.placeholder.com/400x254\" />";
        }

        content += "</div>"
                + "<div style=\"height: 102px; flex-direction: column; justify-content: flex-start; align-items: center; gap: 12px; display: flex\">"
                + "<div style=\"align-self: stretch; text-align: center; color: black; font-size: 16px; font-family: Pretendard; font-weight: 600; line-height: 24px; word-wrap: break-word\">자세히 알아보고 싶다면?</div>"
                + "<div style=\"align-self: stretch; height: 66px; padding-left: 176px; padding-right: 176px; padding-top: 21px; padding-bottom: 21px; background: #463EFB; border-radius: 50px; overflow: hidden; justify-content: center; align-items: center; gap: 10px; display: inline-flex;\" onclick=\\\"window.location.href='http://naver.com'\\\" >"
                + "<div style=\"color: white; font-size: 20px; font-family: Pretendard; font-weight: 600; word-wrap: break-word\">아티클 바로가기</div>"
                + "</div>"
                + "</div>"
                + "</div>"
                + "</div>";

        return content;
    }

}