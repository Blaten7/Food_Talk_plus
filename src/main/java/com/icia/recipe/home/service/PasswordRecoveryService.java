package com.icia.recipe.home.service;

import com.icia.recipe.home.dao.MemberDao;
import com.icia.recipe.home.dto.Member;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
@Slf4j
public class PasswordRecoveryService {
    @Autowired
    MemberDao mDao;

    @Autowired
    HttpSession session;

    public String PasswordRecovery(Member member) {
        //검증하고 처리하는 로직
        String user=mDao.passwordRecovery(member);

        //인증 코드 생성
        if(user!=null) {
            String authenticationCode = generateCode();
            session.setAttribute("authCode", authenticationCode);
            //사용자 이메일로 인증 코드를 전송
            String recipientEamil = member.getM_id();
            try {
                sendEmail(recipientEamil, authenticationCode);
                log.info("이메일 전송 성공");
                return "이메일 전송 완료";
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }else{
            log.info("이메일 전송 실패");
            return "이메일 전송 실패";
        }

    }

        private String generateCode(){
            //간단히 난수 생성
        return String.valueOf((int) (Math.random()*1000000));
        }
        private void sendEmail(String recipientEamil,String authenticationCode) throws MessagingException{
            String host="smtp.naver.com";
            String username="dlsdnrdl5656@naver.com";
            String password="tmakxmdlsdnr1!";
            int port=465;

            //메일 내용 설정
            String subject="비밀번호 찾기 인증 코드";
            String body="인증 코드는"+authenticationCode+"입니다.";

            Properties props=new Properties();
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", port);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.socketFactory.port", port);
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

            //인증 정보 생성
            Session session=Session.getInstance(props,new Authenticator(){
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            //메세지 생성 및 전송
            Message message=new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(recipientEamil));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("이메일 전송 완료!");
        }

    public boolean checkCode(Member member) {
        if(session.getAttribute("authCode").equals(member.getAuthCode())) {
            return true;
        }else{
            return false;
        }
    }
}

