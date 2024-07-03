package com.glos.notificationservice.manager;

import com.glos.notificationservice.domain.entity.Message;
import com.glos.notificationservice.domain.service.EmailService;
import com.glos.notificationservice.domain.template.*;
import com.glos.notificationservice.domain.template.base.MessageProps;
import com.glos.notificationservice.domain.template.base.MessageTemplate;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EmailManager
{
    private final EmailService emailService;

    public EmailManager(EmailService emailService) {
        this.emailService = emailService;
    }


    public void send(MessageTemplate messageTemplate) throws MessagingException
    {
        if (messageTemplate == null) {
            throw new NullPointerException("messageTemplate is null");
        }
        if (messageTemplate instanceof SimpleMessageTemplate) {
            sendNotification(messageTemplate);
        } else if (messageTemplate instanceof HtmlMessageTemplate) {
            sendHtml(messageTemplate);
        } else if (messageTemplate instanceof ConfirmEmailMessageTemplate) {
            sendConfirmEmail(messageTemplate);
        } else if (messageTemplate instanceof ConfirmPhoneNumberMessageTemplate) {
            sendConfirmPhoneNumber(messageTemplate);
        } else if (messageTemplate instanceof ChangeEmailMessageTemplate) {
            sendChangeEmail(messageTemplate);
        } else if (messageTemplate instanceof ChangeUsernameMessageTemplate) {
            sendChangeUsername(messageTemplate);
        } else if (messageTemplate instanceof ChangePhoneNumberMessageTemplate) {
            sendChangePhoneNumber(messageTemplate);
        } else if (messageTemplate instanceof ChangePasswordMessageTemplate) {
            sendChangePassword(messageTemplate);
        } else if (messageTemplate instanceof DropAccountMessageTemplate) {
            sendDropAccount(messageTemplate);
        }
    }

    private void sendDropAccount(MessageTemplate messageTemplate) throws MessagingException {
        final MessageProps props = messageTemplate.getProperties();
        final String to = props.getValue("to", String.class);
        final String subject = props.getValue("subject", String.class);
        final String view = messageTemplate.getViewName();
        emailService.sendTemplate(to, subject, view, props.asMap());
    }

    private void sendChangePassword(MessageTemplate messageTemplate)
            throws MessagingException {
        final MessageProps props = messageTemplate.getProperties();
        final String to = props.getValue("to", String.class);
        final String subject = props.getValue("subject", String.class);
        final String view = messageTemplate.getViewName();
        emailService.sendTemplate(to, subject, view, props.asMap());
    }

    private void sendChangePhoneNumber(MessageTemplate messageTemplate)
            throws MessagingException {
        final MessageProps props = messageTemplate.getProperties();
        final String to = props.getValue("to", String.class);
        final String subject = props.getValue("subject", String.class);
        final String view = messageTemplate.getViewName();
        emailService.sendTemplate(to, subject, view, props.asMap());
    }

    private void sendChangeUsername(MessageTemplate messageTemplate)
            throws MessagingException {
        final MessageProps props = messageTemplate.getProperties();
        final String to = props.getValue("to", String.class);
        final String subject = props.getValue("subject", String.class);
        final String view = messageTemplate.getViewName();
        emailService.sendTemplate(to, subject, view, props.asMap());
    }

    private void sendChangeEmail(MessageTemplate messageTemplate)
            throws MessagingException {
        final MessageProps props = messageTemplate.getProperties();
        final String to = props.getValue("to", String.class);
        final String subject = props.getValue("subject", String.class);
        final String view = messageTemplate.getViewName();
        emailService.sendTemplate(to, subject, view, props.asMap());
    }



    private void sendConfirmEmail(MessageTemplate messageTemplate)
            throws MessagingException {
        final MessageProps props = messageTemplate.getProperties();
        final String to = props.getValue("to", String.class);
        final String subject = props.getValue("subject", String.class);
        final String view = messageTemplate.getViewName();
        emailService.sendTemplate(to, subject, view, props.asMap());
    }

    private void sendConfirmPhoneNumber(MessageTemplate messageTemplate)
            throws MessagingException {
        final MessageProps props = messageTemplate.getProperties();
        final String to = props.getValue("to", String.class);
        final String subject = props.getValue("subject", String.class);
        final String view = messageTemplate.getViewName();
        emailService.sendTemplate(to, subject, view, props.asMap());
    }

    private void sendNotification(MessageTemplate messageTemplate) {
        final MessageProps props = messageTemplate.getProperties();
        final String to = props.getValue("to", String.class);
        final String subject = props.getValue("subject", String.class);
        final String text = props.getValue("text", String.class);
        emailService.sendText(to, subject, text);
    }

    private void sendHtml(MessageTemplate messageTemplate) throws MessagingException {
        final MessageProps props = messageTemplate.getProperties();
        final String to = props.getValue("to", String.class);
        final String subject = props.getValue("subject", String.class);
        final String plainHtml = props.getValue("html", String.class);
        emailService.sendHtml(to, subject, plainHtml, props.asMap());
    }
}
