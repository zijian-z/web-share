package com.zijian.webshare.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailConsumer {
    private static final Logger log = LoggerFactory.getLogger(MailConsumer.class);
    @Value("${spring.mail.username}")
    private String from;
    private final JavaMailSender mailSender;

    public MailConsumer(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @RabbitListener(queues = RabbitConfig.MAIL_QUEUE_NAME)
    public void consume(Mail mail) {
        log.info("准备发送: {}", mail.toString());
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(mail.getTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getContent());
        mailSender.send(mailMessage);
    }
}
