package com.bjtu.androidbackend;

import com.bjtu.androidbackend.util.MailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Test
    public void sendSimpleMail() {
        mailService.sendMail("18301044@bjtu.edu.cn", "请查收你的验证码", "您的验证码为：123456，请在5分钟内注册。");
    }

}
