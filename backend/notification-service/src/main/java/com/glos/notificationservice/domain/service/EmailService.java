package com.glos.notificationservice.domain.service;

import ch.qos.logback.classic.Level;
import com.glos.notificationservice.domain.entity.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.freemarker.SpringTemplateLoader;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import javax.naming.*;
import java.nio.charset.StandardCharsets;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class EmailService
{
    private final Logger logger = Logger.getLogger("EmailService");
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    private String username;


    @Autowired
    public EmailService(JavaMailSender mailSender, SpringTemplateEngine templateEngine)
    {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendText(String to, String subject, String content) {
        try {
            logger.info("Sending message");
            final SimpleMailMessage smm = new SimpleMailMessage();
            smm.setTo(to);
            smm.setSubject(subject);
            smm.setText(content);
            smm.setFrom(username);
            mailSender.send(smm);
            logger.info("Message success sent to email");
        } catch (Exception e) {
            logger.info("Error sending message to email.");
        }
    }

    @Async
    public void sendTemplate(String to, String subject, String viewName, Map<String, Object> props) throws MessagingException {
        try {
            logger.info("Sending message");
            final MimeMessage message = mailSender.createMimeMessage();
            final Context context = new Context();
            context.setVariables(props);

            final String process = templateEngine.process(viewName, context);

            final MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED,
                    StandardCharsets.UTF_8.name());

            helper.setTo(to);
            helper.setFrom(username);
            helper.setSubject(subject);
            helper.setText(process, true);

            mailSender.send(message);
            logger.info("Message success sent to email");
        } catch (MessagingException ex) {
            logger.info("Error sending message to email.");
        }
    }

    @Async
    public void sendHtml(String to, String subject, String plainHtml, Map<String, Object> props)
            throws MessagingException {
        try {
            logger.info("Sending message");
            final MimeMessage message = mailSender.createMimeMessage();
            final Context context = new Context();
            context.setVariables(props);

            final String process = templateEngine.process(plainHtml, context);

            final MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED,
                    StandardCharsets.UTF_8.name());

            helper.setTo(to);
            helper.setFrom(username);
            helper.setSubject(subject);
            helper.setText(process, true);

            mailSender.send(message);
            logger.info("Message success sent to email");
        } catch (MessagingException me) {
            logger.info("Error sending message to email.");
        }
    }
}